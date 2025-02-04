package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brewmanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Bill extends Fragment {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private DatabaseReference databaseReference;
    private Button sendBillButton;
    private Button deleteOrderButton;

    public Bill() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        recyclerView = view.findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);
        deleteOrderButton = view.findViewById(R.id.deleteOrderButton);

        sendBillButton = view.findViewById(R.id.sendBillButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        sendBillButton.setOnClickListener(v -> sendOrderData());

        deleteOrderButton.setOnClickListener(v -> clearData());

        fetchOrderData();

        return view;
    }

    private void fetchOrderData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Order order = snapshot.getValue(Order.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void sendOrderData() {
        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        ordersRef.removeValue(); // Remove existing data before sending new order

        for (Order order : orderList) {
            String orderId = ordersRef.push().getKey();
            if (orderId != null) {
                ordersRef.child(orderId).setValue(order);
            }
        }

        Toast.makeText(getContext(), "Order Sent to Chef", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getActivity(), ChefActivity.class);
        startActivity(intent);
    }

    private void clearData() {
        databaseReference.removeValue();
        orderList.clear();
        orderAdapter.notifyDataSetChanged();
    }
}
