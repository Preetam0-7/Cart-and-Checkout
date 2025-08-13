package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.CartItemDto;
import com.nisum.cartAndCheckout.dto.response.CartItemResponseDTO;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemDto;
import com.nisum.cartAndCheckout.dto.response.UpdateCartItemSizeDto;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
@RequestMapping("/api/cart")
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    public CartController(CartServiceImpl cartServiceImpl) {
        this.cartServiceImpl = cartServiceImpl;
    }

    @ModelAttribute
    public void setUserIdInSession(HttpSession session) {
//        if (session.getAttribute("userId") == null) {
        session.setAttribute("userId", 1); // default test user
//        }
    }

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> addToCart(@RequestBody CartItemRequestDTO dto) {
        CartItemResponseDTO response = cartServiceImpl.addToCart(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/items")
    public ResponseEntity<List<CartItemDto>> getCartItems(HttpSession session){

        int userId = (int) session.getAttribute("userId");
        System.out.println(userId);
        List<CartItemDto> items = cartServiceImpl.getCartItemsByUserId(userId);

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/updateQuantity")
    public ResponseEntity<UpdateCartItemDto> updateQuantity(
            @RequestParam int cartItemId,
            @RequestParam int newQuantity,
            HttpSession session){
        int userId = (int) session.getAttribute("userId");

        UpdateCartItemDto response = cartServiceImpl.updateCartItemQuantity(userId,cartItemId,newQuantity);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateSize")
    public ResponseEntity<UpdateCartItemSizeDto> updateQuantity(
            @RequestParam int cartItemId,
            @RequestParam String size,
            HttpSession session){
        int userId = (int) session.getAttribute("userId");

        UpdateCartItemSizeDto response = cartServiceImpl.updateCartItemSize(userId,cartItemId,size);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteCartItem")
    public ResponseEntity<Map<String,Object>> deleteItem(
            @RequestParam int cartItemId,
            HttpSession session
    ){

        int userId = (int) session.getAttribute("userId");
        Map<String,Object> response = cartServiceImpl.deleteCartItem(userId,cartItemId);
        return ResponseEntity.ok(response);

    }
}
