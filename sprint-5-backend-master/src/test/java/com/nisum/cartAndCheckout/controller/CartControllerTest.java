package com.nisum.cartAndCheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.cartAndCheckout.dto.request.CartItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.*;
import com.nisum.cartAndCheckout.service.implementation.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    private final MockHttpSession session = new MockHttpSession();

    {
        session.setAttribute("userId", 1);
    }

    @Test
    void testAddToCart_Success() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .userId(1)
                .productId(1)
                .sku("sku1")
                .size("M")
                .finalPrice(BigDecimal.valueOf(99.99))
                .build();

        CartItemResponseDTO response = new CartItemResponseDTO("Item added successfully");

        Mockito.when(cartServiceImpl.addToCart(any(CartItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item added successfully"));
    }

    @Test
    void testAddToCart_ItemAlreadyExists() throws Exception {
        CartItemRequestDTO request = CartItemRequestDTO.builder()
                .userId(2)
                .productId(2)
                .sku("sku2")
                .size("L")
                .finalPrice(BigDecimal.valueOf(49.50))
                .build();

        CartItemResponseDTO response = new CartItemResponseDTO("Item already exists in cart");

        Mockito.when(cartServiceImpl.addToCart(any(CartItemRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/cart/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item already exists in cart"));
    }

    @Test
    void testGetCartItems() throws Exception {


        List<CartItemDto> cartItems = List.of(
                CartItemDto.builder()
                        .cartItemId(1)
                        .productName("Product 1")
                        .size("M")
                        .availableSizes(List.of("S", "M", "L"))
                        .cartQuantity(2)
                        .stockQuantity(5)
                        .unitPrice(99.99)
                        .discount(10.0)
                        .finalPrice(89.99)
                        .imageurl("http://example.com/image1.jpg")
                        .build(),
                CartItemDto.builder()
                        .cartItemId(2)
                        .productName("Product 2")
                        .size("L")
                        .availableSizes(List.of("M", "L", "XL"))
                        .cartQuantity(1)
                        .stockQuantity(3)
                        .unitPrice(49.50)
                        .discount(5.0)
                        .finalPrice(47.02)
                        .imageurl("http://example.com/image2.jpg")
                        .build()
        );


        Mockito.when(cartServiceImpl.getCartItemsByUserId(1)).thenReturn(cartItems);

        mockMvc.perform(get("/api/cart/items").session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateQuantity() throws Exception {
        UpdateCartItemDto response = new UpdateCartItemDto("Quantity updated", 1);

        Mockito.when(cartServiceImpl.updateCartItemQuantity(1, 1, 3)).thenReturn(response);

        mockMvc.perform(put("/api/cart/updateQuantity")
                        .session(session)
                        .param("cartItemId", "1")
                        .param("newQuantity", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Quantity updated"));
    }

    @Test
    void testUpdateSize() throws Exception {
        UpdateCartItemSizeDto response = new UpdateCartItemSizeDto("Size updated", 1, 200, 10, 180);

        Mockito.when(cartServiceImpl.updateCartItemSize(1, 1, "XL")).thenReturn(response);

        mockMvc.perform(put("/api/cart/updateSize")
                        .session(session)
                        .param("cartItemId", "1")
                        .param("size", "XL"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Size updated"));
    }

    @Test
    void testDeleteItem() throws Exception {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "deleted");
        response.put("cartItemId", 1);

        Mockito.when(cartServiceImpl.deleteCartItem(1, 1)).thenReturn(response);

        mockMvc.perform(delete("/api/cart/deleteCartItem")
                        .session(session)
                        .param("cartItemId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("deleted"))
                .andExpect(jsonPath("$.cartItemId").value(1));
    }
}
