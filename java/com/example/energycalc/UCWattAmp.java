package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UCWattAmp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uc_watt_to_amp);

        //variable declaration and assignment of reference
        TextView outputText = findViewById(R.id.output);
        EditText inputWatt = findViewById(R.id.InputAmperage);
        EditText inputVoltage = findViewById(R.id.Voltage);
        Button convert = findViewById(R.id.Convert);
        final Button btnBack = (Button) findViewById(R.id.BacktoUC);

        //set default value to input field
        inputVoltage.setText("230");


        //create on click listeners and actions
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                    // string variables declaration and assiment
                    String text1 = inputWatt.toString();
                    String text2 = inputVoltage.toString();

            try {
                    // check if variables are empty , and toast error if they are empty
                    if (text1.isEmpty() || text2.isEmpty())
                    {
                        Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                    }

                else{
                        //parse String into integers
                        int valWatt = Integer.parseInt(inputWatt.getText().toString());
                        int valVoltage = Integer.parseInt(inputVoltage.getText().toString());

                        // if value is greater than zero then action
                        if (valWatt > 0) {

                            int result = valWatt / valVoltage;
                            outputText.setText(result + "A");

                        // if value is invalid, make toast
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                //if any exception , this will run
                catch(Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //create on click listeners and actions
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // kill the current activity
                finish();
            }
        });
    }
}