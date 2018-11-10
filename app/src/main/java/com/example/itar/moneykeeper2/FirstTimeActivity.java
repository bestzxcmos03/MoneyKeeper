package com.example.itar.moneykeeper2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class FirstTimeActivity extends AppCompatActivity{
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Perhaps set content view here
        prefs = getSharedPreferences("com.example.itar.moneykeeper2", MODE_PRIVATE);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            setContentView(R.layout.activity_firsttime);
            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    public void openRegisterInfo(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }


}

