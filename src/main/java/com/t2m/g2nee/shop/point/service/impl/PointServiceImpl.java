package com.t2m.g2nee.shop.point.service.impl;

import static com.t2m.g2nee.shop.point.domain.Point.ChangeReason.PHOTO_REVIEW;
import static com.t2m.g2nee.shop.point.domain.Point.ChangeReason.PURCHASE;
import static com.t2m.g2nee.shop.point.domain.Point.ChangeReason.RETURN;
import static com.t2m.g2nee.shop.point.domain.Point.ChangeReason.REVIEW;
import static com.t2m.g2nee.shop.point.domain.Point.ChangeReason.SIGNUP;
import static com.t2m.g2nee.shop.point.domain.Point.ChangeReason.USE;

import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.repository.MemberRepository;
import com.t2m.g2nee.shop.orderset.order.domain.Order;
import com.t2m.g2nee.shop.orderset.order.repository.OrderRepository;
import com.t2m.g2nee.shop.point.domain.Point;
import com.t2m.g2nee.shop.point.dto.request.PointCreateRequestDto;
import com.t2m.g2nee.shop.point.repository.PointRepository;
import com.t2m.g2nee.shop.point.service.PointService;
import com.t2m.g2nee.shop.policyset.pointpolicy.dto.response.PointPolicyInfoDto;
import com.t2m.g2nee.shop.policyset.pointpolicy.service.PointPolicyService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PointPolicyService pointPolicyService;

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 으로 username 에 해당하는 정보가 없는 경우 예외를 던집니다.
     */
    @Override
    public void savePoint(PointCreateRequestDto request) {
        Member member = memberRepository.findActiveMemberById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원 정보를 찾을 수 없습니다."));
        Order order = null;

        // 주문반품시 포인트 회수/반환을 위해 orderId가 저장된 경우 고려
        if (request.getOrderId() != null) {
            order = orderRepository.findById(request.getOrderId())
                    .orElseThrow(() -> new NotFoundException("주문 정보가 없습니다."));
        }
        Point point = Point.builder()
                .point(request.getPoint())
                .member(member)
                .order(order)
                .changeReason(request.getReason())
                .changeDate(LocalDateTime.now())
                .build();

        pointRepository.save(point);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveSignUpPoint(Member member) {
        PointPolicyInfoDto pointPolicyInfoDto = pointPolicyService.getPointPolicyByPointName(SIGNUP.getName());
        PointCreateRequestDto pointCreateRequestDto = new PointCreateRequestDto(
                member.getCustomerId(),
                null,
                Integer.parseInt(pointPolicyInfoDto.getAmount()),
                SIGNUP
        );
        savePoint(pointCreateRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveReviewPoint(Member member) {
        PointPolicyInfoDto pointPolicyInfoDto = pointPolicyService.getPointPolicyByPointName(REVIEW.getName());
        PointCreateRequestDto pointCreateRequestDto = new PointCreateRequestDto(
                member.getCustomerId(),
                null,
                Integer.parseInt(pointPolicyInfoDto.getAmount()),
                REVIEW
        );
        savePoint(pointCreateRequestDto);
    }

    @Override
    public void givePhotoReviewPoint(Member member) {
        PointPolicyInfoDto pointPolicyInfoDto = pointPolicyService.getPointPolicyByPointName(PHOTO_REVIEW.getName());
        PointCreateRequestDto pointCreateRequestDto = new PointCreateRequestDto(
                member.getCustomerId(),
                null,
                Integer.parseInt(pointPolicyInfoDto.getAmount()),
                PHOTO_REVIEW
        );
        savePoint(pointCreateRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void givePurchasePoint(Long memberId, Order order) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        PointPolicyInfoDto pointPolicyInfoDto =
                pointPolicyService.getPointPolicyByPointName(member.getGrade().getGradeName().getName());
        int point = new BigDecimal(pointPolicyInfoDto.getAmount()).multiply(order.getOrderAmount()).intValue();
        PointCreateRequestDto pointCreateRequestDto = new PointCreateRequestDto(
                member.getCustomerId(),
                order.getOrderId(),
                point,
                PURCHASE
        );
        savePoint(pointCreateRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void usePoint(Long memberId, Long orderId, int point) {
        int totalPoint = pointRepository.getTotalPoint(memberId);
        if (totalPoint - point < 0) {
            throw new BadRequestException("잔여 포인트가 부족합니다.");
        }
        PointCreateRequestDto pointCreateRequestDto = new PointCreateRequestDto(
                memberId,
                orderId,
                point * (-1),
                USE
        );
        savePoint(pointCreateRequestDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void returnPoint(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("order 정보가 없습니다."));
        Point point = pointRepository.findUsePointByOrderId(orderId)
                .orElseThrow(() -> new NotFoundException("orderId에 해당하는 사용된 포인트가 없습니다."));

        pointRepository.findReturnPointByOrderId(orderId).orElseThrow(
                () -> new BadRequestException("해당 주문에 사용된 포인트는 이미 반환되었습니다."));

        PointCreateRequestDto pointCreateRequestDto = new PointCreateRequestDto(
                order.getCustomer().getCustomerId(),
                orderId,
                point.getPoint(),
                RETURN
        );
        savePoint(pointCreateRequestDto);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getTotalPoint(Long memberId) {
        return pointRepository.getTotalPoint(memberId);
    }
}
