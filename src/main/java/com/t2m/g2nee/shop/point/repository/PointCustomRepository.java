package com.t2m.g2nee.shop.point.repository;

import com.t2m.g2nee.shop.point.domain.Point;
import java.util.Optional;

public interface PointCustomRepository {
    /**
     * member의 포인트를 모두 더해서 반환하는 메소드
     *
     * @param memberId
     * @return member의 포인트 합계
     */
    Integer getTotalPoint(Long memberId);

    /**
     * 주문할때 썼던 point를 찾아주는 메소드
     *
     * @param orderId
     * @return point 정보
     */
    Optional<Point> findUsePointByOrderId(Long orderId);

    /**
     * 주문 시 사용한 point를 다시 반환해준 내역을 찾는 메소드
     *
     * @param orderId
     * @return point 정보
     */
    Optional<Point> findReturnPointByOrderId(Long orderId);

}
