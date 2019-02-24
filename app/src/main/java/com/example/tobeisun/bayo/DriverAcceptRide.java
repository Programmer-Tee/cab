package com.example.tobeisun.bayo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tobeisun.bayo.model.User;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DriverAcceptRide extends AppCompatActivity {


    TextView DisplayRiderDetails;
    TextView DisplayHobbies;
    TextView  DisplayUsername ;
    TextView  DisplayPhoneNumber;
    TextView DisplayLat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_accept_ride);


        DisplayRiderDetails = (TextView) findViewById(R.id.textviewDetailsofRider);
        DisplayHobbies = (TextView) findViewById(R.id.textviewDisplayHobbies);
        DisplayPhoneNumber= (TextView) findViewById(R.id.textviewDisplayPhoneNumber);
        DisplayUsername= (TextView) findViewById(R.id.textviewDisplayUsername);
      DisplayLat= (TextView) findViewById(R.id.textviewDisplayLatitude);

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        //Query allPost = databaseReference.orderByChild("User").equalTo("email");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if (postSnapshot.hasChild("hobbies") ) {

                        // The class User has the shared preference values also stored in firebase
                        User details = postSnapshot.getValue(User.class);


                        String hobbies = details.getHobbies();
                        String name = details.getUsername();
                        String Phonenumber = details.getPhoneNumber();
                        Double Latitude = details.getlatitude();

                        DisplayHobbies.setText(hobbies);
                        DisplayPhoneNumber.setText(Phonenumber);
                        DisplayUsername.setText(name);
                        DisplayLat.setText(Latitude.toString());

                    }
                    }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}