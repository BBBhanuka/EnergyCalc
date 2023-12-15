package com.example.energycalc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    Button completeRegistration, backLogin;

    DatabaseHelper myDB;

    EditText getUsername, getPassword, getRePassword, getEmail, getBirthYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        myDB = new DatabaseHelper(this);

        completeRegistration = findViewById(R.id.btnCompleteRegistration);
        backLogin = findViewById(R.id.btnBackLogin);

        getUsername = findViewById(R.id.editTextUserName);
        getPassword = findViewById(R.id.editTextPassword);
        getRePassword = findViewById(R.id.editTextRePassword);
        getEmail = findViewById(R.id.editTextEmail);
        getBirthYear = findViewById(R.id.editTextBirthday);

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                backToLogin();

            }
        });


        registerUser();

    }

public  void backToLogin() {

    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);

    // Set the message show for the Alert time
    builder.setMessage("You are going to leave from registration page. Any of your work will not save. \n Are you sure to do this? ");

    // Set Alert Title
    builder.setTitle("Warning !");

    builder.setCancelable(false);

    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

      finish();

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



    public void registerUser() {

        completeRegistration.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                try {
                    String userName = getUsername.getText().toString();
                    String email = getEmail.getText().toString();
                    int birthYear = Integer.parseInt(getBirthYear.getText().toString());
                    String password = getPassword.getText().toString();
                    String rePassword = getRePassword.getText().toString();


                    //Check inputs empty?

                    if (userName.isEmpty() || email.isEmpty() || String.valueOf(birthYear).isEmpty() || password.isEmpty() || rePassword.isEmpty()) {

                        Toast.makeText(getApplicationContext(), "All input fields are Required !", Toast.LENGTH_LONG).show();
                    } else {

                        boolean isUserNameExits = myDB.checkUsername(userName);

                        if (isUserNameExits == true) {

                            Toast.makeText(getApplicationContext(), "User name is already exits", Toast.LENGTH_LONG).show();
                        } else {

                            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                            boolean isEmailValid = false;
                            boolean isPasswordMatch = false;
                            boolean isBirthYearTrue = false;

                            if (password.equals(rePassword)) {
                                isPasswordMatch = true;
                            } else {

                                Toast.makeText(getApplicationContext(), "Password and Re-Password must be same", Toast.LENGTH_LONG).show();
                            }

                            if (email.matches(emailPattern))
                                isEmailValid = true;

                            else
                                Toast.makeText(getApplicationContext(), "Email is not in valid format", Toast.LENGTH_LONG).show();


                            if(birthYear>= 1950 && birthYear <= 2013)
                                isBirthYearTrue = true;

                            else
                                Toast.makeText(getApplicationContext(), "Birth year may wrong. please check again", Toast.LENGTH_LONG).show();


                            if (isEmailValid && isPasswordMatch && isBirthYearTrue) {

                                boolean isInserted = myDB.insertUserData(userName, email, password, String.valueOf(birthYear));

                                if (isInserted == true) {

                                    Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();

                                    finish();

                                    Intent i = new Intent(getApplicationContext(), Login.class);
                                    startActivity(i);

                                } else
                                    Toast.makeText(getApplicationContext(), "Registration Unsuccessful", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }






}