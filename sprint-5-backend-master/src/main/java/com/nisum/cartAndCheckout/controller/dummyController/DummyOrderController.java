package com.nisum.cartAndCheckout.controller.dummyController;

import com.nisum.cartAndCheckout.dto.request.OrderRequestDTO;
import com.nisum.cartAndCheckout.dto.request.OrderItemRequestDTO;
import com.nisum.cartAndCheckout.dto.response.OrderResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class DummyOrderController {

    @PostMapping("/place")
    public OrderResponseDTO placeOrder(@RequestBody OrderRequestDTO orderRequest) {
        Map<String, String> orderItemIds = new HashMap<>();

        // Loop through items and generate dummy orderItemId per SKU
        if (orderRequest.getOrderItemRequestDTOS() != null) {
            for (OrderItemRequestDTO item : orderRequest.getOrderItemRequestDTOS()) {
                String sku = item.getSku();
                String orderItemId = "ORDITEM_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                orderItemIds.put(sku, orderItemId);
            }
        }

        return OrderResponseDTO.builder()
                .orderItemIds(orderItemIds)
                .build();
    }
}

