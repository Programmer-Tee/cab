package com.example.tobeisun.bayo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PickAVehicle extends AppCompatActivity {

    //To receive broadcast that ride has been ordered
    private BroadcastReceiver onRideOrdered = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            Toast.makeText(context, "Ride Ordered", Toast.LENGTH_SHORT).show();
            finish();
        }
    };

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


        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickAVehicle.this, SetTimeandDate.class));
            }
        });


        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(PickAVehicle.this,CustomerDestinationMap.class));
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(onRideOrdered,
                new IntentFilter(Constants.BROADCAST_RIDE_ORDERED));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(onRideOrdered);
    }
}
