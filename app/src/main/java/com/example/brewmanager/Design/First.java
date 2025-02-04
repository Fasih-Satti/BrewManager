package com.example.brewmanager.Design;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.brewmanager.DBHelper;
import com.example.brewmanager.R;
import com.example.brewmanager.dashboard.CashierActivity;
import com.example.brewmanager.dashboard.ChefActivity;
import com.example.brewmanager.dashboard.TableReserve;

public class First extends AppCompatActivity {

    private LinearLayout waiterLayout, chefLayout, cashierLayout;
    private ImageView waiterImage, chefImage, cashierImage, btnNavigation;
    private String selectedRole = "";
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        waiterLayout = findViewById(R.id.waiterLayout);
        chefLayout = findViewById(R.id.chefLayout);
        cashierLayout = findViewById(R.id.cashierLayout);
        waiterImage = findViewById(R.id.waiterImage);
        chefImage = findViewById(R.id.chefImage);
        cashierImage = findViewById(R.id.cashierImage);
        btnNavigation = findViewById(R.id.btn_navigation);

        waiterLayout.setOnClickListener(v -> {
            resetBorders();
            waiterLayout.setBackgroundResource(R.drawable.green_border);
            selectedRole = "Waiter";
            Toast.makeText(First.this, "Waiter Selected", Toast.LENGTH_SHORT).show();
        });

        chefLayout.setOnClickListener(v -> {
            resetBorders();
            chefLayout.setBackgroundResource(R.drawable.green_border);
            selectedRole = "Chef";
            Toast.makeText(First.this, "Chef Selected", Toast.LENGTH_SHORT).show();
        });

        cashierLayout.setOnClickListener(v -> {
            resetBorders();
            cashierLayout.setBackgroundResource(R.drawable.green_border);
            selectedRole = "Cashier";
            Toast.makeText(First.this, "Cashier Selected", Toast.LENGTH_SHORT).show();
        });

        btnNavigation.setOnClickListener(v -> {
            if (selectedRole.isEmpty()) {
                Toast.makeText(First.this, "Please select a role", Toast.LENGTH_SHORT).show();
            } else {
                if (selectedRole.equals("Waiter")) {
                    showDialogForWaiter();
                } else if (selectedRole.equals("Chef")) {
                    Intent intent = new Intent(First.this, ChefActivity.class);
                    startActivity(intent);
                } else if (selectedRole.equals("Cashier")) {
                    Intent intent = new Intent(First.this, CashierActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void showDialogForWaiter() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.custom_waiter_dialog, null);
        EditText nameInput = dialogView.findViewById(R.id.name_input);
        CheckBox confirmName = dialogView.findViewById(R.id.confirm_checkbox_name);
        Button confirmButton = dialogView.findViewById(R.id.confirm_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        confirmButton.setOnClickListener(v -> {
            String enteredName = nameInput.getText().toString().trim();
            if (enteredName.isEmpty()) {
                Toast.makeText(First.this, "Please enter a name", Toast.LENGTH_SHORT).show();
            } else if (!confirmName.isChecked()) {
                Toast.makeText(First.this, "Please confirm the name", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.insertWaiter(enteredName);  // Insert the entered waiter name into the database
                Toast.makeText(First.this, "Waiter added successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(First.this, TableReserve.class);
                startActivity(intent);
            }
        });

        dialog.show();
    }

    private void checkIfWaiterExists(String enteredName, AlertDialog dialog) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM waiters WHERE " + DBHelper.COLUMN_WAITER_NAME + " = ?", new String[]{enteredName});
            if (cursor != null && cursor.getCount() > 0) {
                cursor.close();
                Toast.makeText(First.this, "Welcome, " + enteredName, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Intent intent = new Intent(First.this, TableReserve.class);
                startActivity(intent);
            } else {
                ContentValues values = new ContentValues();
                values.put(DBHelper.COLUMN_WAITER_NAME, enteredName);
                long result = db.insert(DBHelper.TABLE_WAITERS, null, values);
                if (result != -1) {
                    Toast.makeText(First.this, "Waiter added successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = new Intent(First.this, TableReserve.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(First.this, "Error adding waiter", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (SQLException e) {
            Toast.makeText(First.this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private void resetBorders() {
        waiterLayout.setBackgroundResource(R.drawable.border);
        chefLayout.setBackgroundResource(R.drawable.border);
        cashierLayout.setBackgroundResource(R.drawable.border);
    }
}
