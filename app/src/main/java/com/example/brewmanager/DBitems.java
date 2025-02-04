package com.example.brewmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBitems {

    private static final String DB_NAME = "coffee_orders.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TABLE_NO = "table_no";
    private static final String COLUMN_PRODUCT_NAME = "product_name";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_TOTAL_PRICE = "total_price";

    private SQLiteDatabase db;

    public DBitems(Context context) {
        db = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        createTable();
    }

    private void createTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TABLE_NO + " INTEGER, "
                + COLUMN_PRODUCT_NAME + " TEXT, "
                + COLUMN_PRICE + " TEXT, "
                + COLUMN_QUANTITY + " INTEGER, "
                + COLUMN_TOTAL_PRICE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    public void addOrder(int tableNo, String productName, String price, int quantity) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TABLE_NO, tableNo);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_TOTAL_PRICE, calculateTotalPrice(price, quantity));

        db.insert(TABLE_NAME, null, values);
    }

    public List<String> getOrders(int tableNo) {
        List<String> orders = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TABLE_NO + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(tableNo)});

        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex(COLUMN_PRODUCT_NAME);
            int priceIndex = cursor.getColumnIndex(COLUMN_PRICE);
            int quantityIndex = cursor.getColumnIndex(COLUMN_QUANTITY);
            int totalPriceIndex = cursor.getColumnIndex(COLUMN_TOTAL_PRICE);

            if (nameIndex == -1 || priceIndex == -1 || quantityIndex == -1 || totalPriceIndex == -1) {
                Log.e("DBitems", "Column not found in the table");
            } else {
                while (cursor.moveToNext()) {
                    String productName = cursor.getString(nameIndex);
                    String price = cursor.getString(priceIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    String totalPrice = cursor.getString(totalPriceIndex);
                    orders.add("Product: " + productName + ", Price: " + price + ", Quantity: " + quantity + ", Total: " + totalPrice);
                }
            }
            cursor.close();
        }

        return orders;
    }

    private String calculateTotalPrice(String price, int quantity) {
        double priceValue = Double.parseDouble(price);
        return String.valueOf(priceValue * quantity);
    }

    public void close() {
        db.close();
    }
}
