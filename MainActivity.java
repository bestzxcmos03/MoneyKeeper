package com.example.itar.moneykeeper2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    float balance;
    SQLiteDatabase db;
    DatabaseHelper dbHelp;
    Cursor cur;
    ArrayList<String> Array = new ArrayList<>();
    boolean bincome;
    boolean bsaving;
    boolean bexpense;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date,DATE;
    Button create;
    ListView transactionListView;

    /*RegisterActivity reg = new RegisterActivity();
    String name = reg.getUSERNAME();
    String strBalance = reg.getBALANCE();
    int intBalance = Integer.parseInt(strBalance);*/
    public SharedPreferences prefs = null;
    private static String TAG = "banana";
    private float[] yData = {10,20,30,0};
    private String[] xData = {"Income","Saving","Expense"};
    private String[] zData = {"Income","Saving","Expense","Balance"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Running onCreate in MainActivity");
        prefs = getSharedPreferences("com.example.itar.moneykeeper2", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        checkFirstTime();
        bincome = false;
        bsaving = false;
        bexpense = false;

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
        simpleDateFormat = new SimpleDateFormat("dd-MM-YY");
        Date = simpleDateFormat.format(calendar.getTime());
        EditText description = findViewById(R.id.editText3);
        EditText amount = findViewById(R.id.editText5);
        String date = simpleDateFormat.format(calendar.getTime());
        String des = description.getText().toString();
        int Amount = Integer.parseInt( amount.getText().toString());
        String transactionType;
        if(bincome){
            yData[0]+=Amount ;
            transactionType = "Income"; }
            else if(bsaving){
                yData[1]+=  Amount;
                    transactionType = "Saving";}
                else{
                    yData[2]+= Amount;
                    transactionType = "Expense";}
        yData[3]=yData[0]+yData[1]-yData[2];

        //db.execSQL("INSERT INTO " + dbHelp.TABLE_NAME + "("+dbHelp.COL_DATE+","+dbHelp.COL_DESC+","+dbHelp.COL_AMOUNT+")"+ "VALUES('"+date+"','"+des+"','"+Amount+"')");




        setContentView(R.layout.activity_main);
        setupPieChart();
        setupList();
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
            yData[0] = 0;
            yData[1] = 0;
            yData[2] = 0;
            yData[3] = 0;
            prefs.edit().putBoolean("firstrun", false).apply();
        }
        else{
            Log.d(TAG, "Not FirstRun \nStarting main class ");
            setContentView(R.layout.activity_main);
            setupPieChart();
            setupList();
        }
    }
    public void openRegisterInfo(View v) {
        Log.d(TAG, "Opening RegisterInfo");
        //startActivity(new Intent(this, RegisterActivity.class));
        setContentView(R.layout.activity_register);
    }

    CharSequence text;

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
        Intent itn = new Intent(this, CheckTransaction.class);
        startActivity(itn);
    }

    public void goToMain(View v){
        setContentView(R.layout.activity_main);
        setupList();
        setupPieChart();
    }
    public void dialogAlert(View v) {
        EditText bal = findViewById(R.id.editText2);
        Log.d(TAG, " Dialog Alerting");


        /*SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YY");
        String date = sdf.format(calendar.getTime());*/
        balance = Integer.parseInt( bal.getText().toString());
        /*db.execSQL("INSERT INTO " + dbHelp.TABLE_NAME + "("+dbHelp.COL_DESC+","+dbHelp.COL_AMOUNT+")"+
                "VALUES('"+"New User"+"','"+balance+"')");*/
        yData[0] = balance;
        /*Intent itn = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(itn);*/
        setContentView(R.layout.activity_main);
        setupPieChart();
        setupList();
    }

    public void setupList(){
        ListView lv = findViewById(R.id.listView);
        Log.d(TAG, "set up list");



        /*ArrayList<String> dirArray = new ArrayList<>();
        dbHelp = new DatabaseHelper(this);
        db = dbHelp.getWritableDatabase();
        cur = db.rawQuery(" SELECT "+ dbHelp.COL_TYPE +", " +dbHelp.COL_DATE+","+dbHelp.COL_AMOUNT
                +" FROM "+ dbHelp.TABLE_NAME,null);
        cur.moveToFirst();
        db.execSQL("INSERT INTO " + dbHelp.TABLE_NAME + "("+dbHelp.COL_DATE+","+dbHelp.COL_DESC+","+dbHelp.COL_AMOUNT+")"+ "VALUES('"+0+"','"+0+"','"+0+"')")*/;
        Array.clear();
        for(int i = 0 ; i<4; i++){
            Array.add(zData[i]+" ----> "+yData[i]);

            /*dirArray.add(zData[i]+" ----> "+cur.getString(cur.getColumnIndex(dbHelp.COL_AMOUNT)));
            cur.moveToNext(); */
        }
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Array);
        lv.setAdapter(adapterDir);
        /*datas.add(new Data("Balance","Update: Today","2000$"));
        datas.add(new Data("Saving","Update: 10/11/2018","3000$"));
        datas.add(new Data("Income","Update: Today","1000$"));
        datas.add(new Data("Auto-Income","Update: 8/11/2018","500$"));
        datas.add(new Data("Expense","Update: Today","800$"));
        datas.add(new Data("Auto-Expense","Update: 25/10/2018","90$"));6*/


    }

    public void setupPieChart(){
        Log.d(TAG, "set up chart");
        PieChart chart = findViewById(R.id.idPieChart);

        chart.setRotationEnabled(true);
        chart.setHoleRadius(0f);
        //chart.setCenterTextColor(Color.BLACK);
        chart.setTransparentCircleAlpha(0);
        //chart.setCenterText("Super Cool Chart");
        //chart.setCenterTextSize(10);
        chart.setDrawEntryLabels(true);
        chart.setEntryLabelTextSize(20);
        chart.setUsePercentValues(false);

        List <PieEntry> pieEntries = new ArrayList<PieEntry>();
        for(int i = 0 ; i<3; i++){
            if(yData[i]>0)
                pieEntries.add(new PieEntry(yData[i],xData[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntries, " ");
        dataSet.setValueTextSize(20);
        dataSet.setValueTextColor(WHITE);
        PieData data = new PieData(dataSet);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ContextCompat.getColor(this, R.color.GreenIncome));
        colors.add(ContextCompat.getColor(this, R.color.BlueSaving));
        colors.add(ContextCompat.getColor(this, R.color.RedExpense));
        dataSet.setColors(colors);
        dataSet.setSliceSpace(2);

        chart.setData(data);
        chart.invalidate();
    }
}

/*while ( !cur.isAfterLast() ){
        dirArray.add(cur.getString(cur.getColumnIndex(dbHelp.COL_TYPE)) + "\n"
        + xData[0] + cur.getString(cur.getColumnIndex(dbHelp.COL_DATE)) + "\t\t"
        + "Cake : " + cur.getString(cur.getColumnIndex(dbHelp.COL_AMOUNT)));
        cur.moveToNext();
        i++;
        }*/