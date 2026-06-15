package com.shopping.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.shopping.dto.OrderRequestDTO;
import com.shopping.entity.Order;

public interface OrderService {

	String placeOrder(OrderRequestDTO dto);

	List<Order> getOrders(Long userId);

	List<Order> getAllOrders();

	Order updateStatus(Long orderId, String status);

	ResponseEntity<byte[]> generateInvoice(Long orderId) throws Exception;

}
