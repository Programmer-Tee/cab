package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverAcceptOrRejectRide extends AppCompatActivity {

    Button Accept;
    Button Reject;
    TextView AcceptorReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accept_or_reject_ride);

        Accept = (Button) findViewById(R.id.AcceptRidebtn);
        Reject = (Button) findViewById(R.id.RejectRidebtn);
        AcceptorReject = (TextView) findViewById(R.id.textviewAcceptOrReject);


        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DriverAcceptOrRejectRide.this, DriverAcceptRide.class));

            }
        });

    }}