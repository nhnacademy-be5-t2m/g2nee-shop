package com.t2m.g2nee.shop.couponset.coupon.service.impl;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.dto.CouponInfoDto;
import com.t2m.g2nee.shop.couponset.coupon.repository.CouponRepository;
import com.t2m.g2nee.shop.couponset.coupon.repository.CustomCouponRepository;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.review.dto.ReviewDto;
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
public class CouponServiceImpl implements CouponService {


    private final CustomCouponRepository customCouponRepository;

    private final CouponRepository couponRepository;

    /**
     * 각 회원에게 발급된 쿠폰 리스트 조회 컨트롤러
     * @param customerId
     * @return CouponInfoDto
     */

    @Override
    public List<CouponInfoDto> findAllByCustomer_CustomerId(Long customerId) {
        List<Coupon> coupons = couponRepository.findAllByCustomer_CustomerId(customerId);
        return coupons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PageResponse<CouponInfoDto> getAllCoupons(Long customerId, int page) {

        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));

        Page<CouponInfoDto> couponPage = customCouponRepository.getAllCoupons(customerId, pageable);

        PageResponse<CouponInfoDto> pageResponse = new PageResponse<>();

        return pageResponse.getPageResponse(page, 4, couponPage);

    }


    private CouponInfoDto convertToDTO(Coupon coupon) {
        return CouponInfoDto.builder()
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
