package com.t2m.g2nee.shop.memberset.member.repository.Impl;

import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberRepositoryCustom {
    public MemberRepositoryImpl() {
        super(Member.class);
    }

}
