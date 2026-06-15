package com.shopping.service;

import java.util.List;

import com.shopping.entity.Product;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long productId);

    List<Product> getProductsByCategory(
            Long categoryId);

    void deleteProduct(Long productId);

}