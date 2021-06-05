package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


public class PasswordReset extends AppCompatActivity {
    private final static String TAG = "PasswordRest";
    private EditText username;
    private Button reset;
    private Random random = new Random();
    private String user;
    String fouDigitRandomNumber;
    DatabaseHelper dbHelper;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        myDialog = new Dialog(this);
        username = (EditText) findViewById(R.id.username_rest_id);
        reset = (Button) findViewById(R.id.rest_btn_id);

        dbHelper = new DatabaseHelper(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                //chcking to see if the user exists
                Boolean chekuser = dbHelper.checkUsername(user);

                if(chekuser) {
                    sendCode("bankservicenoreply@gmail.com", dbHelper.getEmail(user));
                    showPopup(v);
//                    Intent intent = new Intent(getApplicationContext(), Reset.class);
//                    intent.putExtra("username", user);
//                    startActivity(intent);
                }
                else
                    Toast.makeText(PasswordReset.this, "User does not exists", Toast.LENGTH_SHORT).show();

            }
        });
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    @SuppressLint("DefaultLocale")
    private void sendCode(String username, String email) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, "Uciclass2020");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Code for reseting password");
            fouDigitRandomNumber = String.format("%04d", random.nextInt(10000));
            message.setText("You requested to change the password \n\nThe code is \n\n" + fouDigitRandomNumber);
            Transport.send(message);
            Toast.makeText(getApplicationContext(), "email has been sent", Toast.LENGTH_LONG).show();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private void showPopup(View v) {
        EditText code;
        Button btnCode;

        myDialog.setContentView(R.layout.popup);
        code = (EditText) myDialog.findViewById(R.id.code_id);
        btnCode = (Button) myDialog.findViewById(R.id.codeButton_id);

        btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.getText().toString().matches(fouDigitRandomNumber)){
                    Log.d(TAG, "code is the same");
                    Intent intent = new Intent(getApplicationContext(), Reset.class);
                    intent.putExtra("username", user);
                    startActivity(intent);
                }
                else
                    Log.d(TAG, "code is NOT the same");
            }
        });

        myDialog.show();
    }
}