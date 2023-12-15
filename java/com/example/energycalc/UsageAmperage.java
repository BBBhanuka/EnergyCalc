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

import org.w3c.dom.Text;

import java.util.List;

public class UsageAmperage extends AppCompatActivity implements SensorEventListener {

    //variables declaration
    EditText amperage_input ,hours_input, voltage_input;
    TextView output;
    private SensorManager sensorManager;
    private Sensor sensor2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_amperage);

        //Register Sensor and Assign Gyroscope sensor to Sensor2
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // assignment of declared variables
        amperage_input = findViewById(R.id.InputAmperage);
        hours_input = findViewById(R.id.inputHoursAmperage);
        output = findViewById(R.id.outputAmp);
        voltage_input = findViewById(R.id.Voltage);

        Button backtoHourlyUsageAmp = findViewById(R.id.BacktoHourlyUsage);
        Button usageCalAmp = findViewById(R.id.UsageCalAmperage);

        //set default value for text field
        voltage_input.setText("230");

        // create on click listener and action
        backtoHourlyUsageAmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    finish();
            }
        });

        // create on click listener and action
        usageCalAmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    //variable declaration and assignment
                    EditText amperage_input = findViewById(R.id.InputAmperage);
                    EditText hours_input = findViewById(R.id.inputHoursAmperage);
                    TextView output = findViewById(R.id.outputAmp);

                    // check if inputs are empty and error message if they are empty
                    if (amperage_input.getText().toString().isEmpty() || hours_input.getText().toString().isEmpty() || voltage_input.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Check your input", Toast.LENGTH_SHORT).show();
                    } else {

                        // parse variables into integers and float
                        int amperage = Integer.parseInt(amperage_input.getText().toString());
                        float hours = Float.parseFloat(hours_input.getText().toString());
                        int voltage = Integer.parseInt(voltage_input.getText().toString());
                        float results_amp = (amperage * voltage * hours) / 1000;

                        // check input are equals to zero, then toast
                        if (amperage == 0 || hours == 0 || voltage == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                        } else {
                            output.setText("Usage is : " + results_amp + " Kwh");
                        }
                    }
                }

                // if there is any exception , this wil run
                catch(Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // This is method that will execute when detect a change of sensor.
    // if change of sensor is more than assumed value, it will change text fields and TextView.
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.values[0] > 5) {

            Toast.makeText(getApplicationContext(), "Input Field Cleared !", Toast.LENGTH_SHORT).show();

            amperage_input.setText("");
            voltage_input.setText("230");
            hours_input.setText("");
            output.setText("");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // method that register SensorEventListener app is in onResume State
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_GAME);
    }

    //method that Unregister SensorEventListener when app is in onPause State.
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}