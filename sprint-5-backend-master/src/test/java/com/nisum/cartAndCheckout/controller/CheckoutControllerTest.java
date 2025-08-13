package com.nisum.cartAndCheckout.controller;

import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.service.interfaces.UserAddressService;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserAddressService userAddressService;

    // ✅ Test Success Scenario
    @Test
    void testGetCheckoutAddress_Success() throws Exception {
        UserAddress mockAddress = new UserAddress();
        mockAddress.setId(1);
        mockAddress.setUserId(101);
        mockAddress.setAddressLane1("123 Street");
        mockAddress.setState("NY");

        // simulate session and service behavior
        when(userAddressService.getAddressByIdAndUser(1, 101)).thenReturn(mockAddress);

        mockMvc.perform(get("/api/checkout/checkout-address")
                        .param("id", "1")
                        .sessionAttr("userId", 101))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(101))
                .andExpect(jsonPath("$.addressLane1").value("123 Street"));
    }

    // ✅ Test Unauthorized (no userId in session)
    @Test
    void testGetCheckoutAddress_Unauthorized() throws Exception {
        mockMvc.perform(get("/api/checkout/checkout-address")
                        .param("id", "1"))
                .andExpect(status().isUnauthorized());
    }
}
