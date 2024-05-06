package com.t2m.g2nee.shop.point.service;

import com.t2m.g2nee.shop.point.dto.request.PointCreateRequestDto;

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
     * @return 회원의 세부정보를 반환
     */
    void savePoint(PointCreateRequestDto request);
}
