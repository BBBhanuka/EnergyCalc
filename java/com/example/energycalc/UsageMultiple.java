package com.example.energycalc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UsageMultiple extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    //variables declaration
    DatabaseHelper myDB;
    Spinner spinner;
    TextView  EquipWattage, numOfRows;
    String label,name;
    EditText inputHours;
    Button addTototal, finalizeCal, resetTable;
    Integer wattValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage_multiple);

        // assign references to declared variables
        myDB = new DatabaseHelper(this);

        addTototal = findViewById(R.id.addTotal);
        finalizeCal = findViewById(R.id.finalize);
        resetTable = findViewById(R.id.clearcal);

        inputHours = findViewById(R.id.HoursInput);
        EquipWattage = findViewById(R.id.EqWattage);
        numOfRows = findViewById(R.id.numberOfRows);
        numOfRows.setText("No Records");

        final Button buttonbck = findViewById(R.id.BacktoHourlyUsage2);


        spinner = findViewById(R.id.equipmentNames);
        spinner.setOnItemSelectedListener(this);

        // clear table in database when this page is loading
        myDB.clearTable();

        //load data to spinner (dropdown)
        loadSpinnerData();

        // create on click listener and action
        resetTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get row count in the table in database
                int rowCount = myDB.getRowCount();

                // if row count is not equals to zero, then following action will execute
                if(rowCount != 0 ){
                    myDB.clearTable();
                    Toast.makeText(UsageMultiple.this, rowCount + " row/s cleared !", Toast.LENGTH_SHORT).show();
                    numOfRows.setText("No Records");

                }
                // if row count is zero, following toast will display
                else{
                    Toast.makeText(UsageMultiple.this, "nothing to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // action when tap on back button, it will clear calculations table in the database
        buttonbck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                myDB.clearTable();
            }
        });



        addTototal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                        //parse string into integer
                        Integer inputH = Integer.parseInt(inputHours.getText().toString());

                        //check if input is equal or less then zero
                        if (inputH.equals(0) || inputH < 0) {
                            Toast.makeText(UsageMultiple.this, "Usage hours must be greater than 0", Toast.LENGTH_SHORT).show();
                        }

                        //actions for valid input
                        else {

                            //calculation of result
                            Integer result = wattValue * inputH;

                            //insert calculated data into the table and assign result to boolean variable
                            boolean isInserted = myDB.insertCalculatedData(name, Integer.toString(inputH), Integer.toString(result));

                            //check if data insert task is successful and then following actions will be execute
                            if (isInserted == true) {


                                Toast.makeText(UsageMultiple.this, "Data Added successfully", Toast.LENGTH_SHORT).show();
                                inputHours.setText("");

                                // get current row count in calculations table
                                int rowCount = myDB.getRowCount();

                                //display current row count in calculations table
                                numOfRows.setText("Number of Records : " + rowCount);

                                //action if data add operation is unsuccess
                            } else
                                Toast.makeText(UsageMultiple.this, "Data Add operation Unsuccessful", Toast.LENGTH_SHORT).show();
                        }

                // if there is any exception , this will execute
                } catch (Exception e) {
                    Toast.makeText(UsageMultiple.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // calling for view all method
        viewAll();
    }


    // method for load data into spinner
    private void loadSpinnerData() {
        DatabaseHelper db = new DatabaseHelper(this);
        List<String> names = db.getAllEQNames(AppGlobal.username);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names);

        // Drop down layout style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }


    //method for load value that belongs to the selected item in dropdown menu to another text lable
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        label = parent.getItemAtPosition(position).toString();
        name = parent.getItemAtPosition(position).toString();

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        List<String> values = db.getAllvalues(AppGlobal.username);

        wattValue = Integer.parseInt(values.get(position));

        EquipWattage.setText("Item Wattage :" + wattValue + "W");


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }


    //method for load all data and do the calculation for final result
    public void viewAll() {

        finalizeCal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor results = myDB.getAllCalculatedData();


                        //check if row count of result is equals to zero , then make a toast
                        if (results.getCount() == 0) {
                            Toast.makeText(UsageMultiple.this, "No Records", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        //declare double and assign 0 to it.
                        Double final_Result = Double.valueOf(0);

                        //Declare String-buffer
                        StringBuffer buffer = new StringBuffer();
                        while (results.moveToNext()) {

                            // get 4th element from results parse that into double and divide it by 1000 , then assign to Result
                            Double Result = Double.parseDouble(results.getString(3)) / 1000;

                            // Continuously adding result until all records are over
                            final_Result += Result;


                            buffer.append("Name : " + results.getString(1) + "\n");
                            buffer.append("Usage Time : " + results.getString(2) + "\n");
                            buffer.append("Usage  : " + Result + "Kwh" + "\n\n");

                        }
                        buffer.append("\n\n" + "Total Usage : " + final_Result + "Kwh");
                        showMessage("---- Total Usage ----", buffer.toString());
                    }
                });
    }


    // method for showMessage that using AlertDialog
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
