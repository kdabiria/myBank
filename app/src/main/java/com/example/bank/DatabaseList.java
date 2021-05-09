 package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

 public class DatabaseList extends AppCompatActivity {

    private static final String TAG = "DatabaseList";
    private ListView mListView;
    private TextView empty;
    private DatabaseHelper dbHelper;
    private DatabaseListAdapter myAdapter;
    private List<User> listData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_list);
        
        mListView = (ListView) findViewById(R.id.listView);
        empty = (TextView) findViewById(R.id.empty_id);
        dbHelper = new DatabaseHelper(this);
        
        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "Dispalying data in the ListView");

        Cursor data = dbHelper.getAllData();
//        ArrayList<String> listData = new ArrayList<>();

        listData = new ArrayList<>();

        while (data.moveToNext()) {
            listData.add(new User(data.getString(0),data.getString(1),data.getString(2),data.getString(3),data.getString(4), data.getString(5), data.getString(6)));
//            listData.add(data.getString(0));
//            listData.add(data.getString(1));
//            listData.add(data.getString(2));
//            listData.add(data.getString(3));
//            listData.add(data.getString(4));
//            listData.add(data.getString(5));
        }

        listData.remove(0);

        Log.d(TAG, "checking data " + listData.size());
//        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
//        mListView.setAdapter(adapter);
        if (!listData.isEmpty()) {
            myAdapter = new DatabaseListAdapter(this, listData);
            mListView.setAdapter(myAdapter);
        }
        else
            mListView.setEmptyView(empty);
    }
}