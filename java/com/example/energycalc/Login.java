package com.example.energycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity {


    //variable creation
    Button login, resetPW, register;
    EditText getLoginUserName, getLoginPassword;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDB = new DatabaseHelper(this);

        //assign reference for created variable
        login = findViewById(R.id.btnLogin);
        resetPW = findViewById(R.id.btnResetPW);
        register = findViewById(R.id.btnRegister);

        getLoginUserName = findViewById(R.id.editTextLoginUsername);
        getLoginPassword = findViewById(R.id.editTextLoginPassword);


        //creat on click listeners and assign actions for them
        resetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ResetPassword.class);
                startActivity(i);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });

            // create on click listener in login button and call declared method login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }


    // method declaration - login
    public void login() {

        try {

            // declare String variable and assign reference for them
            String loginUsername = getLoginUserName.getText().toString();
            String loginPassword = getLoginPassword.getText().toString();

            //get data from database that equals to username provided by application user
            Cursor results = myDB.getLoginData(loginUsername);

            //check weather if any input is empty? , if it is make a toast message
            if (loginUsername.isEmpty() || loginPassword.isEmpty())
            {
                Toast.makeText(getApplicationContext(), "All input field are required !", Toast.LENGTH_SHORT).show();
            }
            //Check that the row count in the result is 0 and make a toast message.
            if (results.getCount() == 0)
            {
                Toast.makeText(getApplicationContext(), "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
            }

            // read result and assign them to temporary String variables
            while (results.moveToNext()) {

                String temp_userName = results.getString(0);
                String temp_password = results.getString(1);

                // Check if the username and password provided by the user are equal to the username and password stored in the database and action if they are match
                if (Objects.equals(temp_userName, loginUsername) && Objects.equals(temp_password, loginPassword)) {
                    Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                    //assign username to Global variable called username
                    AppGlobal.username = loginUsername;

                    finish();

                    //Redirect user to Welcome page
                    Intent i = new Intent(getApplicationContext(), Home.class);
                    startActivity(i);

                    // action if provided username and password do not matched with the data in the database.
                } else {
                    Toast.makeText(getApplicationContext(), "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
                }
            }
            //check for any exception and toast message accordingly
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit ")
                .setMessage("Do you want to Exit from App ?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user wants to leave - so dismiss the dialog and exit
                        finish();
                        dialog.dismiss();

                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // The user is not sure, so you can exit or just stay
                        dialog.dismiss();
                    }
                }).show();
    }

}