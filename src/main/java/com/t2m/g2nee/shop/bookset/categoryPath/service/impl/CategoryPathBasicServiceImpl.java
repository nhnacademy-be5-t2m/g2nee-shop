package com.t2m.g2nee.shop.bookset.categoryPath.service.impl;


import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.repository.CategoryPathRepository;
import com.t2m.g2nee.shop.bookset.categoryPath.service.CategoryPathBasicService;
import com.t2m.g2nee.shop.exception.AlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * CategoryPathBasicService의 구현체
 */
@Service
public class CategoryPathBasicServiceImpl implements CategoryPathBasicService {

    private final CategoryPathRepository categoryPathRepository;

    public CategoryPathBasicServiceImpl(CategoryPathRepository categoryPathRepository) {
        this.categoryPathRepository = categoryPathRepository;
    }

    /**
     * 카테고리 경로 저장
     * (조상, 후순) 쌍이 이미 존재하면 존재하는 경로라는 AlreadyExistCategoryPath 예외 발생
     *
     * @param categoryPath
     * @return
     */
    @Override
    public CategoryPath saveCategoryPath(CategoryPath categoryPath) {
        if (categoryPathRepository.existsByAncestorAndDescendant(categoryPath.getAncestor(),
                categoryPath.getDescendant())) {
            throw new AlreadyExistException("이미 존재하는 카테고리 경로입니다.");
        } else {
            return categoryPathRepository.save(categoryPath);
        }
    }

    /**
     * 카테고리 경로 삭제
     * 카테고리가 삭제될 때, 관련된 조상 및 후손 경로도 삭제
     *
     * @param categoryId
     */
    @Override
    @Transactional
    public void deleteCategoryPathBasic(Long categoryId) {
        categoryPathRepository.deleteByDescendant_CategoryId(categoryId);
        categoryPathRepository.deleteByAncestor_CategoryId(categoryId);
    }
}
