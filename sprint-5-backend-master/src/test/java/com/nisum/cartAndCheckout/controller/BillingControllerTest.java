package com.nisum.cartAndCheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.cartAndCheckout.dto.response.BillResponseDTO;
import com.nisum.cartAndCheckout.entity.CartItem;
import com.nisum.cartAndCheckout.entity.ShoppingCart;
import com.nisum.cartAndCheckout.exception.OrderProcessingException;
import com.nisum.cartAndCheckout.repository.CartItemRepository;
import com.nisum.cartAndCheckout.repository.ShoppingCartRepository;
import com.nisum.cartAndCheckout.mapper.BillMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillingController.class)
class BillingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoppingCartRepository shoppingCartRepository;

    @MockBean
    private CartItemRepository cartItemRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ShoppingCart cart;
    private List<CartItem> cartItems;

    @BeforeEach
    void setup() {
        cart = ShoppingCart.builder()
                .cartId(1)
                .userId(101)
                .createdDate(LocalDateTime.now())
                .lastUpdatedDate(LocalDateTime.now())
                .cartTotal(BigDecimal.valueOf(1000))
                .build();

        CartItem item = CartItem.builder()
                .id(1)
                .sku("SKU-RED-M-001")
                .productId(111)
                .size("M")
                .quantity(2)
                .unitPrice(BigDecimal.valueOf(500))
                .finalPrice(BigDecimal.valueOf(475))
                .cart(cart)
                .build();

        cartItems = List.of(item);
    }

    @Test
    void testGenerateBill_Success() throws Exception {
        Mockito.when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        Mockito.when(cartItemRepository.findByCart(cart)).thenReturn(cartItems);
        doNothing().when(cartItemRepository).deleteAll(cartItems);

        mockMvc.perform(get("/api/bill/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(101))
                .andExpect(jsonPath("$.items.length()").value(cartItems.size()))
                .andExpect(jsonPath("$.items[0].sku").value("SKU-RED-M-001"))
                .andExpect(jsonPath("$.items[0].size").value("M"))
                .andExpect(jsonPath("$.items[0].quantity").value(2))
                .andExpect(jsonPath("$.items[0].unitPrice").value(500))
                .andExpect(jsonPath("$.items[0].finalPrice").value(475));
    }

    @Test
    void testGenerateBill_CartNotFound() throws Exception {
        Mockito.when(shoppingCartRepository.findByUserId(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bill/999"))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof OrderProcessingException))
                .andExpect(result ->
                        assertTrue(result.getResolvedException().getMessage().contains("Shopping cart not found")));
    }

    @Test
    void testGenerateBill_EmptyCart() throws Exception {
        Mockito.when(shoppingCartRepository.findByUserId(101)).thenReturn(Optional.of(cart));
        Mockito.when(cartItemRepository.findByCart(cart)).thenReturn(List.of());

        mockMvc.perform(get("/api/bill/101"))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof OrderProcessingException))
                .andExpect(result ->
                        assertTrue(result.getResolvedException().getMessage().contains("No items found in the cart")));
    }
}
