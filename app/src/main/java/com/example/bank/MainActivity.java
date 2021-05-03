package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView register;
    DatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.usrename_id);
        password = (EditText) findViewById(R.id.password_id);

        loginButton = (Button) findViewById(R.id.login_id);
        register = (TextView) findViewById(R.id.reg_id);

        dbhelper = new DatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("Main", "reg clicked!!!!!!" );
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Main", "Login botton working!!!!!!" );
                boolean check = loginCheck();

                if(check)
                    startActivity(new Intent(getApplicationContext(), AccountInfo.class));
            }
        });
    }

    private boolean loginCheck() {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.equals("") || pass.equals("")) {
            Toast.makeText(MainActivity.this, "Fields can't be null", Toast.LENGTH_SHORT).show();
        }
        else {
            boolean checkUse = dbhelper.checkUsername(user);
            boolean checkPass = dbhelper.checkPassword(pass);

            if (checkUse && checkPass) {
                Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                Toast.makeText(MainActivity.this, "failed to login, try again", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }
}