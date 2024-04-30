package com.t2m.g2nee.shop.memberset.address.controller;

import com.t2m.g2nee.shop.memberset.address.dto.request.AddressRequestDto;
import com.t2m.g2nee.shop.memberset.address.dto.response.AddressResponseDto;
import com.t2m.g2nee.shop.memberset.address.service.AddressService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<AddressResponseDto> createAddress(@Valid @RequestBody AddressRequestDto addressRequest) {
        AddressResponseDto addressResponse = addressService.saveAddress(addressRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressResponse);
    }

    /**
     * memberId에 해당하는 주소 list를 받아오는 메소드
     *
     * @param memberId 찾을 주소들의 memberId
     * @return memberId에 해당하는 주소 list를 반환
     */
    @GetMapping("/getListByMemberId/{memberId}")
    public ResponseEntity<List<AddressResponseDto>> getAddressList(@PathVariable("memberId") Long memberId) {
        List<AddressResponseDto> addressListByMemberId = addressService.getAddressListByMemberId(memberId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressListByMemberId);
    }

    /**
     * addressId로 주소를 찾는 메소드
     *
     * @param addressId 찾을 주소의 addressId
     * @return 주소 반환
     */
    @GetMapping("/getByAddressId/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddress(@PathVariable("addressId") Long addressId) {
        AddressResponseDto response = addressService.getAddressByAddressId(addressId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    /**
     * addressId로 주소를 삭제하는 메소드
     *
     * @param addressId 삭제할 주소의 addressId
     * @return ResponseEntity<Void> 반환
     */
    @DeleteMapping("/delete/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId) {
        addressService.delete(addressId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 주소를 수정하는 메소드
     *
     * @param addressRequest 수정할 주소의 정보가 담긴 dto
     * @return 수정된 주소를 반환
     */
    @PatchMapping("/modify/{addressId}")
    public ResponseEntity<AddressResponseDto> modifyAddress(@PathVariable("addressId") Long addressId,
                                                            @Valid @RequestBody AddressRequestDto addressRequest) {
        AddressResponseDto addressResponse = addressService.modifyAddress(addressId, addressRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(addressResponse);
    }

    /**
     * 기본 배송지를 수정하는 메소드
     *
     * @param addressId 기본배송지로 설정할 배송지 id
     * @return 응답 결과
     */
    @PutMapping("/changeDefaultAddress/{addressId}")
    public ResponseEntity<Void> changeDefaultAddress(@PathVariable("addressId") Long addressId) {
        addressService.changeDefaultAddress(addressId);
        return ResponseEntity.ok().build();
    }
}
