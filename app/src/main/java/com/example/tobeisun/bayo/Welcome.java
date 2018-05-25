package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        Button welcomedriver ;
        Button welcomecustomer ;



        welcomedriver=(Button) findViewById(R.id.welcome_driver);
        welcomecustomer=(Button) findViewById(R.id.welcome_customer);


        welcomedriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logindriver = new Intent(Welcome.this,DriverLogin.class);
                startActivity(logindriver);
            }
        });





        welcomecustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customerdriver = new Intent(Welcome.this,CustomerLogin.class);

                startActivity(customerdriver);
            }
        });
    }
}
