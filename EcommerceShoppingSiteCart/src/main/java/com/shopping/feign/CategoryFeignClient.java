package com.shopping.feign;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
	    name = "EcommerceShoppingSiteCategory",
	    contextId = "categoryClient"
	)
public interface CategoryFeignClient {

    @GetMapping("/categories")
    Object getAllCategories();
    
  
}