package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Admin extends AppCompatActivity {

    private static final String TAG = "Admin";
    private Button viewDatabase;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        logout = (Button) findViewById(R.id.admin_logout_id);
        viewDatabase = (Button) findViewById(R.id.viewDatabase_id);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        viewDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked viewDatabase btn!");
                startActivity(new Intent(getApplicationContext(), DatabaseList.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Admin.this, "Cannot go back!", Toast.LENGTH_SHORT).show();
    }
}