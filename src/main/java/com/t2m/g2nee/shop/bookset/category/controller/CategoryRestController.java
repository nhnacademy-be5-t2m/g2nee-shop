package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.request.CategorySaveDto;
import com.t2m.g2nee.shop.bookset.category.dto.request.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop/categories")
@Slf4j
public class CategoryRestController {

    private final CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CategoryInfoDto> createCategory(@RequestBody @Valid CategorySaveDto request) {

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
                .body(service.saveCategory(request));
    }

    @PutMapping("/{categoryId}/update")
    public ResponseEntity<CategoryInfoDto> updateCategory(@PathVariable("categoryId") Long categoryId,
                                                          @RequestBody @Valid CategorySaveDto request) {

        CategoryUpdateDto updateDto = new CategoryUpdateDto(
                categoryId, request.getCategoryName(), request.getCategoryEngName(), request.getAncestorCategoryId()
        );

        log.debug(request.getAncestorCategoryId() + ", " + updateDto.getCategoryId());

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.updateCategory(updateDto));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.deleteCategory(categoryId));

    }

    @PutMapping("/{categoryId}/active")
    public ResponseEntity<Boolean> activeCategory(@PathVariable("categoryId") Long categoryId) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(service.activeCategory(categoryId));

    }
}
