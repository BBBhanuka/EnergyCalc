package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MonthlyBill extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_bill);

        // Declaration and assignment of String variable
        String messageRates = "Rates Breakdown\n";

        //Declaration and assign of Button variable
        final Button buttonBack = findViewById(R.id.btnBacktoHomeMonthlyBill);
        final Button calculate = findViewById(R.id.btnCalmonthlyBill);

        //set on click listener and assign action to it
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //set on click listener and assign action to it
        calculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Declaration and assignment of variables
                EditText editTextNumber = findViewById(R.id.editTextNumber);
                String enteredText = String.valueOf(editTextNumber.getText());
                TextView output1 = findViewById(R.id.details_1);
                TextView output2 = findViewById(R.id.details_2);

                    //check if textField is empty,and if it is toast an error
                if (enteredText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter a value in input field", Toast.LENGTH_SHORT).show();
                } else {

                    //declare integer variables and initialization
                    int points = Integer.parseInt(enteredText);
                    int totalPoints = 0;
                    int fixed_charges = 400;
                    int block1 = 0;
                    int block2 = 0;
                    int block1_2 = 0;
                    int block3_4 = 0;
                    int block5 = 0;


                    // check some conditions and if condition is fulfilled, then actions accordingly
                    if (points <= 30 && points >= 0) {
                        block1 = points * 30;

                    } else if (points >= 31 && points <= 60) {
                        block1 = 30 * 30;
                        block2 = (points - 30) * 37;
                        fixed_charges = 550;

                    } else if (points >= 60 && points < 90) {
                        fixed_charges = 650;
                        block1_2 = points * 42;

                    } else if (points >= 90 && points <= 180) {
                        fixed_charges = 1500;
                        block1_2 = 90 * 42;
                        block3_4 = (points - 90) * 50;
                    } else {
                        fixed_charges = 2000;
                        block1_2 = 90 * 42;
                        block3_4 = 90 * 50;
                        block5 = (points - 180) * 75;
                    }

                    if(points >= 0) {
                        totalPoints = fixed_charges + block1 + block2 + block1_2 + block3_4 + block5;

                        String final_val = String.valueOf(totalPoints);
                        output1.setText("No of Units(Kwh) = " + points + "\n" + "Fixed Charges = " + fixed_charges + "\n" + "Total Bill Amount = " + final_val);
                    }
                    if (points == 0) {
                        output2.setText("Only Fixed Charges"); }

                    else if (points <= 30 && points >= 0) {
                        output2.setText(messageRates + "0 - 30 (Rs.30): " + block1);

                    } else if (points <= 60 && points > 30) {
                        output2.setText(messageRates + "0 - 30 (Rs.30) : " + block1 + "\n" + "31 - 60 (Rs.37) : " + block2);

                    } else if (points <= 90 && points > 60) {
                        output2.setText(messageRates + "0 - 90 (Rs.42) : " + block1_2);

                    } else if (points <= 120 && points > 90) {
                        output2.setText(messageRates + "0 - 90 (Rs.42) : " + block1_2 + "\n" + "91 - 120 (Rs.50) : " + block3_4);

                    } else if (points <= 180 && points > 120) {
                        output2.setText(messageRates + "0 - 90 (Rs.42) : " + block1_2 + "\n" + "91 - 180 (Rs.50) : " + block3_4);

                    } else if (points > 180) {
                        output2.setText(messageRates + "0 - 90 (Rs.42): " + block1_2 + "\n" + "91 - 180 (Rs.50) : " + block3_4 + "\n" + "181 + (Rs.75) : " + block5);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Number of Points must be positive or 0", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}