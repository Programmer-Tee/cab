package com.example.tobeisun.bayo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class SetTimeandDate extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
Spinner spinner ;
Spinner spinnergmt ;
Spinner spinnerdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time);

        spinner = (Spinner) findViewById(R.id.spinnertime);
        spinnergmt =(Spinner) findViewById(R.id.spinnergmt);


        //dropdown for the time

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //dropdown for the gmt (am,pm)


        ArrayAdapter<CharSequence> adaptergmt = ArrayAdapter.createFromResource(this, R.array.gmt, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergmt.setAdapter(adaptergmt);
        spinnergmt.setOnItemSelectedListener(this);



        //dropdown for the date

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selected = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),selected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}