package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 

import com.shopping.dto.OrderRequestDTO;
import com.shopping.entity.Order;
import com.shopping.service.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private JavaMailSender sender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	

	@PostMapping("/place")
	public String placeOrder(@RequestBody OrderRequestDTO dto) {

		return orderService.placeOrder(dto);
	}

	@GetMapping("/{userId}")
	public List<Order> getOrders(@PathVariable Long userId) {

		return orderService.getOrders(userId);
	}

	@GetMapping("/admin/orders")
	public List<Order> getAllOrders() {

		return orderService.getAllOrders();

	}

	@PutMapping("/admin/orders/{id}/{status}")
	public Order updateStatus(

			@PathVariable Long id,

			@PathVariable String status

	) {

		return orderService.updateStatus(id, status);

	}

	@GetMapping("/invoice/{orderId}")
	public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long orderId) throws Exception {

		return orderService.generateInvoice(orderId);

	}
}