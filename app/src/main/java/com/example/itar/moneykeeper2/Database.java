package com.example.itar.moneykeeper2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;

class DatabaseHelper extends SQLiteOpenHelper {
    Cursor c;
    private static final String DB_NAME = "MoneyDataBase";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "Transactions";
    public static final String COL_DATE = "Date";
    public static final String COL_TYPE = "Type";
    public static final String COL_AMOUNT = "Amount";
    public static final String COL_DESC = "Descripftion";
    public static final String TOTAL_INCOME = "TotalIncome";
    public static final String TOTAL_SAVING = "TotalSaving";
    public static final String TOTAL_EXPENSE = "TotalExpense";
    public static final String TOTAL_BALANCE = "TotalBalance";
    public static final String NAME = "Name";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    SQLiteDatabase db;
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(" CREATE TABLE " + TABLE_NAME +" ("+_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_DATE + " TEXT , " + COL_DESC + " TEXT , " + COL_AMOUNT + " INTEGER , " +
                ""+ COL_TYPE + " TEXT , " + TOTAL_INCOME + " INTEGER , " + TOTAL_SAVING + " INTEGER , " + TOTAL_EXPENSE+ " INTEGER, " + TOTAL_BALANCE+ " INTEGER ," + NAME+ " TEXT);");


        /*db.execSQL("INSERT INTO " + TABLE_NAME + "("+COL_DATE+","+COL_DESC+","+COL_AMOUNT+","+COL_TYPE+","+TOTAL_SAVING+","+TOTAL_INCOME+","+TOTAL_EXPENSE+","+TOTAL_BALANCE+")"
                + "VALUES('"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"','"+0+"')");*/

    }

    public void setDB(String date,String type,int amount,String desc,int income,int saving,int expense,int balance){
        db.execSQL("INSERT INTO " + TABLE_NAME + "("+COL_DATE+","+COL_DESC+","+COL_AMOUNT+","+COL_TYPE+","+TOTAL_INCOME+","+TOTAL_SAVING+","+TOTAL_EXPENSE+","+TOTAL_BALANCE+")"
                + "VALUES('"+date+"','"+type+"','"+amount+"','"+desc+"','"+income+"','"+saving+"','"+expense+"','"+balance+"')");
    }


    /*public int getIncome(){
        int ans;
        c = db.rawQuery("SELECT TOTAL_INCOME from TABLE_NAME ",null);
        c.moveToFirst();
        ans=c.getInt(c.getColumnIndex("TOTAL_INCOME"));
        return ans;
    }*/

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

