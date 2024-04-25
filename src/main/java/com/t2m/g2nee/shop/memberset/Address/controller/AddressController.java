package com.t2m.g2nee.shop.memberset.Address.controller;

import com.t2m.g2nee.shop.memberset.Address.dto.request.AddressCreateRequestDto;
import com.t2m.g2nee.shop.memberset.Address.dto.response.AddressResponseDto;
import com.t2m.g2nee.shop.memberset.Address.service.AddressService;
import com.t2m.g2nee.shop.memberset.Customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.Customer.dto.CustomerSaveRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원의 주소정보를 다루는 컨트롤러 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/shop/address")
public class AddressController {
    private final AddressService addressService;


    /**
     * 주소를 저장하는 메소드
     *
     * @param addressRequest 저장할 주소의 정보가 담긴 dto
     * @return 저장된 주소를 반환
     */
    @PostMapping("/save")
    public ResponseEntity<AddressResponseDto> customerSave(@Valid @RequestBody AddressCreateRequestDto addressRequest) {
        AddressResponseDto addressResponse = addressService.saveAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressResponse);
    }

}
