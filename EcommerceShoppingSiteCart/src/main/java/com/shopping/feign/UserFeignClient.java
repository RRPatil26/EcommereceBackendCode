package com.shopping.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopping.dto.UserDTO;

import java.util.List;

@FeignClient(name="EcommerceShoppingSite")
public interface UserFeignClient {

	 @GetMapping("/auth/users")
	    List<Object> getAllUsers();
	 
	 @GetMapping("/auth/users/{userId}")
	    UserDTO getUserById(@PathVariable("userId") Long userId);

    

}