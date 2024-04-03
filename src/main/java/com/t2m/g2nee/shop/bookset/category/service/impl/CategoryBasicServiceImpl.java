package com.t2m.g2nee.shop.bookset.category.service.impl;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.bookset.category.service.CategoryBasicService;
import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * CategoryBasicService의 구현체
 */
@Service
public class CategoryBasicServiceImpl implements CategoryBasicService {

    private final CategoryRepository categoryRepository;

    public CategoryBasicServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * saveCategoryBasic: Category를 저장함
     * 같은 이름이 존재하는 경우, AlreadyExistCategoryName 예외를 던짐
     * 존재하지 않아 새로운 카테고리면, Category를 Categories 테이블에 저장
     */
    @Override
    public Category saveCategoryBasic(Category category) {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new AlreadyExistException("이미 존재하는 카테고리 명 입니다.");
        } else {
            return categoryRepository.save(category);
        }
    }

    /**
     * updateCategoryBasic: Category를 수정함
     * 카테고리가 존재하는지 확인하여 존재하지 않으면 NotFoundCategory 예외를 던짐
     * 존재하면, 수정된 Category를 Categories 테이블에 저장
     */
    @Override
    public Category updateCategoryBasic(Category category) {
        if (categoryRepository.existsById(category.getCategoryId())) {
            return categoryRepository.save(category);
        } else {
            throw new NotFoundException("업데이트 할 카테고리를 찾을 수 없습니다.");
        }
    }

    /**
     * deleteCategoryBasic: Category를 삭제함
     * 카테고리가 존재하는지 확인하여 존재하지 않으면 NotFoundCategory 예외를 던짐
     * 존재하면, categoryId에 해당하는 Category를 Categories테이블에서 상테를 바꿈
     */
    @Override
    public boolean deleteCategoryBasic(Long categoryId) {
        if (categoryRepository.getExistsByCategoryIdAndIsActive(categoryId, true)) {
            categoryRepository.softDeleteByCategoryId(categoryId);
            return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다."))
                    .isActive();
        } else {
            throw new NotFoundException("삭제할 카테고리가 존재하지 않습니다.");
        }
    }

    @Override
    public List<Category> getAncestorList(Long descendantId) {
        if (categoryRepository.existsById(descendantId)) {
            return categoryRepository.getFindAncestorIdsByCategoryId(descendantId);
        } else {
            throw new NotFoundException("카테고리가 존재하지 않습니다.");
        }
    }

    @Override
    public Category activeCategory(Long categoryId) {
        if (categoryRepository.getExistsByCategoryIdAndIsActive(categoryId, false)) {
            categoryRepository.activeCategoryByCategoryId(categoryId);
            return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다."));
        } else {
            throw new NotFoundException("활성화할 카테고리가 존재하지 않습니다.");
        }
    }

}
