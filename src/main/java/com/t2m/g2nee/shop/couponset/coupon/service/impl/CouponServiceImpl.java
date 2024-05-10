package com.t2m.g2nee.shop.couponset.coupon.service.impl;

import com.t2m.g2nee.shop.couponset.bookcoupon.domain.BookCoupon;
import com.t2m.g2nee.shop.couponset.bookcoupon.service.BookCouponService;
import com.t2m.g2nee.shop.couponset.categorycoupon.domain.CategoryCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.service.CategoryCouponService;
import com.t2m.g2nee.shop.couponset.coupon.domain.Coupon;
import com.t2m.g2nee.shop.couponset.coupon.dto.request.CouponDownloadDto;
import com.t2m.g2nee.shop.couponset.coupon.dto.request.CouponIssueDto;
import com.t2m.g2nee.shop.couponset.coupon.dto.response.CouponInfoDto;
import com.t2m.g2nee.shop.couponset.coupon.repository.CouponRepository;
import com.t2m.g2nee.shop.couponset.coupon.repository.CouponSaveRepository;
import com.t2m.g2nee.shop.couponset.coupon.service.CouponService;
import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.exception.BadRequestException;
import com.t2m.g2nee.shop.memberset.member.domain.Member;
import com.t2m.g2nee.shop.memberset.member.service.MemberService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponServiceImpl implements CouponService {

    private static final int MAXPAGEBUTTONS = 5;
    private final CouponRepository couponRepository;
    private final MemberService memberService;
    private final CouponTypeService couponTypeService;
    private final CategoryCouponService categoryCouponService;
    private final BookCouponService bookCouponService;
    private final CouponSaveRepository couponSaveRepository;


    public CouponServiceImpl(CouponRepository couponRepository, MemberService memberService,
                             CouponTypeService couponTypeService, CategoryCouponService categoryCouponService,
                             BookCouponService bookCouponService, CouponSaveRepository couponSaveRepository) {
        this.couponRepository = couponRepository;
        this.memberService = memberService;
        this.couponTypeService = couponTypeService;
        this.categoryCouponService = categoryCouponService;
        this.bookCouponService = bookCouponService;
        this.couponSaveRepository = couponSaveRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CouponInfoDto issueCoupon(CouponIssueDto request) {
        List<Member> members = memberService.getAllMembers();
        if (!members.isEmpty()) {
            CouponType couponType = couponTypeService.getCoupon(request.getCouponTypeId());

            if (!couponType.getStatus().equals(CouponType.CouponTypeStatus.BATCH)) {
                throw new BadRequestException("올바르지 않은 쿠폰 발급입니다.");
            }

            List<Coupon> coupons = members.stream()
                    .map(member -> Coupon.builder()
                            .issuedDate(LocalDateTime.now())
                            .expirationDate(LocalDateTime.now().plusDays(couponType.getPeriod()))
                            .status(Coupon.CouponStatus.NOTUSED)
                            .member(member)
                            .couponType(couponType)
                            .build())
                    .collect(Collectors.toList());

            couponSaveRepository.saveAll(coupons);

            return convertToCouponInfoDto(coupons.get(0));
        }

        throw new BadRequestException("쿠폰을 발급할 회원이 없습니다.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CouponInfoDto downloadCoupon(CouponDownloadDto request) {
        Member member = memberService.getMember(request.getCustomerId());
        CouponType couponType = couponTypeService.getCoupon(request.getCouponTypeId());
        if (!couponType.getStatus().equals(CouponType.CouponTypeStatus.INDIVIDUAL)) {
            throw new BadRequestException("올바르지 않은 쿠폰 발급입니다.");
        }

        Coupon coupon = Coupon.builder()
                .issuedDate(LocalDateTime.now())
                .expirationDate(LocalDateTime.now().plusDays(couponType.getPeriod()))
                .status(Coupon.CouponStatus.NOTUSED)
                .member(member)
                .couponType(couponType)
                .build();

        return convertToCouponInfoDto(couponRepository.save(coupon));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<CouponInfoDto> getMyCoupons(Long customerId, int page) {
        Page<Coupon> myCoupons = couponRepository.getCouponsByCustomerId(customerId,
                PageRequest.of(page - 1, 10));

        List<CouponInfoDto> couponInfoDtoList = myCoupons
                .stream().map(this::convertToCouponInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, myCoupons.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, myCoupons.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<CouponInfoDto>builder()
                .data(couponInfoDtoList)
                .currentPage(page)
                .totalPage(myCoupons.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(myCoupons.getTotalElements())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<CouponInfoDto> getBookCoupons(Long customerId, Long bookId, int page) {
        Page<Coupon> coupons = couponRepository.getBookCoupons(customerId, bookId,
                PageRequest.of(page - 1, 10));

        List<CouponInfoDto> couponInfoDtoList = coupons
                .stream().map(this::convertToCouponInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, coupons.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, coupons.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<CouponInfoDto>builder()
                .data(couponInfoDtoList)
                .currentPage(page)
                .totalPage(coupons.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(coupons.getTotalElements())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public PageResponse<CouponInfoDto> getTotalCoupons(Long customerId, int page) {
        Page<Coupon> coupons = couponRepository.getTotalCoupons(customerId,
                PageRequest.of(page - 1, 10));

        List<CouponInfoDto> couponInfoDtoList = coupons
                .stream().map(this::convertToCouponInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, coupons.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, coupons.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<CouponInfoDto>builder()
                .data(couponInfoDtoList)
                .currentPage(page)
                .totalPage(coupons.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(coupons.getTotalElements())
                .build();
    }


    private CouponInfoDto convertToCouponInfoDto(Coupon coupon) {

        String discount = coupon.getCouponType().getDiscount().toString();
        if (coupon.getCouponType().getType().equals(CouponType.Type.AMOUNT)) {
            //금액의 경우 소수점을 떼고 보여줌
            int dot = discount.indexOf(".");
            if (dot != -1) {
                discount = discount.substring(0, dot);
            }
        }

        //쿠폰 대상 선택
        String target = "전체";
        BookCoupon bookCoupon = bookCouponService.getBookCoupon(coupon.getCouponType().getCouponTypeId());
        if (Objects.nonNull(bookCoupon)) {
            target = bookCoupon.getBook().getTitle();
        }
        CategoryCoupon categoryCoupon =
                categoryCouponService.getCategoryCoupon(coupon.getCouponType().getCouponTypeId());
        if (Objects.nonNull(categoryCoupon)) {
            target = categoryCoupon.getCategory().getCategoryName();
        }

        return new CouponInfoDto(
                coupon.getCouponId(),
                coupon.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                coupon.getIssuedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                coupon.getStatus().getName(), discount, coupon.getCouponType().getMaximumDiscount(),
                coupon.getCouponType().getMinimumOrderAmount(), coupon.getCouponType().getName(),
                coupon.getCouponType().getPeriod(), coupon.getCouponType().getType().getName(),
                target
        );
    }
}
