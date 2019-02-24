package com.example.tobeisun.bayo.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Ride {

    private String orderedBy;
    private String orderedForName;
    private String orderedForPhone;
    private String driverName;
    private Date orderedOn;
    private LatLng pickupLocation;
    private LatLng destinationLocation;

    public Ride(String orderedBy, String orderedForName, String orderedForPhone, String driverName,
                Date orderedOn, LatLng pickupLocation, LatLng destinationLocation) {
        this.orderedBy = orderedBy;
        this.orderedForName = orderedForName;
        this.orderedForPhone = orderedForPhone;
        this.driverName = driverName;
        this.orderedOn = orderedOn;
        this.pickupLocation = pickupLocation;
        this.destinationLocation = destinationLocation;
    }

    public String getOrderedBy() {
        return orderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }

    public String getOrderedForName() {
        return orderedForName;
    }

    public void setOrderedForName(String orderedForName) {
        this.orderedForName = orderedForName;
    }

    public String getOrderedForPhone() {
        return orderedForPhone;
    }

    public void setOrderedForPhone(String orderedForPhone) {
        this.orderedForPhone = orderedForPhone;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Date getOrderedOn() {
        return orderedOn;
    }

    public void setOrderedOn(Date orderedOn) {
        this.orderedOn = orderedOn;
    }

    public LatLng getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(LatLng pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public LatLng getDestinationLocation() {
        return destinationLocation;
    }

    public void setDestinationLocation(LatLng destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "orderedBy='" + orderedBy + '\'' +
                ", orderedForName='" + orderedForName + '\'' +
                ", orderedForPhone='" + orderedForPhone + '\'' +
                ", driverName='" + driverName + '\'' +
                ", orderedOn=" + orderedOn +
                ", pickupLocation=" + pickupLocation +
                ", destinationLocation=" + destinationLocation +
                '}';
    }
}
