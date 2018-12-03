package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class FinalFinal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_final);


      // String locationlat = getIntent().getStringExtra("getlat");

        Bundle extras = getIntent().getExtras();

        if (extras !=null) {

            Intent receiveIntent = this.getIntent();
            double e1 = receiveIntent.getDoubleExtra("doubleValuelat", 1);
            double e2 = receiveIntent.getDoubleExtra("doubleValuelong", 1);

            Toast.makeText(FinalFinal.this, "the latitude2 saved is" + e1, Toast.LENGTH_SHORT).show();
            Toast.makeText(FinalFinal.this, "the longitude2 saved is" + e2, Toast.LENGTH_SHORT).show();


            double value2 = extras.getDouble("LATITUDE_ID");
            double valuelong2 = extras.getDouble("LONGITUDE_ID");
            //The key argument here must match that used in the other activity
            Toast.makeText(FinalFinal.this, "the latitude1 saved is" + value2, Toast.LENGTH_SHORT).show();
            Toast.makeText(FinalFinal.this, "the longitude1 saved is" + valuelong2, Toast.LENGTH_SHORT).show();




        }


    }}

