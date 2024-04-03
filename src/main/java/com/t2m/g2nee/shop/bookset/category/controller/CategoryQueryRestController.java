package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryQueryService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<Map<String, List<CategoryInfoDto>>> getRootCategories() {
        List<CategoryInfoDto> rootCategories = service.getRootCategories();

        Map<String, List<CategoryInfoDto>> response = new HashMap<>();
        response.put("data", rootCategories);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{categoryId}/sub")
    public ResponseEntity<Map<String, List<CategoryInfoDto>>> getSubCategories(
            @PathVariable("categoryId") Long categoryId) {
        List<CategoryInfoDto> subCategories = service.getSubCategories(categoryId);

        Map<String, List<CategoryInfoDto>> response = new HashMap<>();
        response.put("data", subCategories);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getAllCategories(@RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllCategories(page));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Map<String, CategoryInfoDto>> getCategory(@PathVariable("categoryId") Long categoryId) {
        CategoryInfoDto category = service.getCategory(categoryId);

        Map<String, CategoryInfoDto> response = new HashMap<>();
        response.put("data", category);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/search/{categoryName}")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getCategories(
            @PathVariable("categoryName") String categoryName,
            @RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategoriesByName(categoryName, page));
    }
}
