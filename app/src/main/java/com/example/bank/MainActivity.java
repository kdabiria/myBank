package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView register;
    private TextView forgot;
    DatabaseHelper dbhelper;
    private String customerID;
    private String username_db;
    private String customerBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.usrename_id);
        password = (EditText) findViewById(R.id.password_id);

        loginButton = (Button) findViewById(R.id.login_id);
        register = (TextView) findViewById(R.id.reg_id);
        forgot = (TextView) findViewById(R.id.forgot_id);

        dbhelper = new DatabaseHelper(this);

        //setting actions on buttons

        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("Main", "reg clicked!!!!!!" );
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked forgot!");
                startActivity(new Intent(getApplicationContext(), PasswordReset.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Main", "Login botton working!!!!!!" );
                //checking if username password vaild
                boolean check = loginCheck();
                if(check && !username_db.equals("admin")) {
//                    Account acc = new Account(Float.parseFloat(customerBalance) ,0, customerID);
                    Intent intent = new Intent(getApplicationContext(), AccountInfo.class);

                    //create bundle to pass info to next activity
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username.getText().toString());
                    bundle.putString("customerID", customerID);
                    bundle.putString("customerBalance", customerBalance);
                    intent.putExtras(bundle);
                    username.setText("");
                    password.setText("");
                    startActivity(intent);
                }
                else if (check && username_db.equals("admin")){
                    if (loginCheck())
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                }
            }
        });
    }

    /***
     * Checking if username and password are valid
     * @return boolean
     */
    private boolean loginCheck() {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        if (user.equals("") || pass.equals("")) {
            Toast.makeText(MainActivity.this, "Fields can't be null", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                boolean checkUse = dbhelper.checkUsername(user);
                if (checkUse)
                    username_db = dbhelper.getUsername(user);
                boolean checkPass = dbhelper.checkPassword(dbhelper.hash(pass));
                customerID = dbhelper.getID(user);
                customerBalance = dbhelper.getBalance(user);

                if (checkPass && checkUse) {
                    Toast.makeText(MainActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                    return true;
                } else {
                    Toast.makeText(MainActivity.this, "failed to login, try again", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /***
     * overriding back button
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Cannot go back!", Toast.LENGTH_SHORT).show();
    }
}