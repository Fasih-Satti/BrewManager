package com.example.brewmanager;

import androidx.annotation.NonNull;

import com.example.brewmanager.dashboard.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    public void retrieveOrders(orderCallback callback) {
        FirebaseDatabase.getInstance().getReference("Orders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> orders = new ArrayList<>();
                for (DataSnapshot orderSnapshot : snapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    if (order != null) {
                        orders.add(order);
                    }
                }
                callback.onOrdersRetrieved(orders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.getMessage());
            }
        });
    }
}
