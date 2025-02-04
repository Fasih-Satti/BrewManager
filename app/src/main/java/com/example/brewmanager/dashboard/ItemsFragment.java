package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.brewmanager.R;
import com.example.brewmanager.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemsFragment extends Fragment {

    private ProductAdapter adapter;
    private List<Product> allProducts;
    private TextView waiterNameTextView, tableInfoTextView, reserveNewTableTextView;
    private DBHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.gridRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        waiterNameTextView = view.findViewById(R.id.waiterNameTextView);
        tableInfoTextView = view.findViewById(R.id.orderForTableTextView);
        reserveNewTableTextView = view.findViewById(R.id.reserveNewTableTextView);

        dbHelper = new DBHelper(getContext());

        allProducts = new ArrayList<>(Arrays.asList(
                new Product(R.drawable.img_1, "Espresso", "50"),
                new Product(R.drawable.img_2, "Cappuccino", "75"),
                new Product(R.drawable.img_3, "Latte", "100"),
                new Product(R.drawable.img_4, "Americano", "120"),
                new Product(R.drawable.img_5, "Mocha", "150"),
                new Product(R.drawable.img_6, "Macchiato", "200")
        ));

        adapter = new ProductAdapter(getContext(), allProducts, product -> openProductDetailActivity(product));
        recyclerView.setAdapter(adapter);

        updateTableInfo();

        reserveNewTableTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TableReserve.class);
            startActivity(intent);
        });

        loadWaiterName();

        ImageButton imageButton = view.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), OrderCheck.class);
            startActivity(intent);
        });

        return view;
    }

    private void openProductDetailActivity(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("product_name", product.getName());
        intent.putExtra("product_price", product.getPrice());
        intent.putExtra("product_image", product.getImageResId());
        startActivity(intent);
    }

    private void updateTableInfo() {
        String tableNumber = "Table No: " + TableReserve.tableNumber;
        int selectedSeats = TableReserve.selectedChairs;
        tableInfoTextView.setText(tableNumber + " - Reserved Seats: " + selectedSeats + "/8");
    }

    private void loadWaiterName() {
        // Call DBHelper to get the waiter name
        String waiterName = dbHelper.getWaiterName();
        if (waiterName != null) {
            waiterNameTextView.setText("Waiter: " + waiterName);
        } else {
            waiterNameTextView.setText("Waiter: Unknown");
        }
    }
}
