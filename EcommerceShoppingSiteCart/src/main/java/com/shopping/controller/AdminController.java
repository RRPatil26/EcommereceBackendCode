package com.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.shopping.dto.AdminDashboardDTO;
import com.shopping.entity.Order;
import com.shopping.repository.OrderRepository;
import com.shopping.service.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public AdminDashboardDTO dashboard() {

        return adminService.getDashboard();
    }
    
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/recent-orders")
    public List<Order> recentOrders(){

        return orderRepository.findAll();
    }
}