package com.shopping.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.entity.Product;
import com.shopping.exception.ProductNotFoundException;
import com.shopping.repository.ProductRepository;
import com.shopping.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repository;

	@Override
	public Product saveProduct(Product product) {

		return repository.save(product);
	}

	@Override
	public List<Product> getAllProducts() {

		return repository.findAll();
	}

	@Override
	public Product getProductById(Long productId) {
		
		Optional<Product> prd=repository.findById(productId);
		
		if(prd.isPresent()) {

		//return repository.findById(productId).orElse(null);
		return repository.findById(productId).get();
		}
		else {
			throw new ProductNotFoundException("product not found...!"+productId);
		}
	}

	@Override
	public List<Product> getProductsByCategory(Long categoryId) {

		return repository.findByCategoryCategoryId(categoryId);
	}

	@Override
	public void deleteProduct(Long productId) {

		repository.deleteById(productId);
	}

}