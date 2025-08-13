package com.nisum.cartAndCheckout.controller.dummyController;

import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dummy/cart")  // Changed to avoid route conflicts
@RequiredArgsConstructor
public class DummyCartController {  // Class renamed to avoid bean conflict

    private final CartServiceImpl cartServiceImpl;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addToCart(@RequestBody CartItemRequestDTO dto) {
        CartItemResponseDTO response = cartServiceImpl.addToCart(dto);
        return ResponseEntity.ok(response);
    }
}
