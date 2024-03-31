package com.t2m.g2nee.shop.bookset.category.service.impl;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryBasicService;
import com.t2m.g2nee.shop.bookset.category.service.CategoryService;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.service.CategoryPathBasicService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryService 구현체
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryBasicService categoryBasicService;

    private final CategoryPathBasicService categoryPathBasicService;

    public CategoryServiceImpl(CategoryBasicService categoryBasicService,
                               CategoryPathBasicService categoryPathBasicService) {
        this.categoryBasicService = categoryBasicService;
        this.categoryPathBasicService = categoryPathBasicService;
    }

    /**
     * 카테고리 저장 후,
     * 조장 카테고리를 모두 찾아 path 저장
     *
     * @param categorySaveDto
     * @return
     */

    @Override
    public Category saveCategory(CategorySaveDto categorySaveDto) {
        Category category = new Category(
                categorySaveDto.getCategoryName(), categorySaveDto.getCategoryEngName()
        );
        categoryBasicService.saveCategoryBasic(category);

        //CategoryPaths 저장
        Long depth = 0L;

        categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, depth++));

        List<Category> ancestorIdList = categoryBasicService.getAncestorList(categorySaveDto.getAncestorCategoryId());
        if (!ancestorIdList.isEmpty()) {
            for (Category ancestor : ancestorIdList) {
                CategoryPath categoryPath = new CategoryPath(
                        ancestor, category, depth++
                );

                categoryPathBasicService.saveCategoryPath(categoryPath);
            }
        }

        return category;
    }

    /**
     * 카테고리 업데이트
     * 카테고리 수정 정보를 받아 업데이트
     *
     * @param categoryUpdateDto
     * @return
     */
    @Override
    public Category updateCategory(CategoryUpdateDto categoryUpdateDto) {
        Category category = new Category(
                categoryUpdateDto.getCategoryId(), categoryUpdateDto.getCategoryName(),
                categoryUpdateDto.getCategoryEngName()
        );
        categoryBasicService.updateCategoryBasic(category);

        //CategoryPaths 수정

        Long depth = 1L;

        List<Category> ancestorIdList = categoryBasicService.getAncestorList(categoryUpdateDto.getAncestorCategoryId());
        for (Category ancestor : ancestorIdList) {
            CategoryPath categoryPath = new CategoryPath(
                    ancestor, category, depth++
            );

            categoryPathBasicService.saveCategoryPath(categoryPath);
        }

        return category;
    }

    /**
     * categoryId에 해당하는 카테고리 삭제
     * 삭제하는 카테고리와 관련된 경로 삭제
     *
     * @param categoryId
     */
    @Override
    public void deleteCategory(Long categoryId) {
        categoryBasicService.deleteCategoryBasic(categoryId);
        categoryPathBasicService.deleteCategoryPathBasic(categoryId);
    }
}
