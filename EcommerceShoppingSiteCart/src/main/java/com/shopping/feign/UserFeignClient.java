package com.shopping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name="EcommerceShoppingSite")
public interface UserFeignClient {

	 @GetMapping("/auth/users")
	    List<Object> getAllUsers();
    

    

}