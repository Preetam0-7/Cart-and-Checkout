package com.nisum.cartAndCheckout.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.service.implementation.AddressService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserAddress address;
    private AddressDto addressDto;

    @BeforeEach
    void setUp() {
        addressDto = AddressDto.builder()
                .addressLane1("123 Main Street")
                .addressLane2("Delhi")
                .state("Delhi")
                .country("India")
                .zipcode("110001")
                .build();

        address = UserAddress.builder()
                .id(1)
                .userId(1)
                .addressLane1("123 Main Street")
                .addressLane2("Delhi")
                .state("Delhi")
                .country("India")
                .zipcode("110001")
                .build();
    }

    // ---------------------------
    // GET /api/checkout/address
    // ---------------------------

    @Test
    void testGetAllAddresses_Success() throws Exception {
        Mockito.when(addressService.getAllAddresses()).thenReturn(List.of(address));

        mockMvc.perform(get("/api/checkout/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].addressLane1").value("123 Main Street"));
    }

    @Test
    void testGetAllAddresses_EmptyList() throws Exception {
        Mockito.when(addressService.getAllAddresses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/checkout/address"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    // ---------------------------
    // POST /api/checkout/address
    // ---------------------------

    @Test
    void testAddAddress_Success() throws Exception {
        Mockito.when(addressService.addAddress(any(AddressDto.class))).thenReturn(address);

        mockMvc.perform(post("/api/checkout/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressLane1").value("123 Main Street"))
                .andExpect(jsonPath("$.zipcode").value("110001"));
    }

    @Test
    void testDeleteAddress_Success() throws Exception {
        doNothing().when(addressService).deleteAddressById(1);

        mockMvc.perform(delete("/api/checkout/address/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Success"));
    }

    @Test
    void testDeleteAddress_NotFound() throws Exception {
        doThrow(new RuntimeException("Address not found")).when(addressService).deleteAddressById(999);

        mockMvc.perform(delete("/api/checkout/address/999"))
                .andExpect(status().isInternalServerError())
                .andExpect(result ->
                        result.getResolvedException().getMessage().contains("Address not found"));
    }
}
