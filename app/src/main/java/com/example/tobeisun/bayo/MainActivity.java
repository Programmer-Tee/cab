package com.example.tobeisun.bayo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Thread thread = new Thread ()
        {
            @Override
            public void run() {
                try
                {
                    sleep(2000); // to display screen for 2 seconds

                }

                catch (Exception e)
                {
                    e.printStackTrace();

                }

                finally
                {
                    Intent welcomeintent = new Intent(MainActivity.this, Welcome.class); //this happens after the try block is executed
                            startActivity(welcomeintent);
                }



            }
        } ;
  thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
