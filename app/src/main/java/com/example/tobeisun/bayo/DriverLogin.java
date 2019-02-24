package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.example.tobeisun.bayo.model.Driver;
import com.example.tobeisun.bayo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DriverLogin extends AppCompatActivity {
    EditText Driveremail;
    EditText Driverpassword;
    Button driverlogin;
    Button driverregister;
    TextView driverstatus;
    TextView driverreglink;
    FirebaseAuth auth;
    Intent intent;


    ProgressDialog loadingbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        auth = FirebaseAuth.getInstance();
        loadingbar= new ProgressDialog(this);

        Driveremail = (EditText) findViewById(R.id.driver_email);
        Driverpassword = (EditText) findViewById(R.id.driver_password);
        driverlogin = (Button) findViewById(R.id.driver_loginbtn);
        driverregister = (Button) findViewById(R.id.driver_registerbtn);
        driverstatus = (TextView) findViewById(R.id.driver_status);
        driverreglink = (TextView) findViewById(R.id.driver_reglink);


        driverregister.setVisibility(View.INVISIBLE);
        driverregister.setEnabled(false);


        driverreglink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverlogin.setVisibility(View.INVISIBLE);


                driverstatus.setText("Register");
                driverreglink.setVisibility(View.INVISIBLE);
                driverregister.setVisibility(View.VISIBLE);
                driverregister.setEnabled(true);
            }
        });


        driverregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String email = Driveremail.getText().toString().trim();
                String password = Driverpassword.getText().toString().trim();

                Registerdriver(email,password);


            }
        });


        driverlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Driveremail.getText().toString().trim();
                String password = Driverpassword.getText().toString().trim();

                LoginDriver (email,password);


                //this part is to move the email to the diversmap activity so as to save it alongside the others in firebase database


            }
        });






    }

    private void LoginDriver(final String email, String password) {

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLogin.this,"Please enter email",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLogin.this,"Please enter a valid password",Toast.LENGTH_LONG).show();
        }



        else{

            loadingbar.setTitle("Driver Login");
            loadingbar.setMessage("Please wait  ...");
            loadingbar.show();


            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {

                            if (task.isSuccessful())
                            {

                                getDriverFirebaseKey(email);
                                UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_EMAIL, email);

                                Toast.makeText(DriverLogin.this,"Successful Login",Toast.LENGTH_LONG).show();
                                loadingbar.dismiss();

                            }

                            else
                            {
                                Toast.makeText(DriverLogin.this,"Sign up failed , please try again",Toast.LENGTH_LONG).show();
                                loadingbar.dismiss();
                            }

                        }
                    });


        }


    }

    private  void Registerdriver(final String email, String password) {

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(DriverLogin.this,"Please enter email",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(DriverLogin.this,"Please enter a valid password",Toast.LENGTH_LONG).show();
        }
        if(email.length() !=6 )
        {
            Toast.makeText(DriverLogin.this,"Password must be 6 digits",Toast.LENGTH_LONG).show();
        }


        else{

            loadingbar.setTitle("Driver registration");
            loadingbar.setMessage("Please wait while we register your data ...");
            loadingbar.show();


            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(DriverLogin.this,"Succeful Signup",Toast.LENGTH_LONG).show();
                                loadingbar.dismiss();

                                if(!emailAlreadyExists(email)) {
                                    saveDriverToFirebase(email, task.getResult().getUser().getDisplayName());
                                }else{
                                    Toast.makeText(DriverLogin.this, "driver email already exists in db", Toast.LENGTH_SHORT).show();
                                }
                                UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_EMAIL, email);


                                Intent x= new Intent(DriverLogin.this,CustomersMap.class);
                                startActivity(x);

                            }

                            else
                            {
                                Toast.makeText(DriverLogin.this,"Sign up failed , please try again later",Toast.LENGTH_LONG).show();
                                loadingbar.dismiss();
                            }

                        }
                    });


        }
    }

    private void saveDriverToFirebase(String email, String username){

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("Driver");

        String key = ref.child("").push().getKey();
        UtilsClass.updateSharedPref(this, Constants.USER_FB_KEY, key);
        Driver driver = new Driver(email, username);
        ref.child(key).setValue(driver);

    }

    private void getDriverFirebaseKey(String email){
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("Driver");
        Query query = ref.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.exists()){
                        Driver user = child.getValue(Driver.class);
                        if(user != null){
                            String key = child.getKey();
                            UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_FB_KEY, key);
                            UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_NAME, user.getName());
                            UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_PHONE_NUMBER, user.getPhoneNumber());
                            UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_EMAIL, user.getEmail());
                            UtilsClass.updateSharedPref(DriverLogin.this, Constants.DRIVER_PLATE_NUMBER, user.getCarPlateNo());
                            UtilsClass.broadcastDriverDetails(DriverLogin.this);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean exists = false;
    private boolean emailAlreadyExists(final String email){

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("Driver");
        Query query = ref.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                exists = false;
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.child("email").getValue(String.class).equals(email)){
                        exists = true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return exists;
    }

}


