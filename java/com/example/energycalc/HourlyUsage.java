package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HourlyUsage extends AppCompatActivity {

    //create object from databaseHelper
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_usage);

        //assign reference for object
        myDB = new DatabaseHelper(this);


        // create variable and add reference for them
        final Button backtoMain1 = (Button) findViewById(R.id.BacktoHome);
        final Button calByWattage = (Button) findViewById(R.id.byWattage);
        final Button calByAmp = (Button) findViewById(R.id.byAmperage);
        final Button addEQinfo = (Button) findViewById(R.id.addInfo);
        final Button calMultiple = (Button) findViewById(R.id.CalMulti);


        // create on click listeners and assign actions for them

        backtoMain1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        calByWattage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UsageWattage.class);
                startActivity(i);
            }
        });

        calByAmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UsageAmperage.class);
                startActivity(i);
            }
        });

        addEQinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddEQinfo.class);
                startActivity(i);
            }
        });

        calMultiple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calMultiple();
            }
        });
    }

    public void calMultiple() {

        // get number of records from equipment information table in database and assign it to integer variable
        int numberOfRecords = myDB.getRowCountEQTable(AppGlobal.username);

        //check previously declared variable is equals to zero and less than zero and make toast message accordingly
        if (numberOfRecords == 0 || numberOfRecords < 0 ) {
            Toast.makeText(HourlyUsage.this, "No Equipment information in Database", Toast.LENGTH_SHORT).show();
        }

        // if integer is greater than 0, then following action will be execute
        else {
            Intent intent = new Intent(this, UsageMultiple.class);
            startActivity(intent);
        }

    }
}