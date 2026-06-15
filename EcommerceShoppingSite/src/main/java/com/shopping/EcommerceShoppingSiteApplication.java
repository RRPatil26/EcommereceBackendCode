package com.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.shopping.entity.User;

@SpringBootApplication
public class EcommerceShoppingSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceShoppingSiteApplication.class, args);
		
		User us=new User();
		us.setFullName("Rashmi");
		System.out.println(us.getFullName());
		
	}
	
	

}
