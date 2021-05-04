package com.example.bank;

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

    private TextView check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");

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
                startActivity(new Intent(getApplicationContext(), DepositMoney.class));
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v(TAG, "withdraw clicked!!!");
                startActivity(new Intent(getApplicationContext(), WithdrawMoney.class));
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
        balance.setText(String.valueOf(dbHelper.getBalance(username)));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(AccountInfo.this, "Logout out", Toast.LENGTH_SHORT).show();
    }
}