package com.example.brewmanager.dashboard;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.brewmanager.DBHelper;
import com.example.brewmanager.Design.First;
import com.example.brewmanager.R;

public class TableReserve extends AppCompatActivity {

    private EditText tableNumberInput;
    private TextView chairCountText;
    private ImageView leftArrow;
    private Button nextButton;
    private boolean[] chairSelection = new boolean[8];
    private ImageView[] chairIcons = new ImageView[8];
    private DBHelper dbHelper;
    public static String tableNumber;
    public static int selectedChairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_reserve);

        dbHelper = new DBHelper(this);

        tableNumberInput = findViewById(R.id.edit_text_table_number);
        chairCountText = findViewById(R.id.total_chairs);
        leftArrow = findViewById(R.id.image_button);
        nextButton = findViewById(R.id.move_next_button);

        chairIcons[0] = findViewById(R.id.checkpoint1);
        chairIcons[1] = findViewById(R.id.checkpoint2);
        chairIcons[2] = findViewById(R.id.checkpoint3);
        chairIcons[3] = findViewById(R.id.checkpoint4);
        chairIcons[4] = findViewById(R.id.checkpoint5);
        chairIcons[5] = findViewById(R.id.checkpoint6);
        chairIcons[6] = findViewById(R.id.checkpoint7);
        chairIcons[7] = findViewById(R.id.checkpoint8);

        leftArrow.setOnClickListener(v -> {
            Intent intent = new Intent(TableReserve.this, First.class);
            startActivity(intent);
        });

        nextButton.setOnClickListener(v -> {
            tableNumber = tableNumberInput.getText().toString().trim();
            selectedChairs = getSelectedChairsCount();
            if (tableNumber.isEmpty()) {
                Toast.makeText(TableReserve.this, "Please enter a table number", Toast.LENGTH_SHORT).show();
            } else {
                saveTableData(tableNumber, selectedChairs);
                Intent intent = new Intent(TableReserve.this, WaiterActivity.class);
                startActivity(intent);
            }
        });

        for (int i = 0; i < chairIcons.length; i++) {
            final int index = i;
            chairIcons[i].setOnClickListener(v -> toggleChairSelection(index));
        }
    }

    private void toggleChairSelection(int chairIndex) {
        chairSelection[chairIndex] = !chairSelection[chairIndex];
        if (chairSelection[chairIndex]) {
            chairIcons[chairIndex].setImageResource(R.drawable.chair_green);
        } else {
            chairIcons[chairIndex].setImageResource(R.drawable.ic_check_normal);
        }
        updateChairCount();
    }

    private void updateChairCount() {
        int selectedChairs = getSelectedChairsCount();
        chairCountText.setText("Selected chairs: " + selectedChairs);
    }

    private int getSelectedChairsCount() {
        int selectedChairs = 0;
        for (boolean isSelected : chairSelection) {
            if (isSelected) {
                selectedChairs++;
            }
        }
        return selectedChairs;
    }

    private void saveTableData(String tableNumber, int selectedChairs) {
        if (dbHelper.checkIfTableExists(tableNumber)) {
            Toast.makeText(TableReserve.this, "Table already exists", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.insertTable(tableNumber, selectedChairs);
            Toast.makeText(TableReserve.this, "Table saved successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
