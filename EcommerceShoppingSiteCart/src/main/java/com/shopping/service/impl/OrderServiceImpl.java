package com.shopping.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shopping.dto.OrderRequestDTO;
import com.shopping.dto.UserDTO;
import com.shopping.entity.Cart;
import com.shopping.entity.CartItem;
import com.shopping.entity.Order;
import com.shopping.entity.OrderItem;
import com.shopping.feign.UserFeignClient;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.OrderItemRepository;
import com.shopping.repository.OrderRepository;
import com.shopping.service.OrderService;
import java.io.ByteArrayOutputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import java.io.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.core.io.ByteArrayResource;

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

	@Autowired
	private UserFeignClient userFeignClient;

	@Autowired
	private JavaMailSender sender;

	@Value("${spring.mail.username}")
	private String fromEmail;

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

		UserDTO user = userFeignClient.getUserById(dto.getUserId());

		SimpleMailMessage sm = new SimpleMailMessage();

		sm.setFrom(fromEmail);
		sm.setTo(user.getEmail());

		sm.setSubject("LaptopHub Order Confirmation");

		sm.setText("Hi " + user.getFullName() + ",\n\n" + "Thank you for shopping with LaptopHub! 🎉\n\n"
				+ "Your order has been placed successfully.\n\n" + "Order ID: " + order.getOrderId() + "\n"
				+ "Shipping Address: " + dto.getShippingAddress() + "\n" + "Payment Method: " + dto.getPaymentMethod()
				+ "\n\n" + "We will notify you once your order is shipped.\n\n" + "Regards,\n" + "LaptopHub Team");

		sender.send(sm);

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

	/*
	 * @Override public Order updateStatus(Long orderId, String status) {
	 * 
	 * Order order = orderRepository.findById(orderId).orElseThrow();
	 * 
	 * order.setOrderStatus(status);
	 * 
	 * return orderRepository.save(order); }
	 */

	@Override
	public Order updateStatus(Long id, String status) {

		Order order = orderRepository.findById(id).orElseThrow();

		order.setOrderStatus(status);

		Order updatedOrder = orderRepository.save(order);

		if ("DELIVERED".equalsIgnoreCase(status)) {

			UserDTO user = userFeignClient.getUserById(order.getUserId());

			/*
			 * SimpleMailMessage sm = new SimpleMailMessage();
			 * 
			 * sm.setFrom(fromEmail);
			 * 
			 * sm.setTo(user.getEmail());
			 * 
			 * sm.setSubject("Order Delivered Successfully");
			 * 
			 * sm.setText(
			 * 
			 * "Hi " + user.getFullName() + ",\n\n"
			 * 
			 * + "Your Order #" + order.getOrderId() +
			 * " has been delivered successfully.\n\n"
			 * 
			 * + "Total Amount : ₹" + order.getTotalAmount() + "\n\n"
			 * 
			 * + "Thank you for shopping with LaptopHub.\n\n"
			 * 
			 * + "Regards,\n" + "LaptopHub Team");
			 * 
			 * sender.send(sm);
			 */
			try {

				byte[] pdfData = generateInvoicePdf(order);

				MimeMessage message = sender.createMimeMessage();

				MimeMessageHelper helper = new MimeMessageHelper(message, true);

				helper.setFrom(fromEmail);

				helper.setTo(user.getEmail());

				helper.setSubject("Order Delivered Successfully");

				helper.setText(

						"Hi " + user.getFullName() + ",\n\n"

								+ "Your Order #" + order.getOrderId() + " has been delivered successfully.\n\n"

								+ "Total Amount : ₹" + order.getTotalAmount() + "\n\n"

								+ "Please find attached invoice.\n\n"

								+ "Thank you for shopping with LaptopHub.\n\n"

								+ "Regards,\n" + "LaptopHub Team"

				);

				helper.addAttachment(

						"Invoice_" + order.getOrderId() + ".pdf",

						new ByteArrayResource(pdfData)

				);

				sender.send(message);

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

		return updatedOrder;
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

	private byte[] generateInvoicePdf(Order order) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		Document document = new Document();

		PdfWriter.getInstance(document, baos);

		document.open();

		document.add(new Paragraph("LaptopHub Invoice"));

		document.add(new Paragraph("Order ID : " + order.getOrderId()));

		document.add(new Paragraph("Amount : ₹" + order.getTotalAmount()));

		document.add(new Paragraph("Status : " + order.getOrderStatus()));

		document.add(new Paragraph("Payment Method : " + order.getPaymentMethod()));

		document.close();

		return baos.toByteArray();
	}
}
