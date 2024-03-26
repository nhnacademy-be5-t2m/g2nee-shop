package com.t2m.g2nee.shop.bookset.Category.service;


import com.t2m.g2nee.shop.bookset.Category.domain.Category;

import java.util.List;

public interface CategoryQueryService {

    List<Category> getSubCategories(Long categoryId);

    Category getCategory(Long categoryId);
}
