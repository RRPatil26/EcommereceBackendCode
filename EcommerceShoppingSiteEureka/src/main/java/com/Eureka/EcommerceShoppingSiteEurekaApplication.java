package com.Eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
@EnableEurekaServer
@SpringBootApplication
public class EcommerceShoppingSiteEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceShoppingSiteEurekaApplication.class, args);
	}

}
