package com.shopping.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.shopping.dto.ProductDTO;

@FeignClient(name = "EcommerceShoppingSiteCategory", contextId = "productClient")
public interface ProductFeignClient {

	@GetMapping("/products/{id}")
	ProductDTO getProductById(@PathVariable Long id);
	
	
    @GetMapping("/products")
    List<Object> getAllProducts();
}