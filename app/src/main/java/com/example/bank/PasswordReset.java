package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PasswordReset extends AppCompatActivity {

    private EditText username;
    private Button reset;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        username = (EditText) findViewById(R.id.username_rest_id);
        reset = (Button) findViewById(R.id.rest_btn_id);

        dbHelper = new DatabaseHelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                Boolean chekuser = dbHelper.checkUsername(user);

                if(chekuser) {
                    Intent intent = new Intent(getApplicationContext(), Reset.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                }
                else
                    Toast.makeText(PasswordReset.this, "User does not exists", Toast.LENGTH_SHORT).show();

            }
        });


    }
}