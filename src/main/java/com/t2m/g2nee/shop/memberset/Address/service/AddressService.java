package com.t2m.g2nee.shop.memberset.Address.service;

import com.t2m.g2nee.shop.memberset.Address.dto.request.AddressRequestDto;
import com.t2m.g2nee.shop.memberset.Address.dto.response.AddressResponseDto;
import java.util.List;

public interface AddressService {
    /**
     * 주소 정보를 받아와 저장하는 메소드
     *
     * @param request 주소등록 시 받는 입력
     * @return 주소 등록 후 등록된 주소정보 반환
     * @return 주소 등록 후 등록된 주소정보 반환
     */
    AddressResponseDto saveAddress(AddressRequestDto request);

    /**
     * memberId에 해당하는 address list 응답하는 메소드
     *
     * @param memberId 찾을 주소의 memberId
     * @return memberId에 해당하는 주소 list 반환
     */
    List<AddressResponseDto> getAddressListByMemberId(Long memberId);

    /**
     * addressId로 address를 찾는 메소드
     *
     * @param addressId 찾을 주소의 addressId
     * @return addressId에 해당하는 주소 반환
     */
    AddressResponseDto getAddressByAddressId(Long addressId);

    /**
     * addressId로 주소 삭제하는 메소드
     *
     * @param addressId 삭제할 주소의 addressId
     */
    void delete(Long addressId);

    /**
     * 주소를 수정하는 메소드
     *
     * @param addressRequest 수정할 address 정보가 담긴 dto
     */
    AddressResponseDto modifyAddress(Long addressId, AddressRequestDto addressRequest);

    /**
     * 기본배송지를 수정하는 메소드
     *
     * @param addressId 기본배송지로 설정할 주소 id
     */
    void changeDefaultAddress(Long addressId);
}
