package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EnergySavingTips extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_energy_saving_tips);



        //declaration and add reference for variables
        final Button btnVideoTips =  findViewById(R.id.videotips);
        final Button btnBacktoHome =  findViewById(R.id.backtoHome4);


        //create on click listeners and assign actions
        btnVideoTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), VideoTips.class);
                startActivity(i);
            }
        });

        btnBacktoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}