package com.example.palmfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;


public class Farmer extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer);
        ImageButton startBtn = findViewById(R.id.card_arrow3);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Farmer.this, Animals.class));
                finish();
            }
        });
}
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                Farmer.this,
                HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
