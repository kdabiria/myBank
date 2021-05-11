package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DepositMoney extends AppCompatActivity {

    private final static String TAG = "DepositeMoney";
    private EditText amount;
    private Button confirm;
    DatabaseHelper dbHelper;
    private String oldbalance;
    private String addAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money);
        addAmount = "0";
        dbHelper = new DatabaseHelper(this);

        amount = (EditText) findViewById(R.id.deposit_money_id);
        confirm = (Button) findViewById(R.id.deposit_confirm_id);

        Bundle bundle = getIntent().getExtras();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldbalance = dbHelper.getBalance(bundle.getString("username"));
                Log.d(TAG, "CHECKING the new amount of oldbalance: " + oldbalance);

                if (amount.getText().toString().length() == 0 )
                    Toast.makeText(DepositMoney.this, "No entry", Toast.LENGTH_SHORT).show();
                else if (amount.getText().toString().charAt(0) == '0' && amount.getText().toString().length() >= 3) {
                    if(amount.getText().toString().charAt(1) == '.' && amount.getText().toString().matches("\\d+(\\.\\d{2})?")) {
                        Toast.makeText(DepositMoney.this, "Deposit made", Toast.LENGTH_SHORT).show();
                        float newBalance = Float.parseFloat(oldbalance) + Float.parseFloat(amount.getText().toString());
                        Log.d(TAG, "CHECKING the new amount of NEW balance: " + newBalance);
                        updateDB(String.valueOf(newBalance), bundle.getString("customerID"));
                        Intent intent = new Intent(getApplicationContext(), AccountInfo.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(DepositMoney.this, "invalid_input", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(amount.getText().toString().matches("^(?=.*[1-9])\\d*(?:\\.\\d{2})?$")) {
                        Toast.makeText(DepositMoney.this, "Deposit made", Toast.LENGTH_SHORT).show();
                        float newBalance = Float.parseFloat(oldbalance) + Float.parseFloat(amount.getText().toString());
                        Log.d(TAG, "CHECKING the new amount of NEW balance: " + newBalance);
                        updateDB(String.valueOf(newBalance), bundle.getString("customerID"));
                        Intent intent = new Intent(getApplicationContext(), AccountInfo.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(DepositMoney.this, "invalid_input", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    /***
     * Updating the balance for given user
     * @param balance
     * @param id
     */
    private void updateDB(String balance, String id) {
        dbHelper.updateBalance(balance, id);
    }
}