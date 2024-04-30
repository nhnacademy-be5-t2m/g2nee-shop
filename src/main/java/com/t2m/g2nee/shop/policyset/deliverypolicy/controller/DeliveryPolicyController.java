package com.t2m.g2nee.shop.policyset.deliverypolicy.controller;

import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.request.DeliveryPolicySaveDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.dto.response.DeliveryPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.deliverypolicy.service.DeliveryPolicyService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 배송비 정책을 위한 컨트롤러입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@RestController
@RequestMapping("/api/v1/shop/deliveryPolicies")
public class DeliveryPolicyController {

    private final DeliveryPolicyService deliveryPolicyService;

    /**
     * DeliveryPolicyController의 생성자입니다.
     * @param deliveryPolicyService 배송비 정책 서비스
     */
    public DeliveryPolicyController(DeliveryPolicyService deliveryPolicyService) {
        this.deliveryPolicyService = deliveryPolicyService;
    }

    /**
     * 배송비 정책을 저장합니다.
     *
     * @param request 배송비 정책 저장 객체
     * @return ResponseEntity<DeliveryPolicyInfoDto>
     */
    @PostMapping
    public ResponseEntity<DeliveryPolicyInfoDto> createDeliveryPolicy(
            @RequestBody @Valid DeliveryPolicySaveDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(deliveryPolicyService.saveDeliveryPolicy(request));
    }

    /**
     * 현재 설정된 배송비 정책을 보여줍니다.
     *
     * @return ResponseEntity<DeliveryPolicyInfoDto>
     */
    @GetMapping("/recentPolicy")
    public ResponseEntity<DeliveryPolicyInfoDto> getDeliveryPolicy() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(deliveryPolicyService.getDeliveryPolicy());
    }

    /**
     * 배송비 정책 기록을 페이징 처리하여 보여줍니다.
     *
     * @param page 현재 페이지
     * @return ResponseEntity<PageResponse < DeliveryPolicyInfoDto>>
     */
    @GetMapping
    public ResponseEntity<PageResponse<DeliveryPolicyInfoDto>> getAllDeliveryPolicy(@RequestParam int page) {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                .body(deliveryPolicyService.getAllDeliveryPolicy(page));
    }
}
