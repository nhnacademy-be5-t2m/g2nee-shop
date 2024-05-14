package com.t2m.g2nee.shop.memberset.member.repository.Impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t2m.g2nee.shop.memberset.customer.domain.Customer;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.domain.QMember;
import com.t2m.g2nee.shop.memberset.member.repository.MemberCustomRepository;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {
    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager entityManager) {
        super(Customer.class);
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Member> findActiveMemberById(Long memberId) {
        QMember member = QMember.member;
        return Optional.ofNullable(
                queryFactory.selectFrom(member)
                        .where(member.customerId.eq(memberId)
                                .and(member.memberStatus.eq(Member.MemberStatus.ACTIVE)))
                        .fetchOne()
        );
    }

    @Override
    public Optional<Member> findActiveMemberByUsername(String username) {
        QMember member = QMember.member;
        return Optional.ofNullable(
                queryFactory.selectFrom(member)
                        .where(member.username.eq(username)
                                .and(member.memberStatus.eq(Member.MemberStatus.ACTIVE)))
                        .fetchOne()
        );
    }

}
