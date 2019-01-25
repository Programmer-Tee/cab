package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class popupforcustomer extends AppCompatActivity {

    Button back;

    TextView price;
    TextView Thepriceis;
    Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popupforcustomer);

        back =(Button) findViewById(R.id.backbtn);

        price =(TextView) findViewById(R.id.pricetextview);
        Thepriceis=(TextView) findViewById(R.id.thepriceistextview);
test =(Button)findViewById(R.id.btntest);
test.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(popupforcustomer.this, PickAVehicle.class));
    }
});
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(popupforcustomer.this, CustomersMap.class));
    }
});


// this is for the pop up window
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width= dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.6)); //stands for 80% and 60%

        //to get the values passed from the customersmap activity

        /*Intent intent= getIntent();

         String value =intent.getStringExtra("Double_price");
         price.setText("#" +value);
           Log.d("THE PRICE IS", "" +value ); */

        double getValue;



        Bundle extras = getIntent().getExtras();

        // Read the extras data if it's available.
        if (extras != null)
        {
            getValue = extras.getDouble("Double_price");
            price.setText("#" +getValue);

        }

        }



}
