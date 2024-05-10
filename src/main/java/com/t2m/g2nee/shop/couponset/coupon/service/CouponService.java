package com.t2m.g2nee.shop.couponset.coupon.service;

import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.dto.request.CouponDownloadDto;
import com.t2m.g2nee.shop.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.shop.couponset.coupon.dto.response.CouponInfoDto;
import com.t2m.g2nee.shop.pageUtils.PageResponse;

public interface CouponService {

    /**
     * 전체 회원에게 일괄적으로 쿠폰을 발급합니다.
     *
     * @param request CouponIssueDto
     * @return CouponInfoDto 객체
     */
    CouponInfoDto issueCoupon(CouponIssueDto request);

    /**
     * 회원이 직접 쿠폰을 다운받습니다.
     *
     * @param request CouponDownloadDto
     * @return CouponInfoDto 객체
     */
    CouponInfoDto downloadCoupon(CouponDownloadDto request);

    /**
     * 회원의 쿠폰 목록을 페이징처리 합니다.
     *
     * @param customerId 회원 id
     * @return PageResponse<CouponInfoDto>
     */
    PageResponse<CouponInfoDto> getMyCoupons(Long customerId, int page);

    /**
     * 회원이 구매시, 책에 사용할 수 있는 쿠폰(책, 카테고리)
     *
     * @param customerId 회원 id
     * @param bookId     책 id
     * @param page       현재 페이지
     * @return PageResponse<CouponInfoDto>
     */
    PageResponse<CouponInfoDto> getBookCoupons(Long customerId, Long bookId, int page);

    /**
     * 회원이 구매시, 전체적으로 사용할 수 있는 쿠폰(책 카테고리 제외)
     *
     * @param customerId
     * @param page
     * @return
     */
    PageResponse<CouponInfoDto> getTotalCoupons(Long customerId, int page);

    /**
     * 쿠폰 id로 쿠폰을 얻는 메소드 입니다.
     *
     * @param couponId 쿠폰 id
     * @return 쿠폰 객체
     */
    Coupon getCoupon(Long couponId);

    /**
     * 쿠폰 사용으로 변경하는 메소드입니다.
     *
     * @param couponId 쿠폰 id
     */
    void useCoupon(Long couponId);
}
