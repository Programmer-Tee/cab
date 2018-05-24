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
                    sleep(3000); // to display screen for 7 seconds

                }

                catch (Exception e)
                {
                    e.printStackTrace();

                }

                finally
                {
                    Intent welcomeintent = new Intent(MainActivity.this, Welcome.class);
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
