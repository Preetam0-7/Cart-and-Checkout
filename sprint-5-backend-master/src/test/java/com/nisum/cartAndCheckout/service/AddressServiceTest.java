package com.nisum.cartAndCheckout.service;

import com.nisum.cartAndCheckout.dto.AddressDto;
import com.nisum.cartAndCheckout.entity.UserAddress;
import com.nisum.cartAndCheckout.exception.AddressNotFoundException;
import com.nisum.cartAndCheckout.repository.AddressRepository;
import com.nisum.cartAndCheckout.service.implementation.AddressService;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private HttpSession session;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAddresses_WithUserIdInSession() {
        when(session.getAttribute("userId")).thenReturn(101);
        List<UserAddress> mockAddresses = List.of(new UserAddress(), new UserAddress());
        when(addressRepository.findByUserId(101)).thenReturn(mockAddresses);

        List<UserAddress> result = addressService.getAllAddresses();

        assertEquals(2, result.size());
        verify(addressRepository, times(1)).findByUserId(101);
    }

    @Test
    void testGetAllAddresses_WithNullUserId_ShouldSetDefault() {
        when(session.getAttribute("userId")).thenReturn(null);
        List<UserAddress> mockAddresses = List.of(new UserAddress());
        when(addressRepository.findByUserId(1)).thenReturn(mockAddresses);

        List<UserAddress> result = addressService.getAllAddresses();

        assertEquals(1, result.size());
        verify(session).setAttribute("userId", 1);
        verify(addressRepository).findByUserId(1);
    }

    @Test
    void testAddAddress_Success() {
        when(session.getAttribute("userId")).thenReturn(101);

        AddressDto dto = new AddressDto();
        dto.setAddressLane1("123 Lane");
        dto.setAddressLane2("Near Park");
        dto.setZipcode("700001");
        dto.setState("WB");
        dto.setCountry("India");
        dto.setAddressType("Home");
        dto.setContactName("Asutosh");
        dto.setContactPhoneNumber("9876543210");

        UserAddress saved = new UserAddress();
        when(addressRepository.save(any(UserAddress.class))).thenReturn(saved);

        UserAddress result = addressService.addAddress(dto);

        assertNotNull(result);
        verify(addressRepository).save(any(UserAddress.class));
    }

    @Test
    void testDeleteAddressById_Success() {
        when(session.getAttribute("userId")).thenReturn(101);
        UserAddress address = new UserAddress();
        when(addressRepository.findByIdAndUserId(1, 101)).thenReturn(Optional.of(address));

        assertDoesNotThrow(() -> addressService.deleteAddressById(1));
        verify(addressRepository).delete(address);
    }

    @Test
    void testDeleteAddressById_NotFound() {
        when(session.getAttribute("userId")).thenReturn(101);
        when(addressRepository.findByIdAndUserId(1, 101)).thenReturn(Optional.empty());

        AddressNotFoundException ex = assertThrows(AddressNotFoundException.class,
                () -> addressService.deleteAddressById(1));

        assertEquals("User address not found for the current user", ex.getMessage());
    }
}
