package com.t2m.g2nee.shop.couponset.coupontype.service.impl;



import com.t2m.g2nee.shop.bookset.book.domain.Book;
import com.t2m.g2nee.shop.bookset.book.repository.BookRepository;
import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.couponset.bookcoupon.domain.BookCoupon;
import com.t2m.g2nee.shop.couponset.bookcoupon.repository.BookCouponRepository;
import com.t2m.g2nee.shop.couponset.categorycoupon.domain.CategoryCoupon;
import com.t2m.g2nee.shop.couponset.categorycoupon.repository.CategoryCouponRepository;
import com.t2m.g2nee.shop.couponset.coupontype.domain.CouponType;
import com.t2m.g2nee.shop.couponset.coupontype.dto.CouponTypeInfoDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.request.CouponTypeRequestDto;
import com.t2m.g2nee.shop.couponset.coupontype.dto.response.CouponTypeCreatedDto;
import com.t2m.g2nee.shop.couponset.coupontype.repository.CouponTypeRepository;
import com.t2m.g2nee.shop.couponset.coupontype.service.CouponTypeService;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 쿠폰 controller 클래스
 *
 * @author :김수현
 * @since :1.0
 */
@Service
@RequiredArgsConstructor
public class CouponTypeServiceImpl implements CouponTypeService {



    private final CouponTypeRepository couponTypeRepository;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookCouponRepository bookCouponRepository;
    private final CategoryCouponRepository categoryCouponRepository;
    private static final int MAXPAGEBUTTONS = 5;


    /**
     * admin에서 모든 쿠폰을 조회할 수 있는 Service
     * @param page
     * @return
     */
    @Override
    public PageResponse<CouponTypeInfoDto> getAllCouponTypes(int page) {

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


        /**
         * admin에서 일반 쿠폰을 생성할 수 있는 Service
         * @param couponTypeRequestDto
         * @return
         */
    @Override
    public CouponTypeCreatedDto createCouponType(CouponTypeRequestDto couponTypeRequestDto) {



        CouponType couponTypeInfo = CouponType.builder()
                .couponTypeId(couponTypeRequestDto.getCouponTypeId())
                .name(couponTypeRequestDto.getName())
                .period(couponTypeRequestDto.getPeriod())
                .type(couponTypeRequestDto.getType())
                .discount(couponTypeRequestDto.getDiscount())
                .type(couponTypeRequestDto.getType())
                .discount(couponTypeRequestDto.getDiscount())
                .minimumOrderAmount(couponTypeRequestDto.getMinimumOrderAmount())
                .maximumDiscount(couponTypeRequestDto.getMaximumDiscount())
                .status(couponTypeRequestDto.getStatus())
                .build();

        CouponType savedCouponType =  couponTypeRepository.save(couponTypeInfo);


        return new CouponTypeCreatedDto(savedCouponType.getPeriod(), savedCouponType.getName(), savedCouponType.getType(), savedCouponType.getDiscount(),savedCouponType.getMinimumOrderAmount(),savedCouponType.getMaximumDiscount(),savedCouponType.getStatus());

    }

    /**
     * admin에서 BookCoupon을 생성할 수 있는 Service
     * @param couponTypeRequestDto
     * @return
     */
    @Override
    public CouponTypeCreatedDto createBookCoupon(CouponTypeRequestDto couponTypeRequestDto) {

        Book book = bookRepository.findById(couponTypeRequestDto.getBookId()).orElseThrow(() -> new NotFoundException("Book을 찾을 수 없습니다 " + couponTypeRequestDto.getBookId()));


        BookCoupon bookCouponInfo = BookCoupon.builder()
                .couponTypeId(couponTypeRequestDto.getCouponTypeId())
                .name(couponTypeRequestDto.getName())
                .period(couponTypeRequestDto.getPeriod())
                .type(couponTypeRequestDto.getType())
                .discount(couponTypeRequestDto.getDiscount())
                .type(couponTypeRequestDto.getType())
                .discount(couponTypeRequestDto.getDiscount())
                .minimumOrderAmount(couponTypeRequestDto.getMinimumOrderAmount())
                .maximumDiscount(couponTypeRequestDto.getMaximumDiscount())
                .status(couponTypeRequestDto.getStatus())
                .book(book)
                .build();

        BookCoupon savedBookCoupon = bookCouponRepository.save(bookCouponInfo);

        return new CouponTypeCreatedDto(savedBookCoupon.getPeriod(), savedBookCoupon.getName(), savedBookCoupon.getType(), savedBookCoupon.getDiscount(),savedBookCoupon.getMinimumOrderAmount(),savedBookCoupon.getMaximumDiscount(),savedBookCoupon.getStatus());
    }

    /**
     * admin에서 CategoryCoupon을 생성할 수 있는 Service
     * @param couponTypeRequestDto
     * @return
     */
    @Override
    public CouponTypeCreatedDto createCategoryCoupon(CouponTypeRequestDto couponTypeRequestDto) {

        Category category = categoryRepository.findById(couponTypeRequestDto.getCategoryId()).orElseThrow(() -> new NotFoundException("Category를 찾을 수 없습니다 " + couponTypeRequestDto.getBookId()));

        CategoryCoupon categoryCouponInfo = CategoryCoupon.builder()
                .couponTypeId(couponTypeRequestDto.getCouponTypeId())
                .name(couponTypeRequestDto.getName())
                .period(couponTypeRequestDto.getPeriod())
                .type(couponTypeRequestDto.getType())
                .discount(couponTypeRequestDto.getDiscount())
                .type(couponTypeRequestDto.getType())
                .discount(couponTypeRequestDto.getDiscount())
                .minimumOrderAmount(couponTypeRequestDto.getMinimumOrderAmount())
                .maximumDiscount(couponTypeRequestDto.getMaximumDiscount())
                .status(couponTypeRequestDto.getStatus())
                .category(category)
                .build();

        CategoryCoupon savedCategoryCoupon = categoryCouponRepository.save(categoryCouponInfo);


        return new CouponTypeCreatedDto(savedCategoryCoupon.getPeriod(), savedCategoryCoupon.getName(), savedCategoryCoupon.getType(), savedCategoryCoupon.getDiscount(),savedCategoryCoupon.getMinimumOrderAmount(),savedCategoryCoupon.getMaximumDiscount(),savedCategoryCoupon.getStatus());
    }


    private CouponTypeInfoDto convertToDTO(CouponTypeInfoDto couponTypeInfoDto) {
        String discount = couponTypeInfoDto.getDiscount().toString();
        if (couponTypeInfoDto.getType().equals("AMOUNT")) {
            //금액의 경우 소수점을 떼고 보여줌
            int dot = discount.indexOf(".");
            if (dot != -1) {
                discount = discount.substring(0, dot);
            }
        }

        if (couponTypeInfoDto.getType().equals("PERCENT")) {
            //금액의 경우 *100 후에 보여줌
            int percentValue = (int) (Double.parseDouble(discount) * 100);
            discount = String.valueOf(percentValue);
        }

        CouponTypeInfoDto dto = CouponTypeInfoDto.builder()
                .couponTypeId(couponTypeInfoDto.getCouponTypeId())
                .couponTypeName(couponTypeInfoDto.getCouponTypeName())
                .period(couponTypeInfoDto.getPeriod())
                .discount(new BigDecimal(discount))
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

    @Override
    public CouponType getCoupon(Long couponTypeId) {
        return couponTypeRepository.findById(couponTypeId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 쿠폰입니다."));
    }
}











