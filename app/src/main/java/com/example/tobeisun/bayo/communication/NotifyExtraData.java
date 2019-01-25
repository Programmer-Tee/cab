package com.example.tobeisun.bayo.communication;


//holds notification data and any extra data you want to send through notification, i only included longitude and latitude
// we can add other data as the need arises
public class NotifyExtraData {
    private String title;
    private String body;
    private Double longitude;
    private Double latitude;

    public NotifyExtraData(String title, String body, Double longitude, Double latitude) {
        this.title = title;
        this.body = body;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
