package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brewmanager.Design.First;
import com.example.brewmanager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ChefActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList;
    private DatabaseReference databaseReference;
    private ImageView backImageView;
    private Button doneButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        recyclerView = findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(orderList);
        recyclerView.setAdapter(orderAdapter);

        backImageView = findViewById(R.id.backImageView);
        doneButton = findViewById(R.id.doneOrderButton);
        sharedPreferences = getSharedPreferences("NotificationPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        backImageView.setOnClickListener(v -> {
            Intent intent = new Intent(ChefActivity.this, First.class);
            startActivity(intent);
            finish();
        });

        doneButton.setOnClickListener(v -> {
            String orderId = databaseReference.push().getKey();
            if (orderId != null) {
                saveNotification(orderId);
                Toast.makeText(ChefActivity.this, "Order Done", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        fetchOrderData();
    }

    private void fetchOrderData() {
        // Fetch order data from Firebase (if necessary)
    }

    private void saveNotification(String orderId) {
        editor.putString(orderId, "Order is ready!");
        editor.apply();
        updateNotificationList(orderId);
    }

    private void updateNotificationList(String orderId) {
        // Assuming you have a Notification class to display notifications
        NotificationItem notificationItem = new NotificationItem(orderId, "Chef", "Order is ready!");
        notificationList.add(notificationItem);
        orderAdapter.notifyDataSetChanged();
    }
}
