package com.example.bank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DatabaseListAdapter extends BaseAdapter {

    private final static String TAG = "DatabaseListAdapter";
    private Context mContext;
    private List<User> mData;

    public DatabaseListAdapter(Context mContext, List<User> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View v = View.inflate(mContext, R.layout.database_info_list, null);
        TextView id = (TextView) v.findViewById(R.id.ID_id);
        TextView fname = (TextView) v.findViewById(R.id.admin_fname_id);
        TextView lname = (TextView) v.findViewById(R.id.admin_lname_id);
        TextView username = (TextView) v.findViewById(R.id.admin_username_id);
        TextView balance = (TextView) v.findViewById(R.id.admin_balance_id);
        TextView email = (TextView) v.findViewById(R.id.admin_email_id);

        //set Text
        id.setText("ID: " + mData.get(position).getId());
        fname.setText("First Name: " + mData.get(position).getFname());
        lname.setText("Last Name: " + mData.get(position).getLname());
        username.setText("Username: " + mData.get(position).getUsername());
        balance.setText("Balance: " + String.format("%.2f", Float.parseFloat(mData.get(position).getBalance())));
        Log.d(TAG, "EMIAL is: " + mData.get(position).getEmail());
        if(mData.get(position).getEmail() != null)
            email.setText("Email: " + mData.get(position).getEmail());
        else
            email.setText("Email: None");

        return v;
    }
}
