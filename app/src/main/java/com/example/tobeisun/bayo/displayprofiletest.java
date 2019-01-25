package com.example.tobeisun.bayo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class displayprofiletest extends AppCompatActivity {
    private static final String tag ="displayprofiletest";
    TextView showfirstname ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayprofiletest);

        showfirstname= (TextView) findViewById(R.id.editTextseond);
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        //to get the information stored in preferences
        String getsong =sharedPreferences.getString(getString(R.string.song), " default value");
        showfirstname.setText(getsong);
    }
}
