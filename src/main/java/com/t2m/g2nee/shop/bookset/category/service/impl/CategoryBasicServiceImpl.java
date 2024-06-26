package com.t2m.g2nee.shop.bookset.category.service.impl;


import com.t2m.g2nee.shop.bookset.category.domain.Category;
import com.t2m.g2nee.shop.bookset.category.repository.CategoryRepository;
import com.t2m.g2nee.shop.bookset.category.service.CategoryBasicService;
import com.t2m.g2nee.shop.exception.AlreadyExistException;
import com.t2m.g2nee.shop.exception.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * CategoryBasicService의 구현체입니다.
 *
 * @author : 김수빈
 * @since : 1.0
 */
@Service
public class CategoryBasicServiceImpl implements CategoryBasicService {


    private final CategoryRepository categoryRepository;

    /**
     * CategoryBasicServiceImpl의 생성자입니다.
     *
     * @param categoryRepository 카테고리 레포지토리
     */
    public CategoryBasicServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * {@inheritDoc}
     *
     * @throws AlreadyExistException 카테고리 이름이 중복될 경우
     */
    @Override
    public Category saveCategoryBasic(Category category) {
        //같은 이름이 존재하는 지 확인
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            //같은 이름이 존재하는 경우 예외 발생
            throw new AlreadyExistException("이미 존재하는 카테고리 명 입니다.");
        } else {
            //중복되지 않은 새로운 카테고리라면 저장
            return categoryRepository.save(category);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException     수정 할 카테고리가 존재하지 않을 때
     * @throws AlreadyExistException 수정하는 카테고리 이름이 중복될 때
     */
    @Override
    public Category updateCategoryBasic(Category category) {
        //카테고리가 존재하는지 확인
        if (!categoryRepository.existsById(category.getCategoryId())) {
            //카테고리가 존재하지 않으면 예외 발생
            throw new NotFoundException("업데이트 할 카테고리를 찾을 수 없습니다.");
        } else if (categoryRepository.existsByCategoryNameAndCategoryIdNot(category.getCategoryName(),
                category.getCategoryId())) {
            //동일한 이름의 카테고리로 수정할 수 없게 함
            throw new AlreadyExistException("이미 존재하는 카테고리 명 입니다.");
        } else {
            //수정된 내용을 저장
            return categoryRepository.save(category);

        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 삭제할 카테고리 id가 유효하지 않을 때
     */
    @Override
    public boolean deleteCategoryBasic(Long categoryId) {
        //해당 카테고리가 활성화 상태이면서 존재하는지 확인
        if (categoryRepository.getExistsByCategoryIdAndisActivated(categoryId, true)) {
            //존재하는 경우 카테고리를 soft delete함
            categoryRepository.softDeleteByCategoryId(categoryId);
            //카테고리의 활성화 상태를 반환
            return false;
        } else {
            //존재 하지 않으면 예외 발생
            throw new NotFoundException("삭제할 카테고리가 존재하지 않습니다.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 카테고리가 존재하지 않을 때
     */
    @Override
    public List<Category> getAncestorList(Long descendantId) {
        //카테고리가 존재하는지 확인
        if (categoryRepository.existsById(descendantId)) {
            //존재하면 카테고리의 조상 목록을 반환
            return categoryRepository.getFindAncestorIdsByCategoryId(descendantId);
        } else {
            //존재하지 않으면 예외 발생
            throw new NotFoundException("카테고리가 존재하지 않습니다.");
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException 활성화할 카테고리 id가 유효하지 않을 때
     * @throws NotFoundException 유효한 카테고리 id가 아닐 때
     */
    @Override
    public Category activeCategory(Long categoryId) {
        //비활성 상태이면서 존재하는지 확인
        if (categoryRepository.getExistsByCategoryIdAndisActivated(categoryId, false)) {
            //존재하면 카테고리 활성화
            categoryRepository.activeCategoryByCategoryId(categoryId);
            //활성화된 카테고리 반환
            return categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("카테고리가 존재하지 않습니다."));
        } else {
            //존재하지 않으면 예외 발생
            throw new NotFoundException("활성화할 카테고리가 존재하지 않습니다.");
        }
    }

}
