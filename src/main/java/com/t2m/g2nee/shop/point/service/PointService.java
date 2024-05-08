package com.t2m.g2nee.shop.point.service;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.point.dto.request.PointCreateRequestDto;
import java.math.BigDecimal;

/**
 * 주소 정보를 위한 service interface 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
public interface PointService {

    /**
     * 포인트 정보를 저장하는 메소드
     *
     * @param request
     */
    void savePoint(PointCreateRequestDto request);

    /**
     * member에게 회원가입 포인트를 주는 메소드
     *
     * @param member
     */
    void giveSignUpPoint(Member member);

    /**
     * member에게 리뷰 포인트를 주는 메소드
     *
     * @param member
     */
    void giveReviewPoint(Member member);

    /**
     * member에게 구매시 포인트를 주는 메소드
     *
     * @param member      포인트를 줄 대상
     * @param orderAmount 결제금액
     */
    void givePurchasePoint(Member member, BigDecimal orderAmount);

    /**
     * member가 구매시 포인트를 사용하는 메소드
     *
     * @param memberId 포인트를 사용하는 대상
     * @param orderId  포인트가 사용된 주문
     * @param point    포인트 사용 금액
     */
    void usePoint(Long memberId, Long orderId, int point);

    /**
     * member에게 포인트를 다시 돌려주는 메소드
     *
     * @param orderId 포인트를 사용했던 order의 id
     */
    void returnPoint(Long orderId);

    /**
     * member의 전체 포인트를 받아오는 메소드
     *
     * @param memberId 포인트를 받아올 member의 id
     */
    Integer getTotalPoint(Long memberId);
}
