package com.example.palmfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                Settings.this,
                HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
