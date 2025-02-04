package com.example.brewmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BrewManager.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tables";
    public static final String COLUMN_TABLE_NUMBER = "table_number";
    public static final String COLUMN_SELECTED_CHAIRS = "selected_chairs";

    public static final String TABLE_WAITERS = "waiters";
    public static final String COLUMN_WAITER_NAME = "waiter_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_TABLE_NUMBER + " TEXT PRIMARY KEY, " +
                COLUMN_SELECTED_CHAIRS + " INTEGER);";
        db.execSQL(createTableQuery);

        String createWaitersTableQuery = "CREATE TABLE " + TABLE_WAITERS + " (" +
                COLUMN_WAITER_NAME + " TEXT);";
        db.execSQL(createWaitersTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WAITERS);
        onCreate(db);
    }

    public String getWaiterName() {
        SQLiteDatabase db = getReadableDatabase();
        String waiterName = null;
        String query = "SELECT " + COLUMN_WAITER_NAME + " FROM " + TABLE_WAITERS + " ORDER BY ROWID DESC LIMIT 1";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_WAITER_NAME);
            if (columnIndex >= 0) {
                waiterName = cursor.getString(columnIndex);
            } else {
                waiterName = "Column not found";
            }
            cursor.close();
        } else {
            cursor.close();
            waiterName = "No waiter found";
        }

        return waiterName;
    }

    public void insertWaiter(String waiterName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WAITER_NAME, waiterName);

        db.insert(TABLE_WAITERS, null, contentValues);
        db.close();
    }

    public boolean checkIfTableExists(String tableNumber) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_TABLE_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tableNumber});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void insertTable(String tableNumber, int selectedChairs) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TABLE_NUMBER, tableNumber);
        contentValues.put(COLUMN_SELECTED_CHAIRS, selectedChairs);

        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }
}
