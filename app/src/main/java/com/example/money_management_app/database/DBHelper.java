package com.example.money_management_app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "money_manager.db";
    public static final int DB_VERSION = 2; // 🔥 Increased version

    public static final String TABLE_TXN = "transactions";
    public static final String COL_ID = "id";
    public static final String COL_FRIEND = "friend_name";
    public static final String COL_ITEM = "item";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_TYPE = "type"; // "+" or "-"
    public static final String COL_DATE = "date"; // ✅ New column

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_TXN + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_FRIEND + " TEXT NOT NULL, " +
                COL_ITEM + " TEXT NOT NULL, " +
                COL_AMOUNT + " REAL NOT NULL, " +
                COL_TYPE + " TEXT NOT NULL CHECK (" + COL_TYPE + " IN ('+','-')), " +
                COL_DATE + " TEXT NOT NULL" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TXN);
        onCreate(db);
    }

    // ✅ Updated Insert Method (with date)
    public boolean insertTransaction(String friend, String item, double amount, String type, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_FRIEND, friend);
        cv.put(COL_ITEM, item);
        cv.put(COL_AMOUNT, amount);
        cv.put(COL_TYPE, type);
        cv.put(COL_DATE, date);

        return db.insert(TABLE_TXN, null, cv) != -1;
    }

    public Cursor getAllTransactions() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(
                "SELECT * FROM " + TABLE_TXN +
                        " ORDER BY " + COL_ID + " ASC",
                null
        );
    }
}