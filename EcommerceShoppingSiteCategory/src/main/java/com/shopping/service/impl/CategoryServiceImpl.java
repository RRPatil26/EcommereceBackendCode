package com.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.entity.Category;
import com.shopping.repository.CategoryRepository;
import com.shopping.service.CategoryService;

@Service
public class CategoryServiceImpl
        implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(
            Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {

        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(
            Long categoryId) {

        return categoryRepository
                .findById(categoryId)
                .orElse(null);
    }

    @Override
    public void deleteCategory(
            Long categoryId) {

        categoryRepository.deleteById(categoryId);
    }
}