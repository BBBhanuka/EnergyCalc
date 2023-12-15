package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPassword extends AppCompatActivity {

    //variables declaration
    EditText getResetUsername, getResetBirthyear, getResetPassword, getResetRePassword;
    Button resetPw, backToLoginReset;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // assignment of declared variables
        myDB = new DatabaseHelper(this);

        getResetUsername = findViewById(R.id.editTextUserNameReset);
        getResetBirthyear = findViewById(R.id.editTextBirthdayReset);
        getResetPassword = findViewById(R.id.editTextPasswordReset);
        getResetRePassword = findViewById(R.id.editTextRePasswordReset);

        resetPw = findViewById(R.id.btnResetPasswordR);
        backToLoginReset = findViewById(R.id.btnBackLoginReset);

        // create on click listeners and assign actions
        resetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        backToLoginReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    // method declaration for password reset
    public void resetPassword() {


        try {

            //declaration of variables and assignment of references
            String username = getResetUsername.getText().toString();
            String password = getResetPassword.getText().toString();
            String rePassword = getResetRePassword.getText().toString();
            String birthYear = getResetBirthyear.getText().toString();


            // check if inputs are empty and if they are empty , then make toast
            if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || birthYear.isEmpty()) {
                Toast.makeText(getApplicationContext(), "All input fields are Required !", Toast.LENGTH_SHORT).show();

            }

            else {
                    // check password and reentered password are same? and if it is not then error message
                if (!(password.equals(rePassword))) {
                    Toast.makeText(getApplicationContext(), "Password and Re-Password are not same", Toast.LENGTH_SHORT).show();
                }

                else {
                        //get data from database by providing the username provided by user and assign it to result
                        Cursor result = myDB.getUserDataReset(username);

                        //check if count of cursor result is equals to 0, then toast an error
                        if (result.getCount() == 0) {
                            Toast.makeText(getApplicationContext(), "Username may not Exist. Please check again", Toast.LENGTH_SHORT).show();
                        }

                        //if count of result is not equals to zero, then assign result to temporary variables
                        while (result.moveToNext())
                        {

                            String temp_userName = result.getString(0);
                            String temp_birthyear = result.getString(1);

                            //check data gathered from data base and provided by user are same or not and if it is same then the action accordingly
                            if (temp_userName.equals(username) && temp_birthyear.equals(birthYear)) {

                                //calling updatepassword method by providing the username and password that was provided by user and assign result for boolean variable
                                boolean isPasswordUpdated = myDB.updatePassword(username,password);

                                //check if boolean is true and if it is true , then action accordingly.
                                if (isPasswordUpdated) {

                                    Toast.makeText(getApplicationContext(), "Password Updated Successfully. Please Login using new Password", Toast.LENGTH_SHORT).show();

                                    //kill the current activity
                                    finish();

                                    //start new activity
                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    startActivity(i);

                                    //message if unsuccessful action
                                } else {
                                    Toast.makeText(getApplicationContext(), "Password Update Unsuccessful", Toast.LENGTH_SHORT).show();
                                }
                            }

                            //action if user provided birth year does not match with the birth year in the table in database
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Provided Birth year may wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }

        // if any exception raised during process this will run
        catch (Exception e ) {

            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();

        }

    }

}