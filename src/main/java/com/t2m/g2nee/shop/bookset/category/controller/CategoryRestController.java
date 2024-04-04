package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * db에 변경을 가하는 쿼리를 처리하는 controller
 *
 * @author : 김수빈
 * @since : 1.0
 */
@RestController
@RequestMapping("/shop/categories")
public class CategoryRestController {

    private final CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    /**
     * 카테고리를 저장하는 컨트롤러
     *
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryInfoDto> createCategory(@RequestBody @Valid CategorySaveDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(service.saveCategory(request));
    }

    /**
     * 카테고리를 업데이트하는 컨트롤러
     *
     * @param categoryId
     * @param request
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryInfoDto> updateCategory(@PathVariable("categoryId") Long categoryId,
                                                          @RequestBody @Valid CategorySaveDto request) {

        CategoryUpdateDto updateDto = new CategoryUpdateDto(
                categoryId, request.getCategoryName(), request.getCategoryEngName(), request.getAncestorCategoryId()
        );

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.updateCategory(updateDto));
    }

    /**
     * 카테고리를 soft delete 하는 컨트롤러
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.deleteCategory(categoryId));

    }

    /**
     * soft delete 된 카테고리를 다시 활성화하는 컨트롤러
     *
     * @param categoryId
     * @return
     */
    @PatchMapping("/{categoryId}")
    public ResponseEntity<Boolean> activeCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.activeCategory(categoryId));

    }
}
