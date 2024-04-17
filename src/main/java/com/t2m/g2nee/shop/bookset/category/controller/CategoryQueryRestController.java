package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryQueryService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * select 하는 쿼리를 처리하는 controller
 *
 * @author : 김수빈
 * @since : 1.0
 */
@RestController
@RequestMapping("/shop/categories")
public class CategoryQueryRestController {

    private final CategoryQueryService service;

    public CategoryQueryRestController(CategoryQueryService service) {
        this.service = service;
    }

    /**
     * 카테고리를 계층화하여 반환하는 컨트롤러
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CategoryHierarchyDto>> getRootCategories() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getRootCategories());
    }


    /**
     * 모든 카테고리를 반환하는 컨트롤러
     *
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<CategoryInfoDto>> getAllCategories() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllCategories());
    }

    /**
     * 하나의 카테고리를 반환하는 컨트롤러
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryUpdateDto> getCategory(@PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategory(categoryId));
    }

    /**
     * 카테고리 이름으로 검색하여 페이징처리 하여 반환하는 컨트롤러
     *
     * @param name
     * @param page
     * @return
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getCategories(
            @RequestParam("name") String name,
            @RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategoriesByName(name, page));
    }
}
