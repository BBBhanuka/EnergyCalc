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

public class UCAmptoWatt extends AppCompatActivity implements SensorEventListener {


    // declaration of variables
    TextView outputText;
    EditText inputAmp;
    EditText inputVoltage;
    private SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucampto_watt);

          // Get the default sensor service and retrieve the gyroscope sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        //variables declaration and assignment
        outputText = findViewById(R.id.output);
        inputAmp = findViewById(R.id.InputAmperage);
        inputVoltage = findViewById(R.id.Voltage);
        Button btnBacktoUC = findViewById(R.id.BacktoUC);
        Button convert = findViewById(R.id.CalculateVal);

        //set default value to text input field
        inputVoltage.setText("230");


        // create on click listener and action
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //declaration of variables and assignment
                String text1 = inputAmp.toString();
                String text2 = inputVoltage.toString();

                try {
                    // check if inputs are empty and if they are, then make toast
                    if (text1.isEmpty() || text2.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                    } else {

                        // parse string into integer
                        int valAmp = Integer.parseInt(inputAmp.getText().toString());
                        int valVoltage = Integer.parseInt(inputVoltage.getText().toString());

                        //check if value is greater than 0 , then action
                        if (valAmp > 0) {

                            //do the calculation and show result
                            int result = valAmp * valVoltage;
                            outputText.setText(result + "W");

                         // toast if invalid input
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                        }
                    }
                 // if any exception, then this will run
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // create on click listener and action
        btnBacktoUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //action when Sensor change detection
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.values[0] > 5) {

            Toast.makeText(getApplicationContext(), "input fields cleared !", Toast.LENGTH_SHORT).show();

            inputVoltage.setText("230");
            inputAmp.setText("");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // when application is in onResume state, Registration of event listener
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }


    // when application is in onPause state, Un-Registration of event listener
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}