package com.example.tobeisun.bayo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobeisun.bayo.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InputdetailsCustomer extends AppCompatActivity {
    TextView riderdetails;
    EditText ridername, ridernumber;
    Button riderproceed;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor meditor;
    String storedname;
    String storednumber;
String email;
String hobbies;
    Double lat;
Double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputdetails_customer);
        riderdetails = (TextView) findViewById(R.id.textViewdetailsofrider);
        ridername= (EditText) findViewById(R.id.editTextridername);
        ridernumber= (EditText) findViewById(R.id.editTextridernumber);
        riderproceed= (Button) findViewById(R.id.buttonriderproceed);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);


       // riderdetails.setText(UtilsClass.getSharedPref(this, Constants.USER_HOBBY));
       // ridername.setText(UtilsClass.getSharedPref(this, Constants.USER_NAME));
       // ridernumber.setText(UtilsClass.getSharedPref(this, Constants.USER_PHONE_NUMBER));


     meditor= sharedPreferences.edit();
        checksharedpreferences();
        DatabaseReference databaseReference;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        //Query allPost = databaseReference.orderByChild("User").equalTo("email");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                    if (postSnapshot.hasChild("latitude") ) {

                        // The class User has the shared preference values also stored in firebase
                        SaveLatLong details = postSnapshot.getValue(SaveLatLong.class);

                        email = details.getEmail();
                         lat = details.getLatitude();
                         longitude= details.getLongitude();
                         storedname= details.getGetstorename();
                         storednumber= details.getGethobby();
                         hobbies= details.getGetstorenumber();



                    }
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        riderproceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             String storeridername = ridername.getText().toString();
               meditor.putString(getString(R.string.song),storeridername);
                meditor.commit();

//                startActivity(new Intent(InputdetailsCustomer.this, DriversMap.class));





               String storeridernumber= ridernumber.getText().toString();
                meditor.putString(getString(R.string.storename),storeridernumber);
                meditor.commit();

           /*     String s = "";

//                String s = ridername.getText().toString().trim();
//                UtilsClass.updateSharedPref(InputdetailsCustomer.this, Constants.USER_NAME, s);



                s = riderdetails.getText().toString().trim();
                UtilsClass.updateSharedPref(InputdetailsCustomer.this, Constants.USER_HOBBY, s);

                s = ridernumber.getText().toString().trim();
                UtilsClass.updateSharedPref(InputdetailsCustomer.this, Constants.USER_PHONE_NUMBER, s);

                UtilsClass.broadcastUserDetails(InputdetailsCustomer.this);
                UtilsClass.broadcastRideOrdered(InputdetailsCustomer.this);
//                finish();
*/
                Intent intent = new Intent(InputdetailsCustomer.this, Driversnavigationdrawer.class);
                startActivity(intent);
                saveinfoinUser(storeridername,storeridername,storeridernumber);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void checksharedpreferences() {



       String storeridername = sharedPreferences.getString(getString(R.string.storename), " ");
        String storeridernumber = sharedPreferences.getString(getString(R.string.storenumber), "");


   ridername.setText(storeridername);
   // int convert= Integer.parseInt(storeridernumber);

   ridernumber.setText(storeridernumber);

    }
    private void saveUserDetailsToFirebase(User user){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        String key = UtilsClass.getSharedPref(this, Constants.USER_FB_KEY);
//        Toast.makeText(getContext(), "key: "+key, Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(key)) {
            key = ref.child("").push().getKey();
        }

        ref.child("User").child(key).setValue(user);

    }



    String key = "";
    private void saveinfoinUser(String storeridername, String ridernumber, String ridername) {

        // SaveProfiledetails();
        // dataa.child(getIntent().getStringExtra("EdiTtEXTvALUE"));
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference();


            // SaveLatLong saveLatLong = new SaveLatLong(latitude, longitude, placeName, email,new Date().toString(),getsong,getstorenumber,getstorename);
//        String username, String email, String phoneNumber, String hobbies, double latitude, double longitude
//        User useTest = new User("tabitha", "tabs&ctrl@gmail.com",
//                "0809", "singing",0.7845, 0.96547);
            User user = new User( ridername, email, ridernumber, "", lat, longitude);
            //generate key for first insert,any subsequent inserts will update the former one
            if (key.isEmpty()) key = ref.child("").push().getKey();
            ref.child("User").child(key).setValue(user);


           // Toast.makeText(getContext(), "latitude saved", Toast.LENGTH_LONG).show();
            // Log.d("the third latlng is", "" + user);

    }



}
