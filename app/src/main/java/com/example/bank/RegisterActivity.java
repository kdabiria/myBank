package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                String newEntry4 = password.getText().toString();
                if(balance.getText().toString().matches("^\\s*(?=.*[1-9])\\d*(?:\\.\\d{1,2})?\\s*$"))
                    newEntry5 = Float.parseFloat(balance.getText().toString());
                String newEntry6 = null;
                if(email.getText().toString().length() != 0)
                    newEntry6 = email.getText().toString();
                if(newEntry3.length() != 0 && newEntry4.length() != 0 && newEntry.length() != 0 && newEntry2.length() != 0 ) {
                    check = AddData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6);
                    Log.d(TAG, check + "!!!!!");
                    if(check) {
                        toastMeesage("Successfully Created Account!");
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
                else
                    toastMeesage("You must fill the required fields!");
            }
        });



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

    private void toastMeesage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}