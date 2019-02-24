package com.example.tobeisun.bayo.model;

import com.google.android.gms.maps.model.LatLng;

public class Driver {
    private String name;
    private String email;
    private String phoneNumber;
    private LatLng driverLocation;
    private String carPlateNo;

    public Driver() {
    }

    public Driver(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Driver(String name, String email, String phoneNumber, LatLng driverLocation, String carPlateNo) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.driverLocation = driverLocation;
        this.carPlateNo = carPlateNo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LatLng getDriverLocation() {
        return driverLocation;
    }

    public void setDriverLocation(LatLng driverLocation) {
        this.driverLocation = driverLocation;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", driverLocation=" + driverLocation +
                ", carPlateNo='" + carPlateNo + '\'' +
                '}';
    }
}
