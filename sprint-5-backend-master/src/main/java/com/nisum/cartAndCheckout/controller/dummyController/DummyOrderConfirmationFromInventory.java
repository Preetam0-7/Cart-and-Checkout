package com.nisum.cartAndCheckout.controller.dummyController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class DummyOrderConfirmationFromInventory {

    /**
     * Endpoint to mock inventory update after order is placed.
     * Example payload:
     * [
     *   {
     *     "sku": "SKU-RED-M-001",
     *     "quantity": 2,
     *     "orderId": "ORD123456"
     *   },
     *   {
     *     "sku": "SKU-BLU-L-002",
     *     "quantity": 1,
     *     "orderId": "ORD123456"
     *   }
     * ]
     */
    @PostMapping("/update-stock")
    public ResponseEntity<String> updateInventoryStock(@RequestBody List<Map<String, Object>> updatePayload) {
        System.out.println("✅ Dummy Inventory Update Request Received");

        updatePayload.forEach(item -> {
            System.out.println("SKU: " + item.get("sku"));
            System.out.println("Quantity: " + item.get("quantity"));
            System.out.println("Order ID: " + item.get("orderId"));
            System.out.println("Category ID: " + item.get("categoryId"));
            System.out.println("-------------------------------");
        });

        return ResponseEntity.ok("✅ Inventory updated successfully (dummy).");
    }
}
