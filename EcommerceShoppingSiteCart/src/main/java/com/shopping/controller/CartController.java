package com.shopping.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shopping.service.CartService;

import com.shopping.dto.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

	@Autowired
	private CartService service;

	@PostMapping
	public String addToCart(@RequestBody CartRequestDTO dto) {

		service.addToCart(dto.getUserId(), dto.getProductId(), dto.getQuantity());

		return "Added To Cart";
	}

	@GetMapping("/{userId}")
	public List<CartResponseDTO> getCart(@PathVariable Long userId) {

		return service.getCart(userId);
	}

	@DeleteMapping("/{cartItemId}")
	public String deleteItem(@PathVariable Long cartItemId) {

		service.deleteItem(cartItemId);

		return "Deleted";
	}

	@PutMapping("/increase/{cartItemId}")
	public String increaseQuantity(@PathVariable Long cartItemId) {

		service.increaseQuantity(cartItemId);

		return "Quantity Increased";
	}

	@PutMapping("/decrease/{cartItemId}")
	public String decreaseQuantity(@PathVariable Long cartItemId) {

		service.decreaseQuantity(cartItemId);

		return "Quantity Decreased";
	}
}