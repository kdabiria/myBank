package com.example.bank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/***
 *AccountInfo class will display user information
 */

public class AccountInfo extends AppCompatActivity {

    private final static String TAG = "AccountInfo";
    private TextView balance;
    private Button deposit;
    private Button withdraw;
    private Button logout;
    private Button myInfo;
    DatabaseHelper dbHelper;
    String username;
    String customerID;
    float customerBalance;


    private TextView name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        customerID = bundle.getString("customerID");
        customerBalance = Float.parseFloat(bundle.getString("customerBalance"));

        dbHelper = new DatabaseHelper(this);

        balance = (TextView) findViewById(R.id.balance_id);
        deposit = (Button) findViewById(R.id.deposit_id);
        withdraw = (Button) findViewById(R.id.withdraw_id);
        myInfo = (Button) findViewById(R.id.myInfo_id);
        logout = (Button) findViewById(R.id.logout_button_id);

        name = (TextView) findViewById(R.id.fnam_id);
        name.setText("Welcome " + dbHelper.getFirstName(username));
        updateBalance();

        //Setting actions on Buttons

        deposit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DepositMoney.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", username);
                bundle1.putString("customerID",customerID);
                bundle1.putString("customerBalance", bundle.getString("customerBalance"));
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(TAG, "withdraw clicked!!!");
                Intent intent = new Intent(getApplicationContext(), WithdrawMoney.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", username);
                bundle1.putString("customerID",customerID);
                bundle1.putString("customerBalance", bundle.getString("customerBalance"));
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "withdraw clicked!!!");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "CLICLED myInof");
                Intent intent = new Intent(getApplicationContext(), ShowInfo.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("username", username);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

    }

    /***
     * update and format balance textfield
     */
    private void updateBalance() {
        Log.d(TAG, "UPDATED THE BALANCE");
        String checkBalance = String.valueOf(dbHelper.getBalance(username));
        float numBalance = Float.parseFloat(checkBalance);
        Log.d(TAG, "FLOAT value  : " + numBalance);
        balance.setText("Balance:" + String.format("%.2f", numBalance));
    }

    /***
     * Overrideing back button
     */
    @Override
    public void onBackPressed() {
        Toast.makeText(AccountInfo.this, "Cannot go back", Toast.LENGTH_SHORT).show();
    }

}