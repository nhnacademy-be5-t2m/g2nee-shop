package com.t2m.g2nee.shop.bookset.categorypath.service.impl;


import com.t2m.g2nee.shop.bookset.categorypath.domain.CategoryPath;
import com.t2m.g2nee.shop.bookset.categorypath.repository.CategoryPathRepository;
import com.t2m.g2nee.shop.bookset.categorypath.service.CategoryPathBasicService;
import com.t2m.g2nee.shop.exception.AlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * CategoryPathBasicService의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
@Transactional
public class CategoryPathBasicServiceImpl implements CategoryPathBasicService {

    private final CategoryPathRepository categoryPathRepository;

    /**
     * CategoryPathBasicServiceImpl의 생성자 입니다.
     *
     * @param categoryPathRepository
     */
    public CategoryPathBasicServiceImpl(CategoryPathRepository categoryPathRepository) {
        this.categoryPathRepository = categoryPathRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @throws AlreadyExistException 카테고리 경로가 존재할 경우
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCategoryPathBasic(Long categoryId) {
        //관련된 조상 및 후손 경로 삭제
        categoryPathRepository.deleteCategoryPathByAncestorIdAndDescendantId(categoryId);
    }
}
