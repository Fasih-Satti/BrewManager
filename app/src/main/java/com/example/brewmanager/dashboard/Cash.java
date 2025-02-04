package com.example.brewmanager.dashboard;

public class Cash {

    private String orderName;
    private String customerName;
    private int quantity;
    private String orderId;
    private String tableNumber;

    public Cash(String orderName, String customerName, int quantity, String orderId, String tableNumber) {
        this.orderName = orderName;
        this.customerName = customerName;
        this.quantity = quantity;
        this.orderId = orderId;
        this.tableNumber = tableNumber;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTableNumber() {
        return tableNumber;
    }
}
