package com.example.tobeisun.bayo.model;

import android.widget.EditText;

public class User {

    private String username;
    private String email;
    private String phoneNumber;
    private String hobbies;
    public double latitude;
    public double longitude;

    public User(String username, String email, String phoneNumber, String hobbies, String s, double s1, double s2) {
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public User(String username, String email, String phoneNumber, String hobbies, double latitude, double longitude) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hobbies = hobbies;
        this.latitude= latitude;
        this.longitude= longitude;
    }
// when a class is created, a default construstor is added by the class during compilation , when a real constructor is created, the default class goes awaay
//the default class has to be manually addedto remove the error of "user does not define a non argument constructor", Hence the following addition
    public User()
    {

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getlatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Double getlongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hobbies='" + hobbies + '\'' +
                '}';
    }
}
