package com.example.itar.moneykeeper2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void dialogAlert(View v) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage("Do you want to set pin code?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent itn = new Intent(getApplicationContext(), PinSetting.class);
                startActivity(itn);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent itn = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(itn);
            }
        });
        builder.show();
    }
}
