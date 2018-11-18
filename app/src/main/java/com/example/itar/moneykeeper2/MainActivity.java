package com.example.itar.moneykeeper2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {
    ContentValues val = new ContentValues();
    int ttSaving;
    int ttIncome;
    int ttExpense;
    int ttBalance;
    int balance;
    boolean doOneTime=true;
    boolean bincome;
    boolean bsaving;
    boolean bexpense;
    boolean isPressed;
    public SharedPreferences prefs = null;
    private static String TAG = "banana";
    private int[] yData=new int[4];
    private String[] xData = {"Income","Saving","Expense"};
    private String[] zData = {"Balance","Income","Saving","Expense"};
    SQLiteDatabase db;
    DatabaseHelper dbHelp;
    Cursor cur;
    CharSequence text;
    String StrnName;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;
    Button create;
    ListView transactionListView;
    EditText nickName;
    TextView Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Running onCreate in MainActivity");
        dbHelp = new DatabaseHelper(this);
        db = dbHelp.getWritableDatabase();
        prefs = getSharedPreferences("com.example.itar.moneykeeper2", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        isPressed = false;
        checkFirstTime();

        //yData[0]=dbHelp.getIncome();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "Running onPause in MainActivity");

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Running onPause in MainActivity");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Running onDestroy in MainActivity");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Running onStop in MainActivity");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "Running onResume in MainActivity");

    }
    public void create(View v){
        transactionListView = (ListView)findViewById(R.id.transactionListView);
        create = (Button)findViewById(R.id.create);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        Date = simpleDateFormat.format(calendar.getTime());
        EditText description = findViewById(R.id.editText3);
        EditText amount = findViewById(R.id.editText5);
        String date = simpleDateFormat.format(calendar.getTime());
        String des = description.getText().toString();
        yData[0]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_BALANCE)));
        yData[1]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_INCOME)));
        yData[2]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_SAVING)));
        yData[3]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_EXPENSE)));
        if(doOneTime){
            ttBalance += yData[0];
            ttIncome += yData[1];
            ttSaving += yData[2];
            ttExpense += yData[3];
            doOneTime = false;
        }
        int Amount = Integer.parseInt( amount.getText().toString());
        String transactionType;
        if(bincome){
            transactionType = "Income";
            ttIncome+=Amount;
            isPressed = true;
        } else
            if(bsaving){
                transactionType = "Saving";
                ttSaving+=Amount;
                isPressed = true;
        } else{
                transactionType = "Expense";
                ttExpense+=Amount;
                isPressed = true;
        }
        ttBalance = ttIncome-ttExpense;
        if(!isPressed){
            create(v);
        }
        db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME + "("+ DatabaseHelper.COL_DATE +"," +
                ""+ DatabaseHelper.COL_DESC +","+ DatabaseHelper.COL_AMOUNT +","+dbHelp.COL_TYPE+"," +
                ""+ DatabaseHelper.TOTAL_SAVING +","+ DatabaseHelper.TOTAL_INCOME +"," +
                ""+ DatabaseHelper.TOTAL_EXPENSE +","+ DatabaseHelper.TOTAL_BALANCE +")"
                + "VALUES('"+date+"','"+des+"','"+Amount+"','"+transactionType+"'," +
                "'"+ttSaving+"','"+ttIncome+"','"+ttExpense+"','"+ttBalance+"')");
        setContentView(R.layout.activity_main);
        setupList();
        setupPieChart();
        Context context = getApplicationContext();
        text = "Transaction Added";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void checkFirstTime(){
        if (prefs.getBoolean("firstrun", true)) {
            Log.d(TAG, "FirstRun");
            setContentView(R.layout.activity_firsttime);
            ttSaving=0;
            ttIncome=0;
            ttExpense=0;
            ttBalance=0;
            balance=0;
            bincome = false;
            bsaving = false;
            bexpense = false;
        }
        else{
            Log.d(TAG, "Not FirstRun \nStarting main class ");
            setContentView(R.layout.activity_main);
            setupList();
            setupPieChart();
        }
    }
    public void openRegisterInfo(View v) {
        Log.d(TAG, "Opening RegisterInfo");
        //startActivity(new Intent(this, RegisterActivity.class));
        setContentView(R.layout.activity_register);
    }

    public void addIncome(View v){
        bincome = true;
        bexpense = false;
        bsaving = false;
        text = "You choose Income.";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void addSaving(View v){
        bsaving = true;
        bexpense = false;
        bincome = false;
        text = "You choose Saving.";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void addExpense(View v){
        bexpense = true;
        bincome = false;
        bsaving = false;
        text = "You choose Expense.";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void addNewTransaction(View v){
        Log.d(TAG, "Adding new transaction");
        setContentView(R.layout.new_transaction);


    }
    public void checkTransaction(View v){
        Log.d(TAG, "start to check transaction");
        setContentView(R.layout.check_transaction);
        ListView lv = findViewById(R.id.transactionListView);
        ArrayList<String> dirArray = new ArrayList<>();

        cur = db.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_NAME,null);
        cur.moveToFirst();
        cur.moveToNext();
        String date;
        String amount;
        String type;
        String desc;
        while (!cur.isAfterLast()){
            date =cur.getString(cur.getColumnIndex(DatabaseHelper.COL_DATE));
            amount =cur.getString(cur.getColumnIndex(DatabaseHelper.COL_AMOUNT));
            type =cur.getString(cur.getColumnIndex(DatabaseHelper.COL_TYPE));
            desc =cur.getString(cur.getColumnIndex(DatabaseHelper.COL_DESC));
            dirArray.add(desc + " -> "+type+"\nDate: "+date+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+amount+".- BAHT");
            cur.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dirArray);
        lv.setAdapter(adapterDir);

    }
    public void goToMain(View v){
        setContentView(R.layout.activity_main);
        setupList();
        setupPieChart();
    }
    @SuppressLint("SimpleDateFormat")
    public void AfterPressRegisterButton(View v) {
        EditText bal = findViewById(R.id.editText2);
        prefs.edit().putBoolean("firstrun", false).apply();
        nickName = findViewById(R.id.nickName);
        StrnName =nickName.getText().toString();
        db.execSQL(" INSERT INTO " + DatabaseHelper.TABLE_NAME +
                "("+ DatabaseHelper.NAME +")" + " VALUES ('"+StrnName+"')");

        balance = Integer.parseInt( bal.getText().toString());
        Log.d(TAG, " Seting up first input to database");
        dbHelp.setDB( "\t\t\t\t\t\t\t\t\t\t\t"," ", balance,
                "First input Money", balance,0,0,balance);
        Log.i("Executed first SQL", TAG);
        yData[0] = balance;
        setContentView(R.layout.activity_main);
        setupList();
        setupPieChart();

    }

    @SuppressLint("SetTextI18n")
    public void setupList(){
        Title = findViewById(R.id.Title);
        Cursor c = db.rawQuery("SELECT NAME FROM "+ DatabaseHelper.TABLE_NAME,null);
        c.moveToFirst();
        Title.setText("Hello, "+c.getString(c.getColumnIndex(DatabaseHelper.NAME)));

        ListView lv = findViewById(R.id.listView);
        ArrayList<String> dirArray = new ArrayList<>();

        cur = db.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_NAME,null);
        cur.moveToLast();
        yData[0]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_BALANCE)));
        yData[1]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_INCOME)));
        yData[2]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_SAVING)));
        yData[3]=Integer.parseInt(cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_EXPENSE)));
        dirArray.add(zData[0]+" ----> "+cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_BALANCE))+
                " BAHT\nModified: "+cur.getString(cur.getColumnIndex(DatabaseHelper.COL_DATE)));
        dirArray.add(zData[1]+" ----> "+cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_INCOME))+
                " BAHT\nModified: "+cur.getString(cur.getColumnIndex(DatabaseHelper.COL_DATE)));
        dirArray.add(zData[2]+" ----> "+cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_SAVING))+
                " BAHT\nModified: "+cur.getString(cur.getColumnIndex(DatabaseHelper.COL_DATE)));
        dirArray.add(zData[3]+" ----> "+cur.getString(cur.getColumnIndex(DatabaseHelper.TOTAL_EXPENSE))+
                " BAHT\nModified: "+cur.getString(cur.getColumnIndex(DatabaseHelper.COL_DATE)));
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dirArray);
        lv.setAdapter(adapterDir);

    }

    public void setupPieChart(){
        PieChart chart = findViewById(R.id.idPieChart);
        Log.d(TAG, "Creating chart");
        chart.setRotationEnabled(true);
        chart.setHoleRadius(50f);
        chart.setHoleColor(Color.parseColor("#ffad6a"));
        chart.setCenterTextColor(Color.parseColor("#4d4d4d"));
        chart.setTransparentCircleAlpha(100);
        chart.setCenterText("Summary of Usage");
        chart.setCenterTextSize(18);
        chart.setDrawEntryLabels(true);
        chart.setEntryLabelTextSize(20);
        chart.setUsePercentValues(false);
        Log.d(TAG, "Income = "+yData[1]);
        Log.d(TAG, "Saving = "+yData[2]);
        Log.d(TAG, "Expense = "+yData[3]);
        ArrayList<Integer> colors = new ArrayList<>();
        List <PieEntry> pieEntries = new ArrayList<PieEntry>();
        if(yData[1]>0){
            pieEntries.add(new PieEntry(yData[1],xData[0]));
            colors.add(ContextCompat.getColor(this, R.color.GreenIncome));}
        if(yData[2]>0){
            pieEntries.add(new PieEntry(yData[2],xData[1]));
            colors.add(ContextCompat.getColor(this, R.color.BlueSaving));
        }
        if(yData[3]>0){
            pieEntries.add(new PieEntry(yData[3],xData[2]));
            colors.add(ContextCompat.getColor(this, R.color.RedExpense));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, " ");
        dataSet.setValueTextSize(20);
        dataSet.setValueTextColor(WHITE);
        PieData data = new PieData(dataSet);
        dataSet.setColors(colors);
        dataSet.setSliceSpace(2);
        chart.setData(data);
        chart.invalidate();
    }

    public void credits(View v){
        setContentView(R.layout.credit_activity);
    }
}
