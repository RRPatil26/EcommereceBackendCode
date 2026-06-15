package com.shopping.service;

import java.util.List;

import com.shopping.dto.WishlistResponseDTO;
import com.shopping.entity.Wishlist;

public interface WishlistService {

	Wishlist addToWishlist(Wishlist wishlist);

	List<WishlistResponseDTO> getWishlistByUser(Long userId);

	
	
	void increaseQuantity(Long wishlistId);

	void decreaseQuantity(Long wishlistId);

	void removeWishlist(Long wishlistId);

}