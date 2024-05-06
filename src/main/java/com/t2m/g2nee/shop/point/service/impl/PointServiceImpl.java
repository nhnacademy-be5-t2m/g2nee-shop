package com.t2m.g2nee.shop.point.service.impl;

import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.point.domain.Point;
import com.t2m.g2nee.shop.point.dto.request.PointCreateRequestDto;
import com.t2m.g2nee.shop.point.repository.PointRepository;
import com.t2m.g2nee.shop.point.service.PointService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 포인트 정보를 위한 service 입니다.
 *
 * @author : 정지은
 * @since : 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PointServiceImpl implements PointService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final PointRepository pointRepository;

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Override
    public void savePoint(PointCreateRequestDto request) {
        Member member = memberRepository.findActiveMemberById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        Point point = new Point();
        Order order = null;

        // 주문반품시 포인트 회수/반환을 위해 orderId가 저장된 경우 고려
        if (request.getOrderId() != null) {
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new NotFoundException("주문 정보가 없습니다."));
        }

        point.builder()
                .point(request.getPoint())
                .member(member)
                .changeReason(request.getReason())
                .order(order)
                .build();

        pointRepository.save(point);
    }
}
