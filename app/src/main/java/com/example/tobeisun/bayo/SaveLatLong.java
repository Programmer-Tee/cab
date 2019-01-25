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
public String gethobby;
public String getstorename;
public String getstorenumber;

    public SaveLatLong(){

    }


    public SaveLatLong(double latitude,double longitude,String placeName, String email,String datee, String gethobby, String getstorename, String getstorenumber){

        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.email=email;
         this.datee=datee;
         this.gethobby= gethobby;
           this.getstorename= getstorename;
           this.getstorenumber= getstorenumber;
    }




}
