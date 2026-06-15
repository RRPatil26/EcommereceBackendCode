package com.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dto.CartResponseDTO;
import com.shopping.dto.ProductDTO;
import com.shopping.entity.Cart;
import com.shopping.entity.CartItem;
import com.shopping.feign.CategoryFeignClient;
import com.shopping.feign.ProductFeignClient;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.CartRepository;
import com.shopping.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductFeignClient productFeignClient;
	
	 @Autowired
	    private CategoryFeignClient categoryFeignClient;

	    public Object fetchCategories() {
	        return categoryFeignClient.getAllCategories();
	    }

	@Override
	public void addToCart(Long userId, Long productId, Integer quantity) {

		Cart cart;

		Optional<Cart> existingCart = cartRepository.findByUserId(userId);

		if (existingCart.isPresent()) {

			cart = existingCart.get();

		} else {

			cart = new Cart();
			cart.setUserId(userId);

			cart = cartRepository.save(cart);
		}

		ProductDTO product = productFeignClient.getProductById(productId);

		CartItem item = new CartItem();

		item.setCartId(cart.getCartId());

		item.setProductId(productId);

		item.setQuantity(quantity);

		item.setSubtotal(product.getPrice() * quantity);

		cartItemRepository.save(item);
	}

	@Override
	public List<CartResponseDTO> getCart(Long userId) {

		Cart cart = cartRepository.findByUserId(userId).orElse(null);

		if (cart == null) {

			return new ArrayList<>();
		}

		List<CartItem> items = cartItemRepository.findByCartId(cart.getCartId());

		List<CartResponseDTO> response = new ArrayList<>();

		for (CartItem item : items) {

			ProductDTO product = productFeignClient.getProductById(item.getProductId());

			CartResponseDTO dto = new CartResponseDTO();

			dto.setCartItemId(item.getCartItemId());

			dto.setProductId(product.getProductId());

			dto.setProductName(product.getProductName());

			dto.setImageUrl(product.getImageUrl());

			dto.setPrice(product.getPrice());

			dto.setQuantity(item.getQuantity());

			dto.setSubtotal(item.getSubtotal());

			response.add(dto);
		}

		return response;
	}

	@Override
	public void deleteItem(Long cartItemId) {

		cartItemRepository.deleteById(cartItemId);
	}
	
	@Override
	public void increaseQuantity(Long cartItemId) {

	    CartItem item = cartItemRepository
	            .findById(cartItemId)
	            .orElseThrow(() ->
	                    new RuntimeException("Cart Item Not Found"));

	    ProductDTO product =
	            productFeignClient.getProductById(
	                    item.getProductId());

	    item.setQuantity(
	            item.getQuantity() + 1);

	    item.setSubtotal(
	            product.getPrice() *
	            item.getQuantity());

	    cartItemRepository.save(item);
	}
	@Override
	public void decreaseQuantity(Long cartItemId) {

	    CartItem item = cartItemRepository
	            .findById(cartItemId)
	            .orElseThrow(() ->
	                    new RuntimeException("Cart Item Not Found"));

	    if (item.getQuantity() > 1) {

	        ProductDTO product =
	                productFeignClient.getProductById(
	                        item.getProductId());

	        item.setQuantity(
	                item.getQuantity() - 1);

	        item.setSubtotal(
	                product.getPrice() *
	                item.getQuantity());

	        cartItemRepository.save(item);
	    }
	}
	
}