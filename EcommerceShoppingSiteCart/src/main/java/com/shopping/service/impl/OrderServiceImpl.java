package com.shopping.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dto.OrderRequestDTO;
import com.shopping.entity.Cart;
import com.shopping.entity.CartItem;
import com.shopping.entity.Order;
import com.shopping.entity.OrderItem;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.OrderItemRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.service.OrderService;
import java.io.ByteArrayOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public String placeOrder(OrderRequestDTO dto) {

		Cart cart = cartRepository.findByUserId(dto.getUserId()).orElseThrow();

		List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());

		double total = 0;

		for (CartItem item : cartItems) {

			total += item.getSubtotal();
		}

		Order order = new Order();

		order.setUserId(dto.getUserId());

		order.setOrderDate(LocalDateTime.now());

		order.setOrderStatus("PLACED");

		order.setTotalAmount(total);

		order.setShippingAddress(dto.getShippingAddress());

		order.setPhoneNumber(dto.getPhoneNumber());

		order.setPaymentMethod(dto.getPaymentMethod());

		order = orderRepository.save(order);
		for (CartItem item : cartItems) {

			OrderItem orderItem = new OrderItem();

			orderItem.setOrder(order);

			orderItem.setProductId(item.getProductId());

			orderItem.setQuantity(item.getQuantity());

			orderItem.setPrice(item.getSubtotal());

			orderItemRepository.save(orderItem);
		}
		cartItemRepository.deleteAll(cartItems);

		return "Order Placed Successfully";
	}

	@Override
	public List<Order> getOrders(Long userId) {

		return orderRepository.findByUserId(userId);
	}

	@Override
	public List<Order> getAllOrders() {

		return orderRepository.findAll();

	}

	@Override
	public Order updateStatus(Long orderId, String status) {

		Order order = orderRepository.findById(orderId).orElseThrow();

		order.setOrderStatus(status);

		return orderRepository.save(order);
	}

	@Override
	public ResponseEntity<byte[]> generateInvoice(Long orderId) throws Exception {

		Order order = orderRepository.findById(orderId).orElseThrow();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document document = new Document();

		PdfWriter.getInstance(document, baos);

		document.open();

		document.add(new Paragraph("Laptop Store Invoice"));

		document.add(new Paragraph("Order Id : " + order.getOrderId()));

		document.add(new Paragraph("Amount : ₹" + order.getTotalAmount()));

		document.add(new Paragraph("Status : " + order.getOrderStatus()));

		document.add(new Paragraph("Payment : " + order.getPaymentMethod()));

		document.close();

		HttpHeaders headers = new HttpHeaders();

		headers.add("Content-Disposition", "attachment; filename=invoice.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(baos.toByteArray());

	}
}
