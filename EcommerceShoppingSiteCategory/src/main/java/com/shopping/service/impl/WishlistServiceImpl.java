package com.shopping.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dto.WishlistResponseDTO;
import com.shopping.entity.Product;
import com.shopping.entity.Wishlist;
import com.shopping.repository.ProductRepository;
import com.shopping.repository.WishlistRepository;
import com.shopping.service.WishlistService;

@Service
public class WishlistServiceImpl implements WishlistService {

	@Autowired
	private WishlistRepository repository;
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Wishlist addToWishlist(Wishlist wishlist) {

		return repository.save(wishlist);
	}

	@Override
	public List<WishlistResponseDTO> getWishlistByUser(Long userId) {

		List<Wishlist> wishlistItems = repository.findByUserId(userId);

		List<WishlistResponseDTO> response = new ArrayList<>();

		for (Wishlist item : wishlistItems) {

			Product product = productRepository.findById(item.getProductId()).orElse(null);

			if (product != null) {

				WishlistResponseDTO dto = new WishlistResponseDTO();

				dto.setWishlistId(item.getWishlistId());

				dto.setProductId(product.getProductId());

				dto.setProductName(product.getProductName());

				dto.setImageUrl(product.getImageUrl());

				dto.setPrice(product.getPrice());

				dto.setBrand(product.getBrand());

				dto.setQuantity(item.getQuantity());

				response.add(dto);
			}
		}

		return response;
	}

	
	
	@Override
	public void increaseQuantity(Long wishlistId) {

	    Wishlist wishlist =
	            repository.findById(wishlistId)
	            .orElseThrow();

	    wishlist.setQuantity(
	            wishlist.getQuantity() + 1);

	    repository.save(wishlist);
	}
	@Override
	public void decreaseQuantity(Long wishlistId) {

	    Wishlist wishlist =
	            repository.findById(wishlistId)
	            .orElseThrow();

	    if(wishlist.getQuantity() > 1){

	        wishlist.setQuantity(
	                wishlist.getQuantity() - 1);

	        repository.save(wishlist);
	    }
	}
	
	@Override
	public void removeWishlist(Long wishlistId) {

	    repository.deleteById(wishlistId);
	}
	
	
}