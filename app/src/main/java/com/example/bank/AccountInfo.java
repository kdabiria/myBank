package com.example.bank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AccountInfo extends AppCompatActivity {

    private final static String TAG = "AccountInfo";
    private TextView balance;
    private Button deposit;
    private Button withdraw;
    private Button logout;
    DatabaseHelper dbHelper;
    String username;
    String customerID;
    float customerBalance;


    private TextView check;

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
        logout = (Button) findViewById(R.id.logout_button_id);

        check = (TextView) findViewById(R.id.check_id);
        check.setText(username);
        updateBalance();

        deposit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(TAG, "Deposite clicked!!!");
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

    }

    private void updateBalance() {
        Log.d(TAG, "UPDATED THE BALANCE");
        String checkBalance = String.valueOf(dbHelper.getBalance(username));
        float numBalance = Float.parseFloat(checkBalance);
        Log.d(TAG, "FLOAT value  : " + numBalance);
        balance.setText(String.format("%.2f", numBalance));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(AccountInfo.this, "Logout out", Toast.LENGTH_SHORT).show();
    }
}