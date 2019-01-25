package com.example.tobeisun.bayo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.Date;


public class aboutme extends Fragment {
    private static final String tag ="aboutme";
    Button save;
    Button Edit;
    EditText details, name, number;
    TextView displaydetails, displayname, displaynumber;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor meditor;



    public aboutme() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_aboutme, container, false);
        save = (Button) v.findViewById(R.id.buttonSaveProfile);
        Edit = (Button) v.findViewById(R.id.buttonEditprofile);
        details = (EditText) v.findViewById(R.id.editTextdetails);
        name = (EditText) v.findViewById(R.id.editTextName);
        number = (EditText) v.findViewById(R.id.editTextNumber);
        displayname= (TextView) v.findViewById(R.id.textViewdisplayname);
        displaynumber= (TextView) v.findViewById(R.id.textViewdisplaynumber);
        displaydetails = (TextView) v.findViewById(R.id.textViewdisplaydetails);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());


        save.setVisibility(View.INVISIBLE);
        Edit.setVisibility(View.VISIBLE);

        //to edit the "database"
        meditor= sharedPreferences.edit();
        checksharedpreferences();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


     save.setVisibility(View.INVISIBLE);
     Edit.setVisibility(View.VISIBLE);
details.setVisibility(View.INVISIBLE);
name.setVisibility(View.INVISIBLE);
number.setVisibility(View.INVISIBLE);
                //detailsshowinText= details.getText().toString();
                String song = details.getText().toString();
                meditor.putString(getString(R.string.song),song);
                meditor.commit();


                String storename= name.getText().toString();
                meditor.putString(getString(R.string.storename),storename);
                meditor.commit();

                String storenumber = number.getText().toString();
                meditor.putString(getString(R.string.storenumber), storenumber);










// kind of like declaring the database.. it is not really  database because it is used for private information stored on phones






                    // dataa.child(getIntent().getStringExtra("EdiTtEXTvALUE"));


                 /*   if(detailsshowinText != null ) {
                        SaveCustomerProfile saveCustomerProfile = new SaveCustomerProfile(detailsshowinText);

                        //generate key for first insert,any subsequent inserts will update the former one
                        if (key.isEmpty()) key = dataa.child("").push().getKey();
                        dataa.child("SaveCustomerProfile").child(key).setValue(saveCustomerProfile);


                        Toast.makeText(getContext(), "latitude saved", Toast.LENGTH_LONG).show();
                        Log.d("details are ", "" + saveCustomerProfile);



                } */

                //startActivity(new Intent(getContext(), displayprofiletest.class));




            }
        });


        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


Edit.setVisibility(View.INVISIBLE);
save.setVisibility(View.VISIBLE);

displayname.setVisibility(View.INVISIBLE);
displaynumber.setVisibility(View.INVISIBLE);
displaydetails.setVisibility(View.INVISIBLE);
            }
        });

        return v;



    }


private void checksharedpreferences()
{              //the string value is gotten from "strings.xml" in values
    String song = sharedPreferences.getString(getString(R.string.song), "");

    String storename = sharedPreferences.getString(getString(R.string.storename), " ");
    String storenumber = sharedPreferences.getString(getString(R.string.storenumber), " ");



    displaydetails.setText(song);
    displaynumber.setText(storenumber);
    displayname.setText(storename);
}






}


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

