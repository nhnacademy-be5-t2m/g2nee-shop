//package com.t2m.g2nee.shop.policyset.pointPolicy.service.impl;
//
//import com.t2m.g2nee.shop.exception.NotFoundException;
//import com.t2m.g2nee.shop.pageUtils.PageResponse;
//import com.t2m.g2nee.shop.policyset.deliveryPolicy.domain.DeliveryPolicy;
//import com.t2m.g2nee.shop.policyset.pointPolicy.domain.PointPolicy;
//import com.t2m.g2nee.shop.policyset.pointPolicy.dto.request.PointPolicySaveDto;
//import com.t2m.g2nee.shop.policyset.pointPolicy.dto.response.PointPolicyInfoDto;
//import com.t2m.g2nee.shop.policyset.pointPolicy.repository.PointPolicyRepository;
//import com.t2m.g2nee.shop.policyset.pointPolicy.service.PointPolicyService;
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class PointPolicyServiceImpl implements PointPolicyService {
//
//    private static int maxPageButtons = 5;
//    private final PointPolicyRepository pointPolicyRepository;
//
//    public PointPolicyServiceImpl(PointPolicyRepository pointPolicyRepository) {
//        this.pointPolicyRepository = pointPolicyRepository;
//    }
//
//    @Override
//    public PointPolicyInfoDto saveDeliveryPolicy(PointPolicySaveDto request) {
//        if(pointPolicyRepository.count()==0){//처음으로 저장하는 경우
//            return convertToDeliveryPolicyInfoDto(pointPolicyRepository.save(convertToDeliveryPolicy(request)));
//        }else{//변경해야할 경우
//            //이전의 정책을 비활성화로 변경
//            pointPolicyRepository.softDelete();
//            //새로운 정책 저장
//            return convertToDeliveryPolicyInfoDto(pointPolicyRepository.save(convertToDeliveryPolicy(request)));
//        }
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public PointPolicyInfoDto getDeliveryPolicy() {
//        return convertToDeliveryPolicyInfoDto(pointPolicyRepository.findFirstByIsActivatedOrderByChangedDateDesc(true).orElseThrow(() -> new NotFoundException("배송비 정책이 존재하지 않습니다")));
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public PageResponse<PointPolicyInfoDto> getAllDeliveryPolicy(int page) {
//        Page<PointPolicy> deliveryPolicies = pointPolicyRepository.findAll(
//                PageRequest.of(page - 1, 10, Sort.by("changedDate"))
//        );
//
//        List<PointPolicyInfoDto> deliveryPolicyInfoDtoList = deliveryPolicies
//                .stream().map(this::convertToDeliveryPolicyInfoDto)
//                .collect(Collectors.toList());
//
//        int startPage = (int) Math.max(1, deliveryPolicies.getNumber() - Math.floor((double) maxPageButtons / 2));
//        int endPage = Math.min(startPage + maxPageButtons - 1, deliveryPolicies.getTotalPages());
//
//        if (endPage - startPage + 1 < maxPageButtons) {
//            startPage = Math.max(1, endPage - maxPageButtons + 1);
//        }
//
//
//        return PageResponse.<PointPolicyInfoDto>builder()
//                .data(deliveryPolicyInfoDtoList)
//                .currentPage(page)
//                .totalPage(deliveryPolicies.getTotalPages())
//                .startPage(startPage)
//                .endPage(endPage)
//                .totalElements(deliveryPolicies.getTotalElements())
//                .build();
//    }
//
//    private PointPolicyInfoDto convertToDeliveryPolicyInfoDto(DeliveryPolicy deliveryPolicy) {
//        return new PointPolicyInfoDto(deliveryPolicy.getDeliveryPolicyId(), deliveryPolicy.getDeliveryFee().intValue(),
//                deliveryPolicy.getFreeDeliveryStandard().intValue(), deliveryPolicy.getIsActivated(), deliveryPolicy.getChangedDate());
//    }
//
//    private PointPolicy convertToDeliveryPolicy(PointPolicySaveDto pointPolicySaveDto) {
//        return new DeliveryPolicy(BigDecimal.valueOf(pointPolicySaveDto.getDeliveryFee()),
//                BigDecimal.valueOf(pointPolicySaveDto.getFreeDeliveryStandard()));
//    }
//}
