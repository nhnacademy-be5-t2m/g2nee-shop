package com.t2m.g2nee.shop.memberset.Member.repository.Impl;

import com.t2m.g2nee.shop.memberset.Member.domain.Member;
import com.t2m.g2nee.shop.memberset.Member.repository.MemberCustomRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MemberRepositoryImpl extends QuerydslRepositorySupport implements MemberCustomRepository {
    public MemberRepositoryImpl() {
        super(Member.class);
    }

}
