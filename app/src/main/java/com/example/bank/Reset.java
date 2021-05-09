package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Reset extends AppCompatActivity {

    private TextView username;
    private EditText pass, repass;
    private Button confirm;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        username = (TextView) findViewById(R.id.username_reset_text_id);
        pass = (EditText) findViewById(R.id.password_rest_id);
        repass = (EditText) findViewById(R.id.repassword_rest_id);
        confirm = (Button) findViewById(R.id.confirm_reset_btn_id);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String password = pass.getText().toString();
                String repassword = repass.getText().toString();



                if(password.equals(repassword)) {
                    Boolean checkPassUpdate = dbHelper.updatePassword(user, password);
                    if(checkPassUpdate) {
                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        Toast.makeText(Reset.this, "Password Updated Successfully", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(Reset.this, "Password Not Updated Successfully", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(Reset.this, "Password Not Matching", Toast.LENGTH_LONG).show();
            }
        });
    }
}