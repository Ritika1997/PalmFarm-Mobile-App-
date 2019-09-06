package com.example.palmfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                AboutUs.this,
                HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
