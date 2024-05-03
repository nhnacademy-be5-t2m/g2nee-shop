package com.t2m.g2nee.shop.memberset.address.service.impl;

import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.address.domain.Address;
import com.t2m.g2nee.shop.memberset.address.dto.request.AddressRequestDto;
import com.t2m.g2nee.shop.memberset.address.dto.response.AddressResponseDto;
import com.t2m.g2nee.shop.memberset.address.repository.AddressRepository;
import com.t2m.g2nee.shop.memberset.address.service.AddressService;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * 주소 정보를 위한 service 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 수정할 주소가 없을시 exception 반환합니다.
     */
    @Override
    public AddressResponseDto modifyAddress(Long addressId, AddressRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        //기본배송지로 설정한 경우 기존의 기본배송지로 설정되어있던 주소를 isDefault=false로 수정
        if (request.getIsDefault()) {
            onlyOneDefaultAddress(member.getCustomerId());
        }
        Address address = Address.builder()
                .addressId(addressId)
                .alias(request.getAlias())
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .detail(request.getDetail())
                .member(member)
                .isDefault(request.getIsDefault())
                .build();
        Address result = addressRepository.save(address);
        return addressToAddressResponse(result);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 수정할 주소가 없을시 exception 반환합니다.
     */
    @Override
    public void changeDefaultAddress(Long addressId) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("주소 정보가 없습니다."));
        onlyOneDefaultAddress(address.getMember().getCustomerId());
        address.setIsDefault(true);
        addressRepository.save(address);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException   회원정보가 없을 시 exception을 반환합니다.
     * @throws BadRequestException 주소가 이미 10개일 시 exception을 반환합니다.
     */
    @Override
    public AddressResponseDto saveAddress(AddressRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        int addressCount = addressRepository.countAddressByMemberCustomerId(member.getCustomerId());
        if (addressCount >= 10) {
            throw new BadRequestException("10개 이상의 주소를 저장할 수 없습니다.");
        }
        //기본배송지로 설정한 경우 기존의 기본배송지로 설정되어있던 주소를 isDefault=false로 수정
        if (request.getIsDefault()) {
            onlyOneDefaultAddress(member.getCustomerId());
        }
        Address address = Address.builder()
                .alias(request.getAlias())
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .detail(request.getDetail())
                .member(member)
                .isDefault(request.getIsDefault())
                .build();
        Address result = addressRepository.save(address);
        return addressToAddressResponse(result);
    }

    /**
     * 기존의 기본배송지로 설정되어있던 주소의 기본배송지 설정을 false로 해줄 메소드
     *
     * @param memberId 기본배송지가 true인 것을 찾기 위해 memberId로 address검색
     */
    private void onlyOneDefaultAddress(Long memberId) {
        Address address = addressRepository.findAddressByMemberCustomerIdAndIsDefault(memberId, true);
        if (address == null) {
            return;
        }
        address.setIsDefault(false);
        addressRepository.save(address);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 회원정보가 없을 시 exception을 반환합니다.
     */
    @Override
    public List<AddressResponseDto> getAddressListByMemberId(Long memberId) {
        List<AddressResponseDto> response = new ArrayList<>();
        List<Address> result = addressRepository.getAllByMemberCustomerIdOrderByIsDefaultDesc(memberId);
        for (Address address : result) {
            response.add(addressToAddressResponse(address));
        }
        return response;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 주소정보가 없을 시 exception을 반환합니다.
     */
    @Override
    public AddressResponseDto getAddressByAddressId(Long addressId) {
        Address result = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("주소 정보가 없습니다."));
        return addressToAddressResponse(result);
    }

    /**
     * address를 addressResponse로 바꿔주는 메소드
     *
     * @param address AddressResponseDto로 바꿀 Address
     * @return AddressResponseDto로 바꾼 결과 반환
     */
    public AddressResponseDto addressToAddressResponse(Address address) {
        return new AddressResponseDto(
                address.getAddressId(),
                address.getAddress(),
                address.getZipcode(),
                address.getAlias(),
                address.getDetail(),
                address.getIsDefault()
        );
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 주소정보가 없을 시 exception을 반환합니다.
     */
    @Override
    public void delete(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new NotFoundException("주소 정보가 없습니다.");
        }
        addressRepository.deleteById(addressId);
    }
}
