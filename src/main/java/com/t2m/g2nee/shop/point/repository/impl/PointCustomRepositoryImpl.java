package com.t2m.g2nee.shop.point.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import com.t2m.g2nee.shop.point.domain.Point;
import com.t2m.g2nee.shop.point.domain.QPoint;
import com.t2m.g2nee.shop.point.dto.response.PointResponseDto;
import com.t2m.g2nee.shop.point.repository.PointCustomRepository;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PointCustomRepositoryImpl extends QuerydslRepositorySupport implements PointCustomRepository {
    private final JPAQueryFactory queryFactory;

    public PointCustomRepositoryImpl(EntityManager entityManager) {
        super(Point.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }
    QPoint point = QPoint.point1;

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTotalPoint(Long memberId) {

        return queryFactory.select(point.point.sum())
                .from(point)
                .where(point.member.customerId.eq(memberId))
                .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Point> findUsePointByOrderId(Long orderId) {
        return Optional.ofNullable(from(point)
                .where(point.order.orderId.eq(orderId)
                        .and(point.changeReason.eq(Point.ChangeReason.USE)))
                .fetchOne());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Point> findReturnPointByOrderId(Long orderId) {
        return Optional.ofNullable(from(point)
                .where(point.order.orderId.eq(orderId)
                        .and(point.changeReason.eq(Point.ChangeReason.RETURN)))
                .fetchOne());
    }

    /**
     * Author : 신동민
     * 회원 포인트 적립 내역을 확인하는 메서드
     * @param memberId 회원 아이디
     * @return List<PointResponseDto>
     */
    @Override
    public Page<PointResponseDto> getMemberPointDetail(Pageable pageable, Long memberId) {
        List<PointResponseDto> pointDetailList = from(point)
                .where(point.member.customerId.eq(memberId))
                .select(Projections.fields(PointResponseDto.class
                        , point.point
                        , point.changeDate
                        , point.changeReason.as("reason")))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(pointDetailList, pageable, pointDetailList.size());

    }
}
