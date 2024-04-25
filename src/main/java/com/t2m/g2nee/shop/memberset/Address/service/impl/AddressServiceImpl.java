package com.t2m.g2nee.shop.memberset.Address.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Address.domain.Address;
import com.t2m.g2nee.shop.memberset.Address.dto.request.AddressCreateRequestDto;
import com.t2m.g2nee.shop.memberset.Address.repository.AddressRepository;
import com.t2m.g2nee.shop.memberset.Address.service.AddressService;
import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import com.t2m.g2nee.shop.memberset.Address.dto.response.AddressResponseDto;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberRepository;
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
     * @throws NotFoundException 회원정보가 없을 시 exception을 반환합니다.
     */
    @Override
    public AddressResponseDto saveAddress(AddressCreateRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        Address address = Address.builder()
                .alias(request.getAlias())
                .zipcode(request.getZipcode())
                .address(request.getAddress())
                .detail(request.getDetail())
                .member(member)
                .isDefault(request.getIsDefault())
                .build();
        Address result = addressRepository.save(address);
        return new AddressResponseDto(
                result.getAddressId(),
                result.getAddress(),
                result.getZipcode(),
                result.getAlias(),
                result.getDetail(),
                result.getIsDefault()
        );
    }
}
