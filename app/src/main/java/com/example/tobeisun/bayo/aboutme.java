package com.example.tobeisun.bayo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobeisun.bayo.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class aboutme extends Fragment {
    private static final String tag = "aboutme";

    public static final String TAG_NAME = "nameKey";
    public static final String TAG_PHONENUUMBER= "numberKey";
    public static final String TAG_HOBBIES= "hobbiesKey";


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
//        Edit = (Button) v.findViewById(R.id.buttonEditprofile);
        details = (EditText) v.findViewById(R.id.editTextdetails);
        name = (EditText) v.findViewById(R.id.editTextName);
        number = (EditText) v.findViewById(R.id.editTextNumber);
 //       displayname = (TextView) v.findViewById(R.id.textViewdisplayname);
//        displaynumber = (TextView) v.findViewById(R.id.textViewdisplaynumber);
//        displaydetails = (TextView) v.findViewById(R.id.textViewdisplaydetails);
       sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

//        details.setText(UtilsClass.getSharedPref(getContext(), Constants.USER_HOBBY));
  //      name.setText(UtilsClass.getSharedPref(getContext(), Constants.USER_NAME));
    //    number.setText(UtilsClass.getSharedPref(getContext(), Constants.USER_PHONE_NUMBER));


//        save.setVisibility(View.VISIBLE);
//        Edit.setVisibility(View.VISIBLE);

        //to edit the "database"
    meditor = sharedPreferences.edit();
        checksharedpreferences();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String username = name.getText().toString().trim();
                String email = UtilsClass.getSharedPref(getContext(), Constants.USER_EMAIL);
                String phoneNumber = number.getText().toString().trim();
                String hobbies = details.getText().toString().trim();


               // UtilsClass.updateSharedPref(getContext(), Constants.USER_NAME, username);
                //UtilsClass.updateSharedPref(getContext(), Constants.USER_PHONE_NUMBER, phoneNumber);
                //UtilsClass.updateSharedPref(getContext(), Constants.USER_HOBBY, hobbies);

                //UtilsClass.broadcastUserDetails(getContext());



               // User user = new User(username, email, phoneNumber, hobbies, "","","");
                //saveUserDetailsToFirebase(user);



//                save.setVisibility(View.INVISIBLE);
//                Edit.setVisibility(View.VISIBLE);
//                details.setVisibility(View.INVISIBLE);
//                name.setVisibility(View.INVISIBLE);
//                number.setVisibility(View.INVISIBLE);
//                //detailsshowinText= details.getText().toString();
           String song = details.getText().toString();
meditor.putString(getString(R.string.song), song);
                meditor.commit();
//
//
 String storename = name.getText().toString();
               meditor.putString(getString(R.string.storename), storename);
                meditor.commit();
//
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


//        Edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Edit.setVisibility(View.INVISIBLE);
//                save.setVisibility(View.VISIBLE);
//
////                displayname.setVisibility(View.INVISIBLE);
////                displaynumber.setVisibility(View.INVISIBLE);
////                displaydetails.setVisibility(View.INVISIBLE);
//            }
//        });

        return v;


    }


    private void checksharedpreferences() {              //the string value is gotten from "strings.xml" in values
        String song = sharedPreferences.getString(getString(R.string.song), "");

        String storename = sharedPreferences.getString(getString(R.string.storename), " ");
        String storenumber = sharedPreferences.getString(getString(R.string.storenumber), " ");


        details.setText(song);
      number.setText(storenumber);
        name.setText(storename);
    }


    private void saveUserDetailsToFirebase(User user){
        DatabaseReference ref =FirebaseDatabase.getInstance().getReference();

        String key = UtilsClass.getSharedPref(getContext(), Constants.USER_FB_KEY);
//        Toast.makeText(getContext(), "key: "+key, Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(key)) {
            key = ref.child("").push().getKey();
        }

        ref.child("User").child(key).setValue(user);

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

