package com.example.tobeisun.bayo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PickAVehicle extends AppCompatActivity {

    TextView infobus ;
    TextView infocar;
    Button bus ;
    Button car ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_avehicle);
        infobus= (TextView) findViewById(R.id.textViewinfobus);
        infocar=(TextView) findViewById(R.id.textViewinfocar);
        bus =(Button) findViewById(R.id.btnbus);
        car =(Button)findViewById(R.id.btncar);

    }
}
