package com.t2m.g2nee.shop.orderset.orderdetail.repository;

import com.t2m.g2nee.shop.orderset.orderdetail.dto.response.GetOrderDetailResponseDto;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * 주문상세 repository 에서 querydsl 사용가능하게 하는 클래스
 *
 * @author : 박재희
 * @since : 1.0
 */
@NoRepositoryBean
public interface OrderDetailCustomRepository {
    /**
     * 주문 상세 정보 확인
     *
     * @param orderDetailId 주문상세 번호.
     * @return 주문 상세 정보 반환
     */
    Optional<GetOrderDetailResponseDto> getOrderDetailById(Long orderDetailId);


}
