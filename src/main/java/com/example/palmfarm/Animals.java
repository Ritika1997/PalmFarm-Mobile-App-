package com.example.palmfarm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import org.w3c.dom.Text;

public class Animals extends AppCompatActivity {
    int cow=0;
    int buffalo=0;
    int pig=0;
    int sheep=0;
    int hen=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(
                Animals.this,
                Farmer.class);
        startActivity(intent);
        finish();
    }

    public void decrement_cow(View view) {
        cow--;
        if (cow < 0) {
            cow=0;
        }
            display_cow(cow);
    }
    public void increment_cow(View view) {
        cow++;
        if(cow>100){
            cow=100;
        }
            display_cow(cow);
    }
    private void display_cow(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_cow);
        quantityTextView.setText("" + number);
    }

    //buffalo
    public void decrement_b(View view) {
        buffalo--;
        if (buffalo < 0) {
            buffalo=0;
        }
            display_b(buffalo);
    }
    public void increment_b(View view) {
        buffalo++;
        if(buffalo>100){
            buffalo=100;
        }
            display_b(buffalo);
    }
    private void display_b(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_buffalo);
        quantityTextView.setText("" + number);
    }

    //pig
    public void decrement_pig(View view) {
        pig--;
        if (pig < 0) {
            pig=0;
        }
            display_pig(pig);
    }
    public void increment_pig(View view) {
        pig++;
        if(pig>100){
            pig=100;
        }
            display_pig(pig);
    }
    private void display_pig(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_pig);
        quantityTextView.setText("" + number);
    }

//hen
    public void decrement_hen(View view) {
        hen--;
        if (hen < 0) {
            hen=0;
        }
        display_hen(hen);
    }
    public void increment_hen(View view) {
        hen++;
        if(hen>100){
            hen=100;
        }
            display_hen(hen);
    }
    private void display_hen(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_hen);
        quantityTextView.setText("" + number);
    }

    //sheep
    public void decrement_sheep(View view) {
        sheep--;
        if (sheep < 0) {
            sheep = 0;
        }
            display_sheep(sheep);
    }
    public void increment_sheep(View view) {
        sheep++;
        if(sheep>100){
            sheep=100;
        }
            display_sheep(sheep);
    }
    private void display_sheep(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_sheep);
        quantityTextView.setText("" + number);
    }
    public void submitAnimals(View view){
        String animals_msg="Cows = "+cow+"\nBuffaloes = "+buffalo+"\nPigs = "+pig+"\nHens = "+hen+"\nSheep = "+sheep;
        display(animals_msg);
    }
    public void display(String message) {
        TextView animals= findViewById(R.id.submit_animal);
        animals.setText(message);
    }
}
