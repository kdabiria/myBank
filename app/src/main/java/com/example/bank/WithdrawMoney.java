package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WithdrawMoney extends AppCompatActivity {

    private final static String TAG = "WithdrawMoney";

    private EditText amount;
    private Button confirm;
    DatabaseHelper dbHelper;
    private String oldbalance;
    private String addAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_money);

        addAmount = "0";
        dbHelper = new DatabaseHelper(this);

        amount = (EditText) findViewById(R.id.withdraw_money_id);
        confirm = (Button) findViewById(R.id.withdraw_confirm_id);

        //getting information from previous activity
        Bundle bundle = getIntent().getExtras();


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().length() == 0)
                    Toast.makeText(WithdrawMoney.this, "No entry", Toast.LENGTH_SHORT).show();
                else if (amount.getText().toString().charAt(0) == '0' && amount.getText().toString().length() >= 3) {
                    if (amount.getText().toString().charAt(1) == '.' && amount.getText().toString().matches("\\d+(\\.\\d{2})?")) {
                        oldbalance = dbHelper.getBalance(bundle.getString("username"));
                        Log.d(TAG, "CHECKING the new amount of oldbalance: " + oldbalance);
                        if (check(oldbalance, addAmount)) {
                            Toast.makeText(WithdrawMoney.this, "Withdraw made", Toast.LENGTH_SHORT).show();
                            float newBalance = Float.parseFloat(oldbalance) - Float.parseFloat(amount.getText().toString());
                            updateDB(String.valueOf(newBalance), bundle.getString("customerID"));
                            Intent intent = new Intent(getApplicationContext(), AccountInfo.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(WithdrawMoney.this, "Do not have enough money", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(WithdrawMoney.this, "Input not valid", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(amount.getText().toString().matches("^(?=.*[1-9])\\d*(?:\\.\\d{2})?$")) {
                        oldbalance = dbHelper.getBalance(bundle.getString("username"));
                        Log.d(TAG, "CHECKING the new amount of oldbalance: " + oldbalance);
                        if (check(oldbalance, addAmount)) {
                            Toast.makeText(WithdrawMoney.this, "Withdraw made", Toast.LENGTH_SHORT).show();
                            float newBalance = Float.parseFloat(oldbalance) - Float.parseFloat(amount.getText().toString());
                            updateDB(String.valueOf(newBalance), bundle.getString("customerID"));
                            Intent intent = new Intent(getApplicationContext(), AccountInfo.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(WithdrawMoney.this, "Do not have enough money", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(WithdrawMoney.this, "Input not valid", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    /***
     * Updating balance in databse
     * @param balance
     * @param id
     */
    private void updateDB(String balance, String id) {
        dbHelper.updateBalance(balance, id);
    }

    /***
     * Checking if withdraw is valid. (Having enough money)
     * @param oldbalance
     * @param addAmount
     * @return
     */
    private boolean check(String oldbalance, String addAmount) {
        float newBalance = Float.parseFloat(oldbalance) - Float.parseFloat(amount.getText().toString());
        return newBalance >= 0;
    }
}