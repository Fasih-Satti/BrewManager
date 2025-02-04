package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.brewmanager.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    private TextView productNameTextView, productPriceTextView, totalCashTextView;
    private ImageView productImageView;
    private EditText suggestionEditText;
    private ImageButton leftQuantityButton, rightQuantityButton;
    private Button addButton;
    private int quantity = 1;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Orders");

        productNameTextView = findViewById(R.id.productNameTextView);
        productPriceTextView = findViewById(R.id.productPriceTextView);
        productImageView = findViewById(R.id.productImageView);
        suggestionEditText = findViewById(R.id.suggestionEditText);
        leftQuantityButton = findViewById(R.id.leftQuantityButton);
        rightQuantityButton = findViewById(R.id.rightQuantityButton);
        addButton = findViewById(R.id.addButton);
        totalCashTextView = findViewById(R.id.totalCashTextView);

        Intent intent = getIntent();
        String productName = intent.getStringExtra("product_name");
        String productPrice = intent.getStringExtra("product_price");
        int productImage = intent.getIntExtra("product_image", 0);

        productNameTextView.setText(productName);
        productPriceTextView.setText(productPrice);
        productImageView.setImageResource(productImage);

        ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            Intent backIntent = new Intent(ProductDetailActivity.this, WaiterActivity.class);
            startActivity(backIntent);
            finish();
        });

        leftQuantityButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantity();
            }
        });

        rightQuantityButton.setOnClickListener(v -> {
            quantity++;
            updateQuantity();
        });

        addButton.setOnClickListener(v -> {
            try {
                String suggestion = suggestionEditText.getText().toString();
                String priceText = productPriceTextView.getText().toString();

                if (priceText.isEmpty()) {
                    Toast.makeText(ProductDetailActivity.this, "Price cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int price = Integer.parseInt(priceText);
                int totalPrice = quantity * price;

                // Create a map to store order details
                HashMap<String, Object> orderDetails = new HashMap<>();
                orderDetails.put("productName", productName);
                orderDetails.put("price", price);
                orderDetails.put("quantity", quantity);
                orderDetails.put("totalPrice", totalPrice);
                orderDetails.put("suggestion", suggestion);

                // Push data to Firebase Realtime Database
                databaseReference.push().setValue(orderDetails).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ProductDetailActivity.this, "Order added successfully!", Toast.LENGTH_SHORT).show();
                        suggestionEditText.setText("");
                        quantity = 1;
                        updateQuantity();
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Failed to add order!", Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (NumberFormatException e) {
                Toast.makeText(ProductDetailActivity.this, "Invalid price format!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(ProductDetailActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateQuantity() {
        TextView quantityTextView = findViewById(R.id.quantityTextView);
        quantityTextView.setText(String.valueOf(quantity));
        int totalCash = quantity * Integer.parseInt(productPriceTextView.getText().toString());
        totalCashTextView.setText("Total Cash: " + totalCash);
    }
}
