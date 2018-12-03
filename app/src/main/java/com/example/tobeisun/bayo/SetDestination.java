package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetDestination extends AppCompatActivity {

    Button setdestination;
    Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_destination);

        setdestination= (Button)findViewById(R.id.btnsetdestination);
        no =(Button)findViewById(R.id.btnno);



        setdestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SetDestination.this, CustomerDestinationMap.class));
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SetDestination.this, CustomersMap.class));
            }
        });


    }


}
