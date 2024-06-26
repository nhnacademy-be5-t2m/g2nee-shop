package com.t2m.g2nee.shop.bookset.category.service.impl;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryBasicService;
import com.t2m.g2nee.shop.bookset.category.service.CategoryService;
import com.t2m.g2nee.shop.bookset.categorypath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categorypath.service.CategoryPathBasicService;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CategoryService의 구현체
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryBasicService categoryBasicService;

    private final CategoryPathBasicService categoryPathBasicService;

    /**
     * CategoryServiceImpl의 생성자입니다.
     *
     * @param categoryBasicService     카테고리의 기본 CURD를 하는 서비스
     * @param categoryPathBasicService 카테고리 경로의 기본 CURD를 하는 서비스
     */
    public CategoryServiceImpl(CategoryBasicService categoryBasicService,
                               CategoryPathBasicService categoryPathBasicService) {
        this.categoryBasicService = categoryBasicService;
        this.categoryPathBasicService = categoryPathBasicService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public CategoryInfoDto saveCategory(CategorySaveDto categorySaveDto) {
        //저장을 위해 카테고리 객체로 변환화여 저장
        Category category = new Category(
                categorySaveDto.getCategoryName(), categorySaveDto.getCategoryEngName(),
                categorySaveDto.getIsActivated()
        );

        categoryBasicService.saveCategoryBasic(category);

        //CategoryPaths 저장
        Long depth = 0L;

        //자기 자신의 path는 0으로 하여 저장
        categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, depth++));

        //상위 카테고리 id가 0인 경우: 최상위 카테고리 일 때이므로 경로 추가 없음
        //그 외의 경우: 하위 카테고리 이므로 경로 추가 필요
        if (!categorySaveDto.getAncestorCategoryId().equals(0L)) {
            //상위 카테고리를 검색: 저장할 카테고리와 depth가 적은 카테고리 순서로 반환됨
            List<Category> ancestorIdList =
                    categoryBasicService.getAncestorList(categorySaveDto.getAncestorCategoryId());

            if (!ancestorIdList.isEmpty()) {
                //비어있지 않으면
                for (Category ancestor : ancestorIdList) {
                    //depth를 증가하며 경로 추가
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
     * {@inheritDoc}
     */
    @Override
    public CategoryInfoDto updateCategory(Long categoryId, CategorySaveDto categorySaveDto) {
        //카테고리 저장을 위해 dto를 카테고리 객체로 변환
        Category category = new Category(
                categoryId, categorySaveDto.getCategoryName(),
                categorySaveDto.getCategoryEngName(),
                categorySaveDto.getIsActivated()
        );
        //카테고리 업데이트
        categoryBasicService.updateCategoryBasic(category);

        //CategoryPaths 수정

        //활성화일 경우에만 path 수정
        if (categorySaveDto.getIsActivated().booleanValue()) {
            //기존의 상위 카테고리 확인: 본인, 부모, 조부모~ 순으로 나옴
            List<Category> oldAncestorList = categoryBasicService.getAncestorList(categoryId);

            //기존 바로 위 카테고리id
            Long oldAncestorId = null;
            if (oldAncestorList.size() == 1) { //최상위 카테고리였음
                oldAncestorId = oldAncestorList.get(0).getCategoryId();
            } else if (!oldAncestorList.isEmpty()) { //그 밖의 경우는 모두 상위 카테고리가 1개는 최소 있음
                oldAncestorId = oldAncestorList.get(1).getCategoryId();
            }

            //null을 유지하는 경우, 비활성화 였던 것.

            //상위 카테고리 변경이 됐는지 확인: 변경이 없으면 path 수정은 필요 없음
            //soft delete한 카테고리 수정이나 상위 카테고리가 변경되면 기존 경로 지우고 다시 설정
            if (Objects.isNull(oldAncestorId) || !oldAncestorId.equals(categorySaveDto.getAncestorCategoryId())) {
                //기존 경로를 지움
                categoryPathBasicService.deleteCategoryPathBasic(categoryId);

                //다시 저장(save와 같은 로직)
                Long depth = 0L;

                categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, depth++));

                if (!categorySaveDto.getAncestorCategoryId().equals(0L)) {
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

            }

        } else {//비활성화 시, 경로 삭제
            categoryPathBasicService.deleteCategoryPathBasic(categoryId);
        }


        return new CategoryInfoDto(category);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteCategory(Long categoryId) {
        //삭제하는 카테고리와 관련된 경로 삭제 
        categoryPathBasicService.deleteCategoryPathBasic(categoryId);
        //카테고리는 soft delete
        return categoryBasicService.deleteCategoryBasic(categoryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean activeCategory(Long categoryId) {
        //비활성화 카테고리를 활성화로 변환
        Category category = categoryBasicService.activeCategory(categoryId);
        //최상위 카테고리로 변환하여 path 추가
        categoryPathBasicService.saveCategoryPath(new CategoryPath(category, category, 0L));

        return category.getIsActivated();
    }
}
