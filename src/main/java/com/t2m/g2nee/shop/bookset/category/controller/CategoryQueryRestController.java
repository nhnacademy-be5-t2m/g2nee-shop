package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
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
     * 최상위 카테고리를 반환하는 컨트롤러
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<CategoryInfoDto>> getRootCategories() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getRootCategories());
    }

    /**
     * 특정 카테고리의 서브 카테고리를 반환하는 컨트롤러
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}/sub")
    public ResponseEntity<List<CategoryInfoDto>> getSubCategories(
            @PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getSubCategories(categoryId));
    }

    /**
     * 모든 카테고리를 페이징 처리하여 반환하는 컨트롤러
     *
     * @param page
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getAllCategories(@RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllCategories(page));
    }

    /**
     * 하나의 카테고리를 반환하는 컨트롤러
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryInfoDto> getCategory(@PathVariable("categoryId") Long categoryId) {

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
