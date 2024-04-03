package com.t2m.g2nee.shop.bookset.category.service.impl;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryBasicService;
import com.t2m.g2nee.shop.bookset.category.service.CategoryService;
import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.service.CategoryPathBasicService;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryService 구현체
 */
@Service
@Transactional
@Slf4j
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
    public CategoryInfoDto saveCategory(CategorySaveDto categorySaveDto) {
        Category category = new Category(
                categorySaveDto.getCategoryName(), categorySaveDto.getCategoryEngName()
        );
        categoryBasicService.saveCategoryBasic(category);

        //CategoryPaths 저장
        Long depth = 0L;

        categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, depth++));

        if (categorySaveDto.getAncestorCategoryId() != 0) {
            List<Category> ancestorIdList =
                    categoryBasicService.getAncestorList(categorySaveDto.getAncestorCategoryId());

            if (!ancestorIdList.isEmpty()) {
                for (Category ancestor : ancestorIdList) {
                    CategoryPath categoryPath = new CategoryPath(
                            ancestor, category, depth++
                    );

                    categoryPathBasicService.saveCategoryPath(categoryPath);
                }
            }
        }

        return new CategoryInfoDto(category);
    }

    /**
     * 카테고리 업데이트
     * 카테고리 수정 정보를 받아 업데이트
     *
     * @param categoryUpdateDto
     * @return
     */
    @Override
    public CategoryInfoDto updateCategory(CategoryUpdateDto categoryUpdateDto) {
        log.debug("in update!!!!!");
        Category category = new Category(
                categoryUpdateDto.getCategoryId(), categoryUpdateDto.getCategoryName(),
                categoryUpdateDto.getCategoryEngName()
        );
        categoryBasicService.updateCategoryBasic(category);

        //CategoryPaths 수정

        //기존 상위 카테고리 확인: 자기자신, 부모, 조부모~ 순으로 나옴
        List<Category> oldAncestorList = categoryBasicService.getAncestorList(categoryUpdateDto.getCategoryId());
        Long oldAncestorId = null;
        if (oldAncestorList.isEmpty()) {//삭제되어 경로가 없는 경우: 카테고리를 활성화로 변경
            categoryBasicService.activeCategory(categoryUpdateDto.getCategoryId());
        } else if (oldAncestorList.size() == 1) { //최상위 카테고리였음
            oldAncestorId = oldAncestorList.get(0).getCategoryId();
        } else { //그 밖의 경우
            oldAncestorId = oldAncestorList.get(1).getCategoryId();
        }

        //상위 카테고리 변경이 됐는지 확인
        //변경이 없으면 아무것도 안 함
        if (Objects.isNull(oldAncestorId) || !oldAncestorId.equals(categoryUpdateDto.getAncestorCategoryId())) {
            //soft delete한 카테고리 수정이나 카테고리가 변경되면 기존 경로 싹 지우고 다시 설정

            categoryPathBasicService.deleteCategoryPathBasic(categoryUpdateDto.getCategoryId());

            Long depth = 0L;

            categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, depth++));

            if (categoryUpdateDto.getAncestorCategoryId() != 0) {
                List<Category> ancestorIdList =
                        categoryBasicService.getAncestorList(categoryUpdateDto.getAncestorCategoryId());

                if (!ancestorIdList.isEmpty()) {
                    for (Category ancestor : ancestorIdList) {
                        CategoryPath categoryPath = new CategoryPath(
                                ancestor, category, depth++
                        );

                        categoryPathBasicService.saveCategoryPath(categoryPath);
                    }
                }
            }

        }

        return new CategoryInfoDto(category);
    }

    /**
     * categoryId에 해당하는 카테고리 삭제
     * 삭제하는 카테고리와 관련된 경로 삭제
     *
     * @param categoryId
     */
    @Override
    public boolean deleteCategory(Long categoryId) {
        categoryPathBasicService.deleteCategoryPathBasic(categoryId);
        return categoryBasicService.deleteCategoryBasic(categoryId);
    }

    /**
     * 비활성화된 카테고리 활성화
     *
     * @param categoryId
     * @return
     */
    @Override
    public boolean activeCategory(Long categoryId) {
        Category category = categoryBasicService.activeCategory(categoryId);
        categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, 0L));

        return category.isActive();
    }
}
