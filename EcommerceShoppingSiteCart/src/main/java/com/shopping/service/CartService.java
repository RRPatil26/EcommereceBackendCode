package com.shopping.service;

import java.util.List;

import com.shopping.dto.CartResponseDTO;

public interface CartService {

	void addToCart(Long userId, Long productId, Integer quantity);

	List<CartResponseDTO> getCart(Long userId);

	void deleteItem(Long cartItemId);
	
	void increaseQuantity(Long cartItemId);

    void decreaseQuantity(Long cartItemId);
}