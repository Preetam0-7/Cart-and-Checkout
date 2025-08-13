package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.exception.UserNotFoundException;
import com.nisum.cartAndCheckout.service.interfaces.CheckoutService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final CheckoutService checkoutService;

    @Autowired
    public OrderController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            //userId=1;
            throw new UserNotFoundException("User not logged in.");
        }

        String orderId = checkoutService.placeOrder(userId);
        return ResponseEntity.ok().body("Order placed successfully. Order ID: " + orderId);
    }
}
