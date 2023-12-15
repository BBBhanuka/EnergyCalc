package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity  {

    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isLightSensorAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Register Sensor and Assign Ambient light sensor to sensor1
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Check if Light Sensor is available in the device
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            isLightSensorAvailable = true;
        } else {
            isLightSensorAvailable = false;
        }

        // Declare Buttons and Assignment
        final Button savingTips = (Button) findViewById(R.id.Tips);
        final Button unitConvert = (Button) findViewById(R.id.UnitConversion);
        final Button locCEB = (Button) findViewById(R.id.CEBloc);
        final Button calMonthlyBill = (Button) findViewById(R.id.CalMonthlyBill);
        final Button hourlyUsage = (Button) findViewById(R.id.HourlyUsage);
        final Button aboutApp = (Button) findViewById(R.id.btnInfo);
        final Button luxCalculation = (Button) findViewById(R.id.luxMeter);


        //set on click listeners and actions for each button


        hourlyUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HourlyUsage.class);
                startActivity(i);
            }
        });


        calMonthlyBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MonthlyBill.class);
                startActivity(i);
            }
        });


        locCEB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CEBLocation.class);
                startActivity(i);
            }
        });


        unitConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), UnitConversion.class);
                startActivity(i);
            }
        });


        savingTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), EnergySavingTips.class);
                startActivity(i);
            }
        });

        // This function check for isLightSensorAvailable and if it is not available then make toast message.
        luxCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isLightSensorAvailable) {
                    Intent i = new Intent(getApplicationContext(), LUXMeter.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Light Sensor Not Available in your Device. \nFunction Disabled !", Toast.LENGTH_LONG).show();
                }
            }
        });


        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AppInfo.class);
                startActivity(i);
            }
        });
    }



    //set action when Back button pressed

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("LOGOUT")
                .setMessage("Are you sure to Logout from App ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user wants to leave - so dismiss the dialog and exit
                        finish();
                        dialog.dismiss();

                        Intent i = new Intent(getApplicationContext(), Login.class);
                        startActivity(i);
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user is not sure, so you can exit or just stay
                        dialog.dismiss();
                    }
                }).show();
             }
}