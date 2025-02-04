package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewmanager.OrderAdapter;
import com.example.brewmanager.R;

import java.util.ArrayList;
import java.util.Iterator;

public class OrderCheck extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView emptyTextView, cartTextView;
    private ArrayList<OrderItem> orderList;
    private OrderAdapter orderAdapter;
    private Button removeItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check);

        recyclerView = findViewById(R.id.itemListRecyclerView);
        emptyTextView = findViewById(R.id.emptyTextView);
        cartTextView = findViewById(R.id.cartTextView);
        CheckBox checkAllCheckbox = findViewById(R.id.checkAllCheckbox);
        removeItemButton = findViewById(R.id.removeItemButton);

        Intent intent = getIntent();
        orderList = (ArrayList<OrderItem>) intent.getSerializableExtra("order_list");

        if (orderList == null) {
            orderList = new ArrayList<>();
        }

        // Initialize the adapter correctly
        orderAdapter = new OrderAdapter(orderList);  // Corrected the instantiation
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdapter);  // Set the adapter correctly

        if (orderList.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }

        updateCartText();  // Update Cart Text based on the number of selected items

        checkAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            orderAdapter.selectAllItems(isChecked);  // Assuming selectAllItems is implemented in your adapter
            updateCartText();
        });

        removeItemButton.setOnClickListener(v -> {
            removeSelectedItems();
            updateCartText();
        });
    }

    private void updateCartText() {
        int selectedCount = 0;
        for (OrderItem item : orderList) {
            if (item.isSelected()) {
                selectedCount++;
            }
        }
        cartTextView.setText("Cart (" + selectedCount + ")");
    }

    private void removeSelectedItems() {
        Iterator<OrderItem> iterator = orderList.iterator();
        while (iterator.hasNext()) {
            OrderItem item = iterator.next();
            if (item.isSelected()) {
                iterator.remove();
            }
        }

    }
}
