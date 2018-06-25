package com.example.tobeisun.bayo;

import com.google.firebase.database.IgnoreExtraProperties;

public class SaveLatLong {



    double latitude;
    double longitude;
    String placeName;
    String thenn;

    public SaveLatLong(){

    }


    public SaveLatLong(double latitude,double longitude,String placeName, String thenn){

        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.thenn=thenn;


    }




}
