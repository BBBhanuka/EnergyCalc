package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UnitConversion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_conversion);


        final Button button1 = (Button) findViewById(R.id.BacktoHome3);
        final Button button2 = (Button) findViewById(R.id.ampToWatt);
        final Button button3 = (Button) findViewById(R.id.wattToAmp);


        // create on click listener and action
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // create on click listener and action
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UCAmptoWatt.class);
                startActivity(i);
            }
        });

        // create on click listener and action
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UCWattAmp.class);
                startActivity(i);
            }
        });


    }
}