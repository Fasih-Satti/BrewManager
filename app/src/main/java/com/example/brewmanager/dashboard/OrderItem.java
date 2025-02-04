package com.example.brewmanager.dashboard;

public class OrderItem {
    private String name;
    private String price;
    private int quantity;
    private String suggestion;
    private boolean isSelected;

    public OrderItem(String name, String price, int quantity, String suggestion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.suggestion = suggestion;
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getOrderDetails() {
        return name + " x" + quantity + " @ " + price;
    }
}
