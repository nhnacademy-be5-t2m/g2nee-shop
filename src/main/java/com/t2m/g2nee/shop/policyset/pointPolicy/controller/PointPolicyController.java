//package com.t2m.g2nee.shop.policyset.pointPolicy.controller;
//
//import com.t2m.g2nee.shop.pageUtils.PageResponse;
//import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.request.DeliveryPolicySaveDto;
//import com.t2m.g2nee.shop.policyset.deliveryPolicy.dto.response.DeliveryPolicyInfoDto;
//import com.t2m.g2nee.shop.policyset.deliveryPolicy.service.DeliveryPolicyService;
//import javax.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/shop/deliveryPolicy")
//public class PointPolicyController {
//
//    private final DeliveryPolicyService deliveryPolicyService;
//
//    public PointPolicyController(DeliveryPolicyService deliveryPolicyService) {
//        this.deliveryPolicyService = deliveryPolicyService;
//    }
//
//    @PostMapping
//    public ResponseEntity<DeliveryPolicyInfoDto> createDeliveryPolicy(@RequestBody @Valid DeliveryPolicySaveDto request) {
//        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
//                .body(deliveryPolicyService.saveDeliveryPolicy(request));
//    }
//
//    @GetMapping("/recentPolicy")
//    public ResponseEntity<DeliveryPolicyInfoDto> getDeliveryPolicy() {
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
//                .body(deliveryPolicyService.getDeliveryPolicy());
//    }
//
//    @GetMapping
//    public ResponseEntity<PageResponse<DeliveryPolicyInfoDto>> getAllDeliveryPolicy(@RequestParam int page) {
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
//                .body(deliveryPolicyService.getAllDeliveryPolicy(page));
//    }
//}
