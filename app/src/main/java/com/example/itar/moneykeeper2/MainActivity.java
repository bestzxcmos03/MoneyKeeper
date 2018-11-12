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

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {
    float balance;
    SQLiteDatabase db;
    DatabaseHelper dbHelp;
    Cursor cur;
    boolean bincome;
    boolean bsaving;
    boolean bexpense;
    boolean bautoExpense;


    /*RegisterActivity reg = new RegisterActivity();
    String name = reg.getUSERNAME();
    String strBalance = reg.getBALANCE();
    int intBalance = Integer.parseInt(strBalance);*/
    public SharedPreferences prefs = null;
    private static String TAG = "banana";
    private float[] yData = {1000,3000,800,500};
    private String[] xData = {"Income","Saving","Expense","Auto-Expense"};
    private String[] zData = {"Balance","Income","Saving","Expense","Auto-Expense"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Running onCreate in MainActivity");
        prefs = getSharedPreferences("com.example.itar.moneykeeper2", MODE_PRIVATE);
        setContentView(R.layout.activity_main);
        checkFirstTime();
        boolean bincome = false;
        boolean bsaving = false;
        boolean bexpense = false;
        boolean bautoIncome = false;

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

    public void checkFirstTime(){
        if (prefs.getBoolean("firstrun", true)) {
            Log.d(TAG, "FirstRun");
            setContentView(R.layout.activity_firsttime);
            prefs.edit().putBoolean("firstrun", false).apply();
        }
        else{
            Log.d(TAG, "Not FirstRun /nStarting main class ");
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

    public void addIncome(){
        bincome = true;
        bautoExpense = false;
        bexpense = false;
        bsaving = false;
        text = "You choose Income.";
    }
    public void addSaving(){
        bsaving = true;
        bautoExpense = false;
        bexpense = false;
        bincome = false;
        text = "You choose Saving.";
    }
    public void addExpense(){
        bexpense = true;
        bautoExpense = false;
        bincome = false;
        bsaving = false;
        text = "You choose Expense.";
    }
    public void addAutoExpense(){
        bautoExpense = true;
        bincome = false;
        bexpense = false;
        bsaving = false;
        text = "You choose AutoExpense.";
    }

    public void addNewTransaction(View v){
        Log.d(TAG, "Adding new transaction");
        setContentView(R.layout.new_transaction);

        EditText description = findViewById(R.id.editText3);
        EditText amount = findViewById(R.id.editText5);
        EditText day = findViewById(R.id.editText6);
        EditText month = findViewById(R.id.editText7);
        EditText year = findViewById(R.id.editText);


        db.execSQL("INSERT INTO " + dbHelp.TABLE_NAME + " (" + dbHelp.COL_DATE + ", " + dbHelp.COL_TYPE
                + ", " + dbHelp.COL_AMOUNT +") VALUES (25, description.getText().toString(), Integer.parseInt( amount.getText().toString()))");








        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();


    }
    public void checkTransaction(View v){
        Log.d(TAG, "start to check transaction");
        Intent itn = new Intent(this, CheckTransaction.class);
        startActivity(itn);
    }

    public void dialogAlert(View v) {
        EditText bal = findViewById(R.id.editText2);
        Log.d(TAG, " Dialog Alerting");
        balance = Integer.parseInt( bal.getText().toString());

        yData[0] = balance;
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do you want to set pin code?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "Selected Yes");
                setContentView(R.layout.activity_register2);
                /*Intent itn = new Intent(getApplicationContext(), PinSetting.class);
                startActivity(itn);*/
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Log.d(TAG, "Selected No");
                /*Intent itn = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(itn);*/
                setContentView(R.layout.activity_main);
                setupPieChart();
                setupList();

            }
        });
        builder.show();
    }

    public void setupList(){
        ListView lv = findViewById(R.id.listView);
        ArrayList<String> dirArray = new ArrayList<>();
        dbHelp = new DatabaseHelper(this);
        db = dbHelp.getWritableDatabase();
        cur = db.rawQuery(" SELECT "+ dbHelp.COL_TYPE +", " +dbHelp.COL_DATE+","+dbHelp.COL_AMOUNT
                +" FROM "+ dbHelp.TABLE_NAME,null);
        cur.moveToFirst();

        db.execSQL("INSERT INTO " + dbHelp.TABLE_NAME + " (" + dbHelp.COL_DATE + ", " + dbHelp.COL_TYPE
                + ", " + dbHelp.COL_AMOUNT +") VALUES (25, 'MoneyTest', 750)");
        db.execSQL("INSERT INTO " + dbHelp.TABLE_NAME + " (" + dbHelp.COL_DATE + ", " + dbHelp.COL_TYPE
                + ", " + dbHelp.COL_AMOUNT +") VALUES (25, 'MoneyTest', 200)");
        for(int i = 0 ; i<5; i++){
            dirArray.add(zData[i]+" ---->"+cur.getString(cur.getColumnIndex(dbHelp.COL_AMOUNT)));
            cur.moveToNext();
        }
        ArrayAdapter<String> adapterDir = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dirArray);
        lv.setAdapter(adapterDir);
        /*datas.add(new Data("Balance","Update: Today","2000$"));
        datas.add(new Data("Saving","Update: 10/11/2018","3000$"));
        datas.add(new Data("Income","Update: Today","1000$"));
        datas.add(new Data("Auto-Income","Update: 8/11/2018","500$"));
        datas.add(new Data("Expense","Update: Today","800$"));
        datas.add(new Data("Auto-Expense","Update: 25/10/2018","90$"));*/


    }

    public void setupPieChart(){

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
        for(int i = 0 ; i<yData.length; i++){
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
        colors.add(ContextCompat.getColor(this, R.color.PurpleAutoExpense));
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