package com.example.itar.moneykeeper2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MoneyDataBase";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Transactions";
    public static final String COL_DATE = "Date";
    public static final String COL_TYPE = "Type";
    public static final String COL_AMOUNT = "Amount";
    public static final String TOTAL_INCOME = "TotalIncome";
    public static final String TOTAL_SAVING = "TotalSaving";
    public static final String TOTAL_EXPENSE = "TotalType";
    public static final String TOTAL_AUTOEXPENSE = "TotalAmount";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE+ " DATE, " + COL_TYPE + " TEXT, "
                + COL_AMOUNT + " INTEGER, " + TOTAL_INCOME + " INTEGER, " + TOTAL_SAVING + " INTEGER" +
                ", " + TOTAL_EXPENSE+ " INTEGER, " + TOTAL_AUTOEXPENSE + " INTEGER);");



        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_DATE + ", " + COL_TYPE
                + ", " + COL_AMOUNT +") VALUES (250,'MoneyTest', 1750);");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

