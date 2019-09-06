package com.example.palmfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {
    private EditText inputName;
    private EditText inputEmail;
    private EditText inputMsg;
    private String email;
    private String name;
    private String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        inputName = findViewById(R.id.contactName);
        inputEmail = findViewById(R.id.contactEmail);
        inputMsg = findViewById(R.id.contactMessage);
        ImageButton startBtn = findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    }
    protected void sendEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        name = inputName.getText().toString().trim();
        email = inputEmail.getText().toString().trim();
        msg = inputMsg.getText().toString().trim();
        String message = "Name: " + name + "\nEmail: " + email + "\nMessage: " + msg;
        String[] TO = {"ritika.thakur2016@vitstudent.ac.in"};
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "PalmFarm Message");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Toast.makeText(ContactUs.this, "Sending...", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ContactUs.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                ContactUs.this,
                HomeScreen.class);
        startActivity(intent);
        finish();
    }
}