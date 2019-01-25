package com.example.tobeisun.bayo.communication;


/* this is the main message that we send to FCM server, telling it we are sending a notification(with title and body(content))
*   and extra datas(title, body, longitude and latitude) to a particular device token id.
*   we should figure out how to send to multiple selected devices later.
*   lets manage this one for now
*/
public class Message {
    private String to;
    private NotifyData notification;
    private NotifyExtraData data;


    public Message(String to, NotifyData notification, NotifyExtraData data) {
        this.to = to;
        this.notification = notification;
        this.data = data;

    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public NotifyData getNotification() {
        return notification;
    }

    public void setNotification(NotifyData notification) {
        this.notification = notification;
    }

    public NotifyExtraData getData() {
        return data;
    }

    public void setData(NotifyExtraData data) {
        this.data = data;
    }
}
