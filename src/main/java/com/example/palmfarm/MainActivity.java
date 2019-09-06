package com.example.palmfarm;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import activity.Login;
import helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT =2500;
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(getApplicationContext());
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (session.isLoggedIn()) {
                    Intent homeIntent = new Intent(MainActivity.this, HomeScreen.class);
                    startActivity(homeIntent);
                    finish();
                } else {
                    Intent homeIntent = new Intent(MainActivity.this, Login.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
                },SPLASH_TIME_OUT);
    }
}
