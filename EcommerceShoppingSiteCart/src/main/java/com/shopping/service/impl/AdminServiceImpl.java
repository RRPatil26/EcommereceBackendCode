package com.shopping.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dto.AdminDashboardDTO;
import com.shopping.feign.CategoryFeignClient;

import com.shopping.feign.ProductFeignClient;
import com.shopping.feign.UserFeignClient;
import com.shopping.repository.OrderRepository;
import com.shopping.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private CategoryFeignClient categoryFeign;

	@Autowired
	private ProductFeignClient productFeign;

	@Autowired
	private UserFeignClient userFeign;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public AdminDashboardDTO getDashboard() {

		AdminDashboardDTO dto = new AdminDashboardDTO();

		List<Object> categories = (List<Object>) categoryFeign.getAllCategories();

		List<Object> products = productFeign.getAllProducts();

		List<Object> users = userFeign.getAllUsers();

		dto.setTotalCategories((long) categories.size());

		dto.setTotalProducts((long) products.size());

		dto.setTotalCustomers((long) users.size());

		dto.setTotalOrders(orderRepository.count());

		return dto;
	}
}