package com.example.bank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class WithdrawMoney extends Fragment {

    private TextView amount;
    private Button confirm;

    public WithdrawMoney() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_withdraw_money, container, false);

        confirm = (Button) confirm.findViewById(R.id.confirm_withdraw_id);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("WithdrawFrag", "Withdraw click frag worki!!");
            }
        });

        return v;
    }
}