package com.t2m.g2nee.shop.policyset.pointpolicy.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 포인트 정책에 대한 정보를 반환하는 객체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PointPolicyInfoDto {
    /**
     * 포인트 정책 id
     */
    private Long pointPolicyId;
    /**
     * 포인트 정책 이름
     */
    private String policyName;
    /**
     * 포인트 정책 타입
     */
    private String policyType;
    /**
     * 적립 수치
     */
    private String amount;
    /**
     * 정책 활성화 여부
     */
    private Boolean isActivated;
    /**
     * 정책 변동 날짜
     */
    private String changedDate;
}
