package com.example.tobeisun.bayo.communication;


import android.support.v4.media.app.NotificationCompat;

// holds notification data only
public class NotifyData {
    private String title;
    private String body;


    public NotifyData(String title, String body) {
        this.title = title;
        this.body = body;
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
}
