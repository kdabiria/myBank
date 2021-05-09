package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowInfo extends AppCompatActivity {

    private final static String TAG = "ShowInfo";

    private ListView listView;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        listView = (ListView) findViewById(R.id.myInfo_listView);
        ArrayList<String> mData = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();

        dbHelper = new DatabaseHelper(this);

        mData.add(dbHelper.getFirstName(bundle.getString("username")));
        mData.add(dbHelper.getLasttName(bundle.getString("username")));
        mData.add(dbHelper.getEmail(bundle.getString("username")));

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, mData);
        listView.setAdapter(adapter);

    }
}