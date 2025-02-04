package com.example.brewmanager.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.brewmanager.Design.First;
import com.example.brewmanager.MainActivity;
import com.example.brewmanager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WaiterActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiter);








        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this::onBottomNavigationItemSelected);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, new ItemsFragment() ).commit();
            bottomNavigationView.setSelectedItemId(R.id.coffee);
        }
    }

    private boolean onBottomNavigationItemSelected(MenuItem item) {
        Fragment selectedFragment = null;
        if (item.getItemId() == R.id.coffee) {
            selectedFragment = new ItemsFragment();
        } else if (item.getItemId() == R.id.bill) {
            selectedFragment = new Bill();
        } else if (item.getItemId() == R.id.notification) {
            selectedFragment = new  notification();
        } else if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(WaiterActivity.this, First.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragment, selectedFragment)
                    .commit();
        }

        return true;
    }
}
