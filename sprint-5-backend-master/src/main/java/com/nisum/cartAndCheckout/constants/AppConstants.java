package com.nisum.cartAndCheckout.constants;

public class AppConstants {

    // URLs
    public static final String INVENTORY_AVAILABILITY_URL = "http://localhost:8085/api/inventory/check-availability";
    public static final String INVENTORY_UPDATE_URL = "http://localhost:8085/api/inventory/update-stock";
    public static final String OMS_ORDER_URL = "http://localhost:8085/api/orders/place";
    public static final String PROMO_SERVICE_URL = "http://localhost:8085/api/dummy/cart/promos";
    public static final String SESSION_USER_ID = "userId";

    // Error messages
    public static final String CART_NOT_FOUND = "Shopping cart not found for user: ";
    public static final String EMPTY_CART = "No items found in the cart for user: ";
    public static final String INVENTORY_UNAVAILABLE = "Inventory service is unavailable. Please try again later.";
    public static final String ORDER_FAILED = "Failed to create order in OMS";
    public static final String OMS_UNAVAILABLE = "Order Management System is unavailable. Please try again later.";
    public static final String INVENTORY_UPDATE_FAILED = "Failed to update inventory after order placement.";
    public static final String SKU_NOT_FOUND = "SKU not found in inventory for: ";
    public static final String INSUFFICIENT_QUANTITY = "Insufficient quantity for SKU: ";
    public static final String PROMO_FETCH_FAILED = "Failed to fetch promo data.";
    public static final String EMPTY_PRODUCT_LIST = "Product ID list cannot be empty.";
    public static final String USER_ADDRESS_NOT_FOUND = "User address not found for the current user.";
    public static final String ORDER_SUCCESS_MSG = "✅ Order placed successfully. Order ID: ";
    public static final String ORDER_FAILURE_MSG = "❌ Order placement failed: ";
    public static final String USER_NOT_LOGGED_IN = "User not logged in.";

}
