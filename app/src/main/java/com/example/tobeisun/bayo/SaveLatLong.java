package com.example.tobeisun.bayo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

public class SaveLatLong   {
// if it does not have setters and getters it should be public else it  should be private


   public double latitude;
    public double longitude;
    public String placeName;
    public String email;
public String datee;

    public SaveLatLong(){

    }


    public SaveLatLong(double latitude,double longitude,String placeName, String email,String datee){

        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.email=email;
         this.datee=datee;

    }




}
