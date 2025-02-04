package com.example.brewmanager.dashboard;

public class NotificationItem {
    String orderId;
    String chefId;
    String message;

    public NotificationItem(String orderId, String chefId, String message) {
        this.orderId = orderId;
        this.chefId = chefId;
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getChefId() {
        return chefId;
    }

    public String getMessage() {
        return message;
    }
}
