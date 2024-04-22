package com.t2m.g2nee.shop.bookset.categoryPath.service.impl;


import com.t2m.g2nee.shop.bookset.categoryPath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categoryPath.repository.CategoryPathRepository;
import com.t2m.g2nee.shop.bookset.categoryPath.service.CategoryPathBasicService;
import com.t2m.g2nee.shop.exception.AlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * CategoryPathBasicService의 구현체
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional
public class CategoryPathBasicServiceImpl implements CategoryPathBasicService {

    private final CategoryPathRepository categoryPathRepository;

    public CategoryPathBasicServiceImpl(CategoryPathRepository categoryPathRepository) {
        this.categoryPathRepository = categoryPathRepository;
    }

    @Override
    public CategoryPath saveCategoryPath(CategoryPath categoryPath) {
        //(조상, 후손) 경로가 존재하는 지 확인
        if (categoryPathRepository.existsByAncestorAndDescendant(categoryPath.getAncestor(),
                categoryPath.getDescendant())) {
            //존재하면 예외 발생
            throw new AlreadyExistException("이미 존재하는 카테고리 경로입니다.");
        } else {
            //새로운 경로면 저장
            return categoryPathRepository.save(categoryPath);
        }
    }

    @Override
    public void deleteCategoryPathBasic(Long categoryId) {
        //관련된 조상 및 후손 경로 삭제
        categoryPathRepository.deleteCategoryPathByAncestorIdAndDescendantId(categoryId);
    }
}
