package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LUXMeter extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    Button btnbacktoHome;

    TextView luxValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luxmeter);

        luxValue = findViewById(R.id.LUXValue);
        btnbacktoHome = findViewById(R.id.backtoHome5);


        //Register Sensor and Assign Ambient light sensor to sensor1
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        btnbacktoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    // This is method that will execute when detect a change of sensor.
    // if change of sensor is more than assumed value, it will change text fields and TextView.
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

            luxValue.setText(String.valueOf(sensorEvent.values[0]) + " LUX");
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    // method that register SensorEventListener app is in onResume State
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    //method that Unregister SensorEventListener when app is in onPause State.
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}