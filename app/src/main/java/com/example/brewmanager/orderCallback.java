package com.example.brewmanager;


import com.example.brewmanager.dashboard.Order;

import java.util.List;

public interface orderCallback {
    void onOrdersRetrieved(List<Order> orders);
    void onError(String error);
}
