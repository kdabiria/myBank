package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText balance;
    private EditText password;
    private EditText email;
    private Button createAcc;
    DatabaseHelper mDatabaseHelper;
    private boolean check;
    private Float newEntry5;
    boolean balanceCheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstName_id);
        lastName = (EditText) findViewById(R.id.lastName_id);
        username = (EditText) findViewById(R.id.Username_id);
        password = (EditText) findViewById(R.id.reg_password_id);
        balance = (EditText) findViewById(R.id.reg_balance_id);
        email = (EditText) findViewById(R.id.reg_email_id);
        createAcc = (Button) findViewById(R.id.create_acc_id);

        mDatabaseHelper = new DatabaseHelper(this);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = firstName.getText().toString();
                String newEntry2 = lastName.getText().toString();
                String newEntry3 = username.getText().toString();
                boolean checkUsername = isValidUsername(newEntry3);
                Log.d(TAG, "username validation: " + checkUsername);
                String newEntry4 = password.getText().toString();
                boolean checkPass = isValidPassword(newEntry4);
                String newEntry6 = email.getText().toString();
                boolean checkEmail = isValidEmail(newEntry6);
                if(balance.getText().toString().length() != 0)
                    balanceCheck = checkBalance(balance.getText().toString());
                if(balanceCheck)
                    newEntry5 = Float.parseFloat(balance.getText().toString());
                if(newEntry3.length() != 0 && newEntry4.length() != 0 && newEntry.length() != 0 && newEntry2.length() != 0 && newEntry5 != null) {
                    if(checkUsername) {
                        if (checkPass) {
                            if (checkEmail) {
                                if (balanceCheck) {
                                    check = AddData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6);
                                    if (check) {
                                        toastMeesage("Successfully Created Account!");
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                } else
                                    toastMeesage("Balance Invalid Input");
                            } else
                                toastMeesage("Invalid Email");
                        } else
                            toastMeesage("Invalid Password");
                    }
                    else
                        toastMeesage("Invalid Username");
                }
                else
                    toastMeesage("You must fill the required fields!");
            }
        });



    }

    private boolean checkBalance(String balance) {
        if (balance.startsWith("0"))
            return false;
        else if (balance.charAt(0) == '0' && balance.length() >= 3) {
            return balance.charAt(1) == '.' && balance.matches("\\d+(\\.\\d{2})?");
        }
        else {
            return balance.matches("^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$");
        }
    }

    public boolean AddData(String newEntry, String newEntry2, String newEntry3, String newEntry4, Float newEntry5, String newEntry6) {
        boolean checkuser = mDatabaseHelper.checkUsername(newEntry3);
        boolean insertData = mDatabaseHelper.addData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6 );

        if(checkuser) {
            toastMeesage("Username already Exist!");
            return false;
        }
        else if(insertData) {

            return true;
        }
        else {
//            toastMeesage("Something went wrong!");
            toastMeesage("You must fill the required fields!");
            return false;
        }

    }


    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String pass) {
        return Pattern.matches("[_\\-\\.0-9a-z]*",pass) && pass.length() >= 1 && pass.length() <= 127;
    }

    private boolean isValidUsername(String username) {
        return Pattern.matches("[_\\-\\.0-9a-z]*",username) && username.length() >= 1 && username.length() <= 127 ;
    }

    private void toastMeesage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}