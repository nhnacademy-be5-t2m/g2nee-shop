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

@RestController
@RequestMapping("/shop/categories")
public class CategoryQueryRestController {

    private final CategoryQueryService service;

    public CategoryQueryRestController(CategoryQueryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CategoryInfoDto>> getRootCategories() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getRootCategories());
    }

    @GetMapping("/{categoryId}/sub")
    public ResponseEntity<List<CategoryInfoDto>> getSubCategories(
            @PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getSubCategories(categoryId));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getAllCategories(@RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllCategories(page));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryInfoDto> getCategory(@PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategory(categoryId));
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getCategories(
            @RequestParam("name") String name,
            @RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategoriesByName(name, page));
    }
}
