package com.t2m.g2nee.shop.annotation.aspect;

import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.Member.dto.response.MemberDetailInfoResponseDto;
import com.t2m.g2nee.shop.memberset.Member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.elasticsearch.monitor.os.OsStats;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
/**
 * 입니다.
 *
 * @author : 강병구
 * @date : 2024-04-09
 */
@Slf4j
@Aspect
@Component
public class AuthorizationAspect {
    private final MemberService memberService;

    public AuthorizationAspect(MemberService memberService) {
        this.memberService = memberService;
    }

    @Around(value = "@annotation(com.t2m.g2nee.shop.annotation.AdminAuth)")
    public Object loginCheck(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = getRequest();
        String accessToken = Objects.requireNonNull(request.getHeaders("Authorization").nextElement().substring("Bearer ".length()));
        MemberDetailInfoResponseDto member = memberService.getMemberDetailInfoToAccessToken(accessToken);

        if(Objects.isNull(member)) {
            throw new NotFoundException("없는 회원입니다.");
        }

        if(!member.getAuthorities().contains("ROLE_ADMIN")) {
            throw new BadRequestException("권한이 없습니다.");
        }

        return  pjp.proceed();
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return requestAttributes.getRequest();
    }
}