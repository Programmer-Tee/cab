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

public class CustomerLogin extends AppCompatActivity {

    EditText custemail;
    EditText custpassword;
    Button custlogin;
    Button custregister;
    TextView custstatus;
    TextView custreglink;
    FirebaseAuth auth;
    ProgressDialog loadingbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        auth = FirebaseAuth.getInstance();

        loadingbar = new ProgressDialog(this);


        custemail = (EditText) findViewById(R.id.cust_email);
        custpassword = (EditText) findViewById(R.id.cust_password);
        custlogin = (Button) findViewById(R.id.cust_loginbtn);
        custregister = (Button) findViewById(R.id.cust_regbtn);
        custstatus = (TextView) findViewById(R.id.cust_status);
        custreglink = (TextView) findViewById(R.id.register_cust_link);


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

                Registercustomer(email, password);


            }
        });


        custlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = custemail.getText().toString().trim();
                String password = custpassword.getText().toString().trim();

                Logincustomer(email, password);

            }
        });

//        checkIfUserIsLoggedIn();
    }

    private void Registercustomer(final String email, String password)

    {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(CustomerLogin.this, "Please enter email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(CustomerLogin.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
        }
//        if (email.length() != 6) { // commented out this, since it password length you want to check
        if (password.length() != 6) {
            Toast.makeText(CustomerLogin.this, "Password must be 6 digits", Toast.LENGTH_LONG).show();
        } else {

            loadingbar.setTitle("Customer registration");
            loadingbar.setMessage("Please wait while we register your data ...");
            loadingbar.show();


            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        if(!emailAlreadyExists(email)) {
                            saveUserToFirebase(email, task.getResult().getUser().getDisplayName());
                        }else{
                            Toast.makeText(CustomerLogin.this, "User email already exists in db", Toast.LENGTH_SHORT).show();
                        }
                        UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_EMAIL, email);

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


    private void Logincustomer(final String email, final String password) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(CustomerLogin.this, "Please enter email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(CustomerLogin.this, "Please enter a valid password", Toast.LENGTH_LONG).show();
        } else {

            loadingbar.setTitle("Customer Login");
            loadingbar.setMessage("Please wait  ...");
            loadingbar.show();
//            background bg = new background(this);
//            bg.execute(email, password);


            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(CustomerLogin.this, "Successful Login", Toast.LENGTH_LONG).show();
                        loadingbar.dismiss();


                        getUserFirebaseKey(email);
                        UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_EMAIL, email);

//                        Intent x = new Intent(CustomerLogin.this, CustomersMap.class);
                        Intent x = new Intent(CustomerLogin.this, CustomerNavigationDrawer.class);
                        x.putExtra("email", custemail.getText().toString());

                        startActivity(x);
                    } else {
                        Toast.makeText(CustomerLogin.this, "Login failed , please try again ", Toast.LENGTH_LONG).show();
                        loadingbar.dismiss();
                    }


                }
            });


        }


    }

    private void saveUserToFirebase(String email, String username){

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("User");

        String key = ref.child("").push().getKey();
        UtilsClass.updateSharedPref(this, Constants.USER_FB_KEY, key);
        User user = new User(email, username);
        ref.child(key).setValue(user);

    }

    private void getUserFirebaseKey(String email){
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("User");
        Query query = ref.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren()){
                    if(child.exists()){
                        User user = child.getValue(User.class);
                        if(user != null){
                            String key = child.getKey();
                            UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_FB_KEY, key);
                            UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_NAME, user.getUsername());
                            UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_PHONE_NUMBER, user.getPhoneNumber());
                            UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_EMAIL, user.getEmail());
                            UtilsClass.updateSharedPref(CustomerLogin.this, Constants.USER_HOBBY, user.getHobbies());
                            UtilsClass.broadcastUserDetails(CustomerLogin.this);
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

        DatabaseReference ref =FirebaseDatabase.getInstance().getReference().child("User");
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


//    private void checkIfUserIsLoggedIn(){
//        FirebaseUser user = auth.getCurrentUser();
//
//        if(user != null){
//            Intent intent = new Intent(this, CustomersMap.class);
//            intent.putExtra("email", user.getEmail());
//        }
//    }

}

