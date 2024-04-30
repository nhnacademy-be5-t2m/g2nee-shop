package com.t2m.g2nee.shop.bookset.category.service.impl;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.bookset.category.service.CategoryQueryService;
import com.t2m.g2nee.shop.exception.NotFoundException;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryQueryService의 구현체 입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    /**
     * 페이징 시, 보여줄 최대 버튼 수
     */
    private static final int MAXPAGEBUTTONS = 5;

    /**
     * CategoryQueryServiceImpl의 생성자입니다.
     *
     * @param categoryRepository 카테고리 레포지토리
     */
    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 카테고리가 존재하지 않을 경우
     */
    @Override
    public List<CategoryHierarchyDto> getSubCategories(Long categoryId) {
        //카테고리가 존재하는지 확인
        if (categoryRepository.existsById(categoryId)) {
            //존재하면 서브 카테고리 목록 반환
            return categoryRepository.getSubCategoriesByCategoryId(categoryId)
                    .stream()
                    .map(this::convertToCategoryHierarchyDto)
                    .collect(Collectors.toList());
        }
        //존재하지 않으면 예외 발생
        throw new NotFoundException("카테고리가 존재하지 않습니다.");
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 상위 카테고리가 유효한 id가 아닌 경우
     */
    @Override
    public CategoryUpdateDto getCategory(Long categoryId) {
        //카테고리와 카테고리의 상위 id를 가져옴
        CategoryUpdateDto category = categoryRepository.getFindByCategoryId(categoryId).orElseThrow(
                () -> new NotFoundException("상위 카테고리를 찾을 수 없습니다."));

        //서브 카테고리 목록 설정
        List<CategoryHierarchyDto> subCategories = getSubCategories(categoryId);
        category.setChildren(subCategories);
        setHierarchy(subCategories);

        return category;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<CategoryHierarchyDto> getRootCategories() {
        //카테고리를 계층 구조로 반환
        List<CategoryHierarchyDto> rootCategories = categoryRepository.getRootCategories().stream()
                .map(this::convertToCategoryHierarchyDto)
                .collect(Collectors.toList());

        for (CategoryHierarchyDto rootCategory : rootCategories) {
            List<CategoryHierarchyDto> subCategories = getSubCategories(rootCategory.getCategoryId());
            rootCategory.setChildren(subCategories);
            //하위 카테고리도 자식 설정
            setHierarchy(subCategories);
        }
        return rootCategories;
    }

    /**
     * 카테고리 계층 설정
     *
     * @param categories 계층 설정할 카테고리
     */
    public void setHierarchy(List<CategoryHierarchyDto> categories) {
        for (CategoryHierarchyDto category : categories) {
            //자식 카테고리 목록을 받아 설정
            List<CategoryHierarchyDto> subCategories = getSubCategories(category.getCategoryId());
            category.setChildren(subCategories);
            setHierarchy(subCategories);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PageResponse<CategoryInfoDto> getCategoriesByName(String name, int page) {
        //이름으로 검색한 카테고리를 페이징처리하여 반환
        Page<Category> categories = categoryRepository.findByCategoryNameContaining(name,
                PageRequest.of(page - 1, 10, Sort.by("categoryName")));

        List<CategoryInfoDto> categoryInfoDtoList = categories
                .stream().map(this::convertToCategoryInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, categories.getNumber() - Math.floor((double) MAXPAGEBUTTONS / 2));
        int endPage = Math.min(startPage + MAXPAGEBUTTONS - 1, categories.getTotalPages());

        if (endPage - startPage + 1 < MAXPAGEBUTTONS) {
            startPage = Math.max(1, endPage - MAXPAGEBUTTONS + 1);
        }

        return PageResponse.<CategoryInfoDto>builder()
                .data(categoryInfoDtoList)
                .currentPage(page)
                .totalPage(categories.getTotalPages())
                .startPage(startPage)
                .endPage(endPage)
                .totalElements(categories.getTotalElements())
                .build();
    }

    @Override
    public List<Long> getLowestCategory(List<Long> categoryList) {
        return categoryRepository.getLowestCategory(categoryList);
    }

    /**
     * Category 객체를 CategoryInfoDto로 변환
     *
     * @param category
     * @return
     */
    private CategoryInfoDto convertToCategoryInfoDto(Category category) {
        return new CategoryInfoDto(category);
    }

    /**
     * Category 객체를 CategoryHierarchyDto 변환
     *
     * @param category
     * @return
     */
    private CategoryHierarchyDto convertToCategoryHierarchyDto(Category category) {
        return new CategoryHierarchyDto(category);
    }
}
