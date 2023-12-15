package com.example.energycalc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddEQinfo extends AppCompatActivity {

    //variables creation
    DatabaseHelper myDB;
    EditText getName, getWatt;
    Button Insert, dbTableClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_eqinfo);

        //assign reference for declared variables
        getName = findViewById(R.id.NameEQ);
        getWatt = findViewById(R.id.eqWatt);
        Insert = findViewById(R.id.SaveEqInfo);
        dbTableClear = findViewById(R.id.cleardb);

        //data base variable creation
        myDB = new DatabaseHelper(this);

        //button variable creation and assign reference for it
        final Button backButton = (Button) findViewById(R.id.backToUsageCal);


        //set on click lister and action for back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // call for declared methods
        addData();
        clearDB();

    }




    //method declaration
    public void addData() {

        Insert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            // declare String variables and assign reference for them
                            String eqName = getName.getText().toString();
                            String eqWatt = getWatt.getText().toString();

                            //global variable
                            String userid = AppGlobal.username;


                            //check whether input field are empty and if they are empty it will make toast message
                            if (eqName.isEmpty() || eqWatt.isEmpty()) {
                                Toast.makeText(AddEQinfo.this, "All inputs Required", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                if (Integer.parseInt(eqWatt) == 0) //check if eqWatt input is equals to 0 and if it is then make toast
                                    {
                                    Toast.makeText(AddEQinfo.this, "Wattage must be grater than 0", Toast.LENGTH_SHORT).show();
                                    }

                            else {
                                        //call insertData function and get boolean output from method and assign it to boolean variable
                                    boolean isInserted = myDB.insertData(eqName, eqWatt, userid);

                                    //check if boolean is true and if it is then related toast and other actions
                                    if (isInserted == true) {

                                        Toast.makeText(AddEQinfo.this, "Data Added successfully", Toast.LENGTH_SHORT).show();
                                        getName.setText("");
                                        getWatt.setText("");

                                    //action for if boolean is false.
                                    } else
                                        Toast.makeText(AddEQinfo.this, "Data Add operation Unsuccessful", Toast.LENGTH_SHORT).show();

                                }
                                }
                            }
                        //catch if there is any exception and if it is there make a toast
                        catch (Exception e)
                        {
                            Toast.makeText(AddEQinfo.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void clearDB() {
        dbTableClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    try{
                        // read from database and get number of records in table
                        int numberOfRecords = myDB.getRowCountEQTable(AppGlobal.username);

                        // check if number of records is equals to 0, then show toast as according
                        if (numberOfRecords == 0){

                            Toast.makeText(AddEQinfo.this, "Nothing to Delete", Toast.LENGTH_SHORT).show();
                        }
                        // if number of records is positive number, then dialog box will popup and confirm action
                        else {

                            AlertDialog.Builder builder = new AlertDialog.Builder(AddEQinfo.this);

                            // Set the message show for the Alert time
                            builder.setMessage("You are going to delete " + numberOfRecords + " record/s from table in database. Are you sure to do this, and this action cannot be UNDO?");

                            // Set Alert Title
                            builder.setTitle("Warning !");

                            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                            builder.setCancelable(false);

                            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                                // When the user click yes button then table will clear

                                myDB.clearTableEqinfo(AppGlobal.username);

                                Toast.makeText(AddEQinfo.this,  String.valueOf(numberOfRecords) + " record/s have been deleted", Toast.LENGTH_SHORT).show();

                            });

                            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                                // If user click no then dialog box is canceled.
                                dialog.cancel();
                            });

                            // Create the Alert dialog
                            AlertDialog alertDialog = builder.create();
                            // Show the Alert Dialog box
                            alertDialog.show();

                        }
                    }

                    //catch if there is any exception and if it is there make a toast
                    catch (Exception e)
                    {
                        Toast.makeText(AddEQinfo.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show(); Toast.makeText(AddEQinfo.this, "Data Add operation Unsuccessful", Toast.LENGTH_SHORT).show();
                    }

            }
        });

    }

}