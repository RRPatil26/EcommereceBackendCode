package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shopping.entity.Category;
import com.shopping.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category saveCategory(
            @RequestBody Category category) {

        return categoryService
                .saveCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
    	log.info("SLF4J INFO log printing.....................................");
    	log.error("SLF4J Error log printing.....................................");

        return categoryService
                .getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(
            @PathVariable Long id) {

        return categoryService
                .getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return "Category Deleted";
    }
}