package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shopping.dto.WishlistResponseDTO;
import com.shopping.entity.Wishlist;
import com.shopping.service.WishlistService;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin("*")
public class WishlistController {

    @Autowired
    private WishlistService service;

    @PostMapping
    public Wishlist addWishlist(
            @RequestBody Wishlist wishlist) {

        return service.addToWishlist(
                wishlist);
    }

    @GetMapping("/{userId}")
    public List<WishlistResponseDTO>
    getWishlist(
            @PathVariable Long userId){

        return service.getWishlistByUser(userId);
    }

   
    
    @PutMapping("/increase/{wishlistId}")
    public String increaseQuantity(
            @PathVariable Long wishlistId){

        service.increaseQuantity(
                wishlistId);

        return "Quantity Increased";
    }
    @PutMapping("/decrease/{wishlistId}")
    public String decreaseQuantity(
            @PathVariable Long wishlistId){

        service.decreaseQuantity(
                wishlistId);

        return "Quantity Decreased";
    }
    
    @DeleteMapping("/{wishlistId}")
    public String removeWishlist(
            @PathVariable Long wishlistId){

        service.removeWishlist(
                wishlistId);

        return "Wishlist Item Removed";
    }
}