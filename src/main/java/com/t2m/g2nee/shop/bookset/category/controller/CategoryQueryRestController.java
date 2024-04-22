package com.t2m.g2nee.shop.bookset.category.controller;

import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryHierarchyDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryInfoDto;
import com.t2m.g2nee.shop.bookset.category.dto.response.CategoryUpdateDto;
import com.t2m.g2nee.shop.bookset.category.service.CategoryQueryService;
import com.t2m.g2nee.shop.pageUtils.PageResponse;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 카테고리 조회를 위한 컨트롤러 입니다.
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
     * 카테고리를 계층화하여 반환하는 컨트롤러입니다.
     * @return ResponseEntity<List < CategoryHierarchyDto>>
     */
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping
    public ResponseEntity<List<CategoryHierarchyDto>> getRootCategories() {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getRootCategories());
    }

    /**
     * 카테고리의 자식 카테고리를 가져오는 컨트롤러
     * @param categoryId 카테고리 아이디
     */
    //TODO : 나중에 지우기
    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/{categoryId}/children")
    public ResponseEntity<List<CategoryHierarchyDto>> getChildCategories(@PathVariable("categoryId") Long categoryId){

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategory(categoryId).getChildren());
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
     * 하나의 카테고리를 반환하는 컨트롤러 입니다.
     * @param categoryId 카테고리 id
     * @return ResponseEntity<CategoryUpdateDto>
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryUpdateDto> getCategory(@PathVariable("categoryId") Long categoryId) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategory(categoryId));
    }

    /**
     * 카테고리 이름으로 검색하고, 그 결과들을 페이징처리 하여 반환하는 컨트롤러입니다.
     * @param name 검색할 이름
     * @param page 현재 페이지
     * @return ResponseEntity<PageResponse < CategoryInfoDto>>
     */
    @GetMapping("/search")
    public ResponseEntity<PageResponse<CategoryInfoDto>> getCategories(
            @RequestParam("name") String name,
            @RequestParam int page) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.getCategoriesByName(name, page));
    }
}
