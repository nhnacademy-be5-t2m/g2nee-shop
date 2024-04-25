package com.t2m.g2nee.shop.memberset.Address.service;

import com.t2m.g2nee.shop.memberset.Address.domain.Address;
import com.t2m.g2nee.shop.memberset.Address.dto.request.AddressCreateRequestDto;
import com.t2m.g2nee.shop.memberset.Address.dto.response.AddressResponseDto;

public interface AddressService {
    /**
     * 주소 정보를 받아와 저장하는 메소드
     *
     * @param request 주소등록 시 받는 입력
     * @return 주소 등록 후 등록된 주소정보 반환
     */
    AddressResponseDto saveAddress(AddressCreateRequestDto request);
}
