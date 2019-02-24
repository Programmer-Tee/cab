package com.example.tobeisun.bayo;

public class SaveLatLongDestination {


    public double latitude;
    public double longitude;
    public String placeName;
    public String email;
    public String datee;

    public SaveLatLongDestination(){

    }


    public SaveLatLongDestination(double latitude,double longitude,String placeName, String email,String datee){

        this.latitude = latitude;
        this.longitude = longitude;
        this.placeName = placeName;
        this.email=email;
        this.datee=datee;

    }

    @Override
    public String toString() {
        return "SaveLatLongDestination{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", placeName='" + placeName + '\'' +
                ", email='" + email + '\'' +
                ", datee='" + datee + '\'' +
                '}';
    }
}
