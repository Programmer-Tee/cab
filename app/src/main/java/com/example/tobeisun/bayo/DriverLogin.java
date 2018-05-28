package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class DriverLogin extends AppCompatActivity {
    EditText Driveremail;
    EditText Driverpassword;
    Button driverlogin;
    Button driverregister;
    TextView driverstatus;
    TextView driverreglink;
    FirebaseAuth auth;



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

            }
        });






    }

    private void LoginDriver(String email, String password)
    {

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
                                Toast.makeText(DriverLogin.this,"Successful Login",Toast.LENGTH_LONG).show();
                                loadingbar.dismiss();

                                Intent x= new Intent(DriverLogin.this,DriverMap.class);
                                        startActivity(x);

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

    private  void Registerdriver(String email, String password)
    {

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


                                Intent x= new Intent(DriverLogin.this,DriverMap.class);
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

}


