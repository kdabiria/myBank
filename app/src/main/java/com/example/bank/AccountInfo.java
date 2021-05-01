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

public class AccountInfo extends AppCompatActivity {

    private TextView balance;
    private Button deposit;
    private Button withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);


        balance = (TextView) findViewById(R.id.balance_id);
        deposit = (Button) findViewById(R.id.deposit_id);
        withdraw = (Button) findViewById(R.id.withdraw_id);

        deposit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("AccountInfo", "Deposite clicked!!!");
                startActivity(new Intent(getApplicationContext(), DepositMoney.class));
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.v("AccountInfo", "withdraw clicked!!!");
                startActivity(new Intent(getApplicationContext(), WithdrawMoney.class));
            }
        });

    }
}