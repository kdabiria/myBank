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
    private EditText password;
    private Button createAcc;
    DatabaseHelper mDatabaseHelper;
    private boolean check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstName = (EditText) findViewById(R.id.firstName_id);
        lastName = (EditText) findViewById(R.id.lastName_id);
        username = (EditText) findViewById(R.id.Username_id);
        password = (EditText) findViewById(R.id.reg_password_id);
        createAcc = (Button) findViewById(R.id.create_acc_id);

        mDatabaseHelper = new DatabaseHelper(this);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = firstName.getText().toString();
                String newEntry2 = lastName.getText().toString();
                String newEntry3 = username.getText().toString();
                String newEntry4 = password.getText().toString();
                int newEntry5 = 1000;
                if(username.length() != 0) {
                    check = AddData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5);
                    Log.d(TAG, check + "!!!!!");
                    if(check)
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
                else
                    toastMeesage("You must put something in the text field!");
            }
        });



    }

    public boolean AddData(String newEntry, String newEntry2, String newEntry3, String newEntry4, int newEntry5) {
        boolean insertData = mDatabaseHelper.addData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5 );

        if(insertData) {
            toastMeesage("Successfully Created Account!");
            return true;
        }
        else {
            toastMeesage("Something went wrong!");
            return false;
        }

    }

    private void toastMeesage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}