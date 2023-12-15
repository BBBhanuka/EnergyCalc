package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UsageWattage extends AppCompatActivity implements SensorEventListener {

    //variables declaration
    EditText inputWattage;
    EditText inputHours;
    TextView output;
    String wattage_str;
    String hours_str;
    private SensorManager sensorManager;
    private Sensor sensor1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_wattage);

        //Register Sensor and Assign Gyroscope sensor to sensor1
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // assignment of declared variables
        inputWattage = findViewById(R.id.InputAmperage);
        inputHours = findViewById(R.id.inputHours);
        output = findViewById(R.id.output);

        final Button usageCalWatt = findViewById(R.id.UsageCal);
        final Button backToHourlyUsage = findViewById(R.id.BacktoHourlyUsage);

        // create on click listener and action
        usageCalWatt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //variable declaration and assignment
                wattage_str = inputWattage.getText().toString();
                hours_str = inputHours.getText().toString();

                // check if inputs are empty and error message if they are empty
                if (wattage_str.isEmpty() || hours_str.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Check your Input", Toast.LENGTH_SHORT).show();
                } else {

                    // parse variables into integers and float
                    int wattage = Integer.parseInt(wattage_str);
                    float hours = Integer.parseInt(hours_str);

                    // check input are equals to zero, then toast
                    if (wattage == 0 || hours == 0) {
                        Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                    } else {
                        float results = (wattage * hours) / 1000;

                        output.setText("Usage is : " + results + "Kwh");
                    }
                }
            }
        });


        // create on click listener and action
        backToHourlyUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

    }

    // This is method that will execute when detect a change of sensor.
    // if change of sensor is more than assumed value, it will change text fields and TextView.
    @Override
    public void onSensorChanged(SensorEvent sensorEvent1) {

        if (sensorEvent1.values[0] > 5) {

            Toast.makeText(getApplicationContext(), "Input Field Cleared !", Toast.LENGTH_SHORT).show();

            inputWattage.setText("");
            inputHours.setText("");
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    // method that register SensorEventListener app is in onResume State
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor1, SensorManager.SENSOR_DELAY_GAME);
    }


    //method that Unregister SensorEventListener when app is in onPause State.
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


}