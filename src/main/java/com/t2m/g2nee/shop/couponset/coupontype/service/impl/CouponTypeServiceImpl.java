package com.t2m.g2nee.shop.couponset.coupontype.service.impl;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CouponTypeRepository;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CustomCouponTypeRepository;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 쿠폰 controller 클래스
 *
 * @author :김수현
 * @since :1.0
 */
@Service
@RequiredArgsConstructor
public class CouponTypeServiceImpl implements CouponTypeService {


    private final CustomCouponTypeRepository customCouponTypeRepository;

    private final CouponTypeRepository couponTypeRepository;

    /**
     * 각 회원에게 발급된 쿠폰 리스트 조회 컨트롤러
     * @param customerId
     * @return CouponInfoDto
     */

    @Override
    public List<CouponTypeInfoDto> findAllByCustomer_CustomerId(Long customerId) {
        List<Coupon> coupons = couponTypeRepository.findAllByCustomer_CustomerId(customerId);
        return coupons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PageResponse<CouponTypeInfoDto> getAllCoupons(Long customerId, int page) {

        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));

        Page<CouponTypeInfoDto> couponPage = customCouponTypeRepository.getAllCoupons(customerId, pageable);

        PageResponse<CouponTypeInfoDto> pageResponse = new PageResponse<>();

        return pageResponse.getPageResponse(page, 4, couponPage);

    }


    private CouponTypeInfoDto convertToDTO(Coupon coupon) {
        return CouponTypeInfoDto.builder()
                .couponId(coupon.getCouponId())
                .issuedDate(coupon.getIssuedDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .expirationDate(coupon.getExpirationDate().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .status(String.valueOf(coupon.getStatus()))
                .couponTypeName(coupon.getCouponType().getName())
                .period(coupon.getCouponType().getPeriod())
                .type(String.valueOf(coupon.getCouponType().getType()))
                .discount(coupon.getCouponType().getDiscount())
                .minimumOrderAmount(coupon.getCouponType().getMinimumOrderAmount())
                .maximumDiscount(coupon.getCouponType().getMaximumDiscount())
                .build();
    }





}
