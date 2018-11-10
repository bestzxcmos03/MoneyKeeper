package com.example.itar.moneykeeper2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {

    /*RegisterActivity reg = new RegisterActivity();
    String name = reg.getUSERNAME();
    String strBalance = reg.getBALANCE();
    int intBalance = Integer.parseInt(strBalance);*/

    private static String TAG = "MainActivity";
    private float[] yData = {130,230,330,410};
    private String[] xData = {"Income","Saving","Expense","Auto-Expense"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: starting to create chart");
        setupPieChart();
        setupList();
    }

    public void addNewTransaction(View v){
        Intent itn = new Intent(this, NewTransaction.class);
        startActivity(itn);

    }
    public void checkTransaction(View v){
        Intent itn = new Intent(this, CheckTransaction.class);
        startActivity(itn);
    }
    public void setupList(){
        List<Data> datas = new ArrayList<Data>();
        datas.add(new Data("Balance","Update: Today"));
        datas.add(new Data("Saving","Update: Today"));
        datas.add(new Data("Income","Update: Today"));
        datas.add(new Data("Auto-Income","Update: Today"));
        datas.add(new Data("Expense","Update: Today"));
        datas.add(new Data("Auto-Expense","Update: Today"));
        MyAdapter adapter = new MyAdapter(this,datas);
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adapter);


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

