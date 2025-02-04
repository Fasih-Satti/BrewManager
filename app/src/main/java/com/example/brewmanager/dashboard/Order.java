package com.example.brewmanager.dashboard;
public class Order {
    private int price;
    private String productName;
    private int quantity;
    private String suggestion;
    private int totalPrice;

    // Default constructor
    public Order() {}

    public Order(int price, String productName, int quantity, String suggestion, int totalPrice) {
        this.price = price;
        this.productName = productName;
        this.quantity = quantity;
        this.suggestion = suggestion;
        this.totalPrice = totalPrice;
    }

    // Getters and Setters
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
}
