package com.example.tobeisun.bayo;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SaveLatLong {

    double latitude;
    double longitude;
    String placeName;

    public SaveLatLong(){

    }


    public SaveLatLong(double latitude,double longitude,String placeName){

        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;


    }




}
