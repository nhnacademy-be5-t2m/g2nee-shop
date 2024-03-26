package com.t2m.g2nee.shop.bookset.Category.service;

import com.t2m.g2nee.shop.bookset.Category.domain.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    Category updateCategory(Long categoryId);

    void deleteCategory(Long categoryId);

}