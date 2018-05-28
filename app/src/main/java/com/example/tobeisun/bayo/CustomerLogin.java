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

public class CustomerLogin extends AppCompatActivity {

    EditText custemail;
    EditText custpassword ;
    Button custlogin ;
    Button custregister;
    TextView custstatus;
    TextView custreglink ;
    FirebaseAuth auth;
ProgressDialog loadingbar ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        auth = FirebaseAuth.getInstance();

loadingbar= new ProgressDialog(this);



custemail= (EditText) findViewById(R.id.cust_email) ;
custpassword=(EditText) findViewById(R.id.cust_password);
custlogin=(Button) findViewById(R.id.cust_loginbtn);
custregister=(Button) findViewById(R.id.cust_regbtn) ;
custstatus=(TextView) findViewById(R.id.cust_status);
custreglink=(TextView) findViewById(R.id.register_cust_link) ;


custregister.setVisibility(View.INVISIBLE);
custregister.setEnabled(false);



       custreglink.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               custlogin.setVisibility(View.INVISIBLE);


               custstatus.setText("Register");
               custreglink.setVisibility(View.INVISIBLE);
               custregister.setVisibility(View.VISIBLE);
               custregister.setEnabled(true);







           }
       });



       custregister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               String email = custemail.getText().toString().trim();
               String password = custpassword.getText().toString().trim();

               Registercustomer(email,password);


                       }
                   });







        custlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = custemail.getText().toString().trim();
                String password = custpassword.getText().toString().trim();

                Logincustomer (email,password);

            }
        });
               }

    private void Registercustomer(String email, String password)

        {

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(CustomerLogin.this, "Please enter email", Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(CustomerLogin.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
            }
            if(email.length() !=6 )
            {
                Toast.makeText(CustomerLogin.this,"Password must be 6 digits",Toast.LENGTH_LONG).show();
            }



            else {

                loadingbar.setTitle("Customer registration");
                loadingbar.setMessage("Please wait while we register your data ...");
                loadingbar.show();


                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerLogin.this, "Succeful Signup", Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        } else {
                            Toast.makeText(CustomerLogin.this, "Sign up failed , please try again later", Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        }

                    }
                });

            }
            }


        private void Logincustomer (String email, String password){
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(CustomerLogin.this, "Please enter email", Toast.LENGTH_LONG).show();
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(CustomerLogin.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
            } else {

                loadingbar.setTitle("Customer Login");
                loadingbar.setMessage("Please wait  ...");
                loadingbar.show();


                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(CustomerLogin.this, "Successful Login", Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        } else {
                            Toast.makeText(CustomerLogin.this, "Login failed , please try again ", Toast.LENGTH_LONG).show();
                            loadingbar.dismiss();
                        }

                    }
                });


            }


        }

    }

