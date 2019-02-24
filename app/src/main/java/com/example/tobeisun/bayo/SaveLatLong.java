package com.example.tobeisun.bayo;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

public class SaveLatLong   {
// if it does not have setters and getters it should be public else it  should be private


   public double latitude;
    public double longitude;
    public String placeName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String email;
public String datee;

    public String getGethobby() {
        return gethobby;
    }

    public void setGethobby(String gethobby) {
        this.gethobby = gethobby;
    }

    public String gethobby;
public String getstorename;

    public String getGetstorename() {
        return getstorename;
    }

    public void setGetstorename(String getstorename) {
        this.getstorename = getstorename;
    }

    public SaveLatLong()
    {

    }

    public String getGetstorenumber() {
        return getstorenumber;
    }

    public void setGetstorenumber(String getstorenumber) {
        this.getstorenumber = getstorenumber;
    }

    public String getstorenumber;

    public SaveLatLong(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public SaveLatLong(double latitude, double longitude, String placeName, String email, String datee, String gethobby, String getstorename, String getstorenumber){

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
