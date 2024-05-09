package com.t2m.g2nee.shop.couponset.coupontype.service.impl;



import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CouponTypeRepository;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 쿠폰 service 클래스
 *
 * @author :김수현
 * @since :1.0
 */
@Service
@RequiredArgsConstructor
public class CouponTypeServiceImpl implements CouponTypeService {



    private final CouponTypeRepository couponTypeRepository;
    private static final int MAXPAGEBUTTONS = 5;




    @Override
    public PageResponse<CouponTypeInfoDto> getAllCoupons(int page) {

        int size = 10;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt"));

        Page<CouponTypeInfoDto> couponPage = couponTypeRepository.getAllCoupons(pageable);

        List<CouponTypeInfoDto> couponTypeInfoDtoList = couponPage.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());


        int startPage = (int) Math.max(1, couponPage.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, couponPage.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<CouponTypeInfoDto>builder()
                .data(couponTypeInfoDtoList)
                .currentPage(page)
                .totalPage(couponPage.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(couponPage.getTotalElements())
                .build();


    }

    private CouponTypeInfoDto convertToDTO(CouponTypeInfoDto couponTypeInfoDto) {

        CouponTypeInfoDto dto = CouponTypeInfoDto.builder()
                .couponTypeId(couponTypeInfoDto.getCouponTypeId())
                .couponTypeName(couponTypeInfoDto.getCouponTypeName())
                .period(couponTypeInfoDto.getPeriod())
                .discount(couponTypeInfoDto.getDiscount())
                .minimumOrderAmount(couponTypeInfoDto.getMinimumOrderAmount())
                .maximumDiscount(couponTypeInfoDto.getMaximumDiscount())
                .categoryId(couponTypeInfoDto.getCategoryId())
                .bookId(couponTypeInfoDto.getBookId())
                .status(couponTypeInfoDto.getStatus())
                .build();

        if ("AMOUNT".equals(couponTypeInfoDto.getType())) {

            dto.setType(CouponType.Type.AMOUNT.getName());

        } else if ("PERCENT".equals(couponTypeInfoDto.getType())) {

            dto.setType(CouponType.Type.PERCENT.getName());
        }

        if ("DELETE".equals(couponTypeInfoDto.getStatus())) {

            dto.setStatus(CouponType.CouponTypeStatus.DELETE.getName());

        } else if ("BATCH".equals(couponTypeInfoDto.getStatus())) {

            dto.setStatus(CouponType.CouponTypeStatus.BATCH.getName());

        } else if ("INDIVIDUAL".equals(couponTypeInfoDto.getStatus())) {

            dto.setStatus(CouponType.CouponTypeStatus.INDIVIDUAL.getName());
        }

        return dto;
    }

}











