package com.nisum.cartAndCheckout.controller.dummyController;

import com.nisum.cartAndCheckout.dto.response.InventoryAvailabilityResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventory")
public class DummyInventoryController {

    @PostMapping(value = "/check-availability", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InventoryAvailabilityResponseDTO> checkAvailability(@RequestBody List<String> skus) {
        System.out.println("SKUs received: " + skus);

        return skus.stream().map(sku -> {
            InventoryAvailabilityResponseDTO dto = new InventoryAvailabilityResponseDTO();
            dto.setSku(sku);
            dto.setAvailableQuantity(10); // Fixed quantity
            dto.setCategoryId(105);       // Fixed category for now
            return dto;
        }).collect(Collectors.toList());
    }
}
