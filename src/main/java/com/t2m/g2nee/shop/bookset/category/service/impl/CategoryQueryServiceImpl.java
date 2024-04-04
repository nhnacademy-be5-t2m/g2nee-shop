package com.t2m.g2nee.shop.bookset.category.service.impl;

import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
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

@Service
@Transactional(readOnly = true)
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    private static int maxPageButtons = 5;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryInfoDto> getSubCategories(Long categoryId) {
        //카테고리가 존재하는지 확인
        if (categoryRepository.existsById(categoryId)) {
            //존재하면 서브 카테고리 목록 반환
            return categoryRepository.getSubCategoriesByCategoryId(categoryId)
                    .stream()
                    .map(this::convertToCategoryInfoDto)
                    .collect(Collectors.toList());
        }
        //존재하지 않으면 예외 발생
        throw new NotFoundException("카테고리가 존재하지 않습니다.");
    }

    @Override
    public CategoryInfoDto getCategory(Long categoryId) {
        //카테고리 단일 객체 반환, 존재 하지 않으면 예외 발생
        return convertToCategoryInfoDto(
                categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다.")));
    }


    @Override
    public List<CategoryInfoDto> getRootCategories() {
        //최상위 카테고리를 페이징처리하여 반환
        return categoryRepository.getRootCategories().stream()
                .map(this::convertToCategoryInfoDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<CategoryInfoDto> getAllCategories(int page) {
        //모든 카테고리를 페이징처리하여 반환
        Page<Category> categories = categoryRepository.findAll(
                PageRequest.of(page - 1, 10, Sort.by("categoryName"))
        );

        List<CategoryInfoDto> categoryInfoDtoList = categories
                .stream().map(this::convertToCategoryInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, categories.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, categories.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
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
    public PageResponse<CategoryInfoDto> getCategoriesByName(String name, int page) {
        //이름으로 검색한 카테고리를 페이징처리하여 반환
        Page<Category> categories = categoryRepository.findByCategoryNameContaining(name,
                PageRequest.of(page - 1, 10, Sort.by("categoryName")));

        List<CategoryInfoDto> categoryInfoDtoList = categories
                .stream().map(this::convertToCategoryInfoDto)
                .collect(Collectors.toList());

        int startPage = (int) Math.max(1, categories.getNumber() - Math.floor((double) maxPageButtons / 2));
        int endPage = Math.min(startPage + maxPageButtons - 1, categories.getTotalPages());

        if (endPage - startPage + 1 < maxPageButtons) {
            startPage = Math.max(1, endPage - maxPageButtons + 1);
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

    /**
     * 카테고리 객체를 CategoryInfoDto로 변환
     *
     * @param category
     * @return
     */
    private CategoryInfoDto convertToCategoryInfoDto(Category category) {
        return new CategoryInfoDto(category);
    }
}
