package com.t2m.g2nee.shop.point.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.point.domain.Point;
import com.t2m.g2nee.shop.point.domain.QPoint;
import com.t2m.g2nee.shop.point.repository.PointCustomRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PointCustomRepositoryImpl extends QuerydslRepositorySupport implements PointCustomRepository {
    private final JPAQueryFactory queryFactory;

    public PointCustomRepositoryImpl(EntityManager entityManager) {
        super(Customer.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getTotalPoint(Long memberId) {
        QPoint point1 = QPoint.point1;
        return queryFactory.select(point1.point.sum())
                .from(point1)
                .where(point1.member.customerId.eq(memberId))
                .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Point> findUsePointByOrderId(Long orderId) {
        QPoint point1 = QPoint.point1;
        return Optional.ofNullable(from(point1)
                .where(point1.order.orderId.eq(orderId)
                        .and(point1.changeReason.eq(Point.ChangeReason.USE)))
                .fetchOne());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Point> findReturnPointByOrderId(Long orderId) {
        QPoint point1 = QPoint.point1;
        return Optional.ofNullable(from(point1)
                .where(point1.order.orderId.eq(orderId)
                        .and(point1.changeReason.eq(Point.ChangeReason.RETURN)))
                .fetchOne());
    }
}
