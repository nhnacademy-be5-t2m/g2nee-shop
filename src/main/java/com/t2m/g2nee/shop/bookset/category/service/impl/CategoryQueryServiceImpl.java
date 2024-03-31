package com.t2m.g2nee.shop.bookset.category.service.impl;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.bookset.category.service.CategoryQueryService;
import com.t2m.g2nee.shop.exception.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /*
     * getSubCategories: categoryId의 바로 1단계 아래의 하위 카테고리들을 출력하는 쿼리
     *      categoryId가 존재하지 않으면 예외를 던지고
     *      존재하면 Page<CategoryInfoDto>를 리턴
     */
    @Override
    public Page<CategoryInfoDto> getSubCategories(Long categoryId, Pageable pageable) {
        if (categoryRepository.existsById(categoryId)) {
            Page<Category> subCategories = categoryRepository.getSubCategoriesByCategoryId(categoryId, pageable);
            List<CategoryInfoDto> categoryInfoDtoList = subCategories.getContent().stream()
                    .map(this::convertToCategoryInfoDto)
                    .collect(Collectors.toList());

            return new PageImpl<>(categoryInfoDtoList, pageable, subCategories.getTotalElements());
        }
        throw new NotFoundException("카테고리가 존재하지 않습니다.");
    }

    /*
     * getSubCategories: categoryId에 해당하는 Category 객체를 리턴
     *      categoryId가 존재하지 않으면 예외를 던짐
     */
    @Override
    public CategoryInfoDto getCategory(Long categoryId) {
        return convertToCategoryInfoDto(
                categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다.")));
    }

    /**
     * 최상위 카테고리를 페이징처리하여 반환
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<CategoryInfoDto> getRootCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<CategoryInfoDto> categoryInfoDtoList = categories.getContent().stream()
                .map(this::convertToCategoryInfoDto)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryInfoDtoList, pageable, categories.getTotalElements());
    }

    /**
     * 모든 카테고리를 페이징처리하여 반환
     *
     * @param pageable
     * @return
     */

    @Override
    public Page<CategoryInfoDto> getAllCategories(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);

        List<CategoryInfoDto> categoryInfoDtoList = categories
                .stream().map(this::convertToCategoryInfoDto)
                .collect(Collectors.toList());

        return new PageImpl<>(categoryInfoDtoList, pageable, categories.getTotalElements());
    }

    /**
     * category 객체를 CategoryInfoDto로 변환
     */
    private CategoryInfoDto convertToCategoryInfoDto(Category category) {
        return new CategoryInfoDto(category);
    }
}
