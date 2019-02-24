package com.example.tobeisun.bayo;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;




public class aboutdriver extends Fragment {

    EditText  drivername, drivernumber, drivercar;
    TextView driverdetails;
   Button SaveDriverdetails;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor meditor;
    public aboutdriver() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_aboutdriver, container, false);
        // Inflate the layout for this fragment

        driverdetails = (TextView) v.findViewById(R.id.textView2);
        drivercar= (EditText) v.findViewById(R.id.editTypeofcarDriver);
        drivername= (EditText) v.findViewById(R.id.editTextNamedriver);
        drivernumber=(EditText) v.findViewById(R.id.editTextNumberdriver);
        SaveDriverdetails= (Button) v.findViewById(R.id.buttonSaveProfiledriver);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());

        //to edit the "database"
        meditor= sharedPreferences.edit();
        checksharedpreferences();




        SaveDriverdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //detailsshowinText= details.getText().toString();
                String Drivername = drivercar.getText().toString();
                meditor.putString(getString(R.string.drivercar),Drivername);
                meditor.commit();


                //detailsshowinText= details.getText().toString();
                String Drivernumber = drivernumber.getText().toString();
                meditor.putString(getString(R.string.drivernumber),Drivernumber);
                meditor.commit();


                //detailsshowinText= details.getText().toString();
                String Drivercar = drivercar.getText().toString();
                meditor.putString(getString(R.string.drivercar),Drivercar);
                meditor.commit();
            }
        });
        return v;
    }





    private void checksharedpreferences() {

        //the string value is gotten from "strings.xml" in values
        String drivernamee = sharedPreferences.getString(getString(R.string.drivername), "");

        String drivernumberr = sharedPreferences.getString(getString(R.string.drivernumber), " ");
        String drivercarr = sharedPreferences.getString(getString(R.string.drivercar), " ");



        drivername.setText(drivernamee);
        drivernumber.setText(drivernumberr);
        drivercar.setText(drivercarr);
    }


}
