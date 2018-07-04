package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RequestTaxify extends AppCompatActivity {


    Button request ;
TextView  immediateuse;
TextView lateruse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_taxify);


        request= (Button) findViewById(R.id.btnrequest);
        immediateuse=(TextView) findViewById(R.id.idrequestnow);
        lateruse=(TextView) findViewById(R.id.idrequestlater);





        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RequestTaxify.this, CustomerDestinationMap.class));
            }
        });


        lateruse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RequestTaxify.this, SetTimeandDate.class));
            }
        });

    }
}
