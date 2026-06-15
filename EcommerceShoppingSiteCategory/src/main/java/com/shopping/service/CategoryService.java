package com.shopping.service;

import java.util.List;

import com.shopping.entity.Category;

public interface CategoryService {

    Category saveCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Long categoryId);

    void deleteCategory(Long categoryId);
}