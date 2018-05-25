package com.example.tobeisun.bayo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomerLogin extends AppCompatActivity {

    EditText custemail;
    EditText custpassword ;
    Button custlogin ;
    Button custregister;
    TextView custstatus;
    TextView custreglink ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

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


    }
}
