package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
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
 * 카테고리에 변경을 요구하는 작업을 처리하는 컨트롤러 입니다.
 * @author : 김수빈
 * @since : 1.0
 */
@RestController
@RequestMapping("/api/v1/shop/categories")
public class CategoryRestController {

    private final CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    /**
     * 카테고리를 저장하는 컨트롤러 입니다.
     * @param request 카테고리 저장 정보 객체
     * @return ResponseEntity<CategoryInfoDto>
     */
    @PostMapping
    public ResponseEntity<CategoryInfoDto> createCategory(@RequestBody @Valid CategorySaveDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(service.saveCategory(request));
    }

    /**
     * 카테고리를 수정하는 컨트롤러 입니다.
     * @param categoryId 수정하려는 카테고리 id
     * @param request 카테고리 수정 정보 객체
     * @return ResponseEntity<CategoryInfoDto>
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryInfoDto> updateCategory(@PathVariable("categoryId") Long categoryId,
                                                          @RequestBody @Valid CategorySaveDto request) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.updateCategory(categoryId, request));
    }

    /**
     * 카테고리를 soft delete 하는 컨트롤러입니다.
     * @param categoryId 삭제하려는 카테고리 id
     * @return ResponseEntity<Boolean> 성공할 경우, false 반환
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.deleteCategory(categoryId));

    }

    /**
     * soft delete 된 카테고리를 다시 활성화하는 컨트롤러입니다.
     * @param categoryId 활성화 하려는 카테고리 id
     * @return ResponseEntity<Boolean> 성공 시, true 반환
     */
    @PatchMapping("/{categoryId}")
    public ResponseEntity<Boolean> activeCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.activeCategory(categoryId));

    }
}
