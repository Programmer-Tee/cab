package com.example.tobeisun.bayo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DriverLogin extends AppCompatActivity {
   EditText Driveremail;
    EditText Driverpassword ;
    Button driverlogin ;
    Button driverregister;
    TextView driverstatus;
    TextView driverreglink ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);

        Driveremail= (EditText) findViewById(R.id.driver_email);
        Driverpassword=(EditText) findViewById(R.id.driver_password);
        driverlogin=(Button) findViewById(R.id.driver_loginbtn);
        driverregister=(Button) findViewById(R.id.driver_registerbtn);
        driverstatus=(TextView) findViewById(R.id.driver_status);
        driverreglink=(TextView) findViewById(R.id.driver_reglink);


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

    }
}
