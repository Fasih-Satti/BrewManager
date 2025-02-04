package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.brewmanager.R;
import com.example.brewmanager.Design.First;
import java.util.ArrayList;
import java.util.List;

public class CashierActivity extends AppCompatActivity {

    private RecyclerView cashRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(CashierActivity.this, First.class);
            startActivity(intent);
            finish();
        });

        cashRecyclerView = findViewById(R.id.cashRecyclerView);
        CashAdapter cashAdapter = new CashAdapter(getDummyCash());
        cashRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cashRecyclerView.setAdapter(cashAdapter);
    }

    private List<Cash> getDummyCash() {
        List<Cash> cashList = new ArrayList<>();
        cashList.add(new Cash("Order 1", "Fasih", 5, "1", "Table 5"));
        cashList.add(new Cash("Order 2", "Fasih", 3, "1", "Table 5"));
        return cashList;
    }
}
