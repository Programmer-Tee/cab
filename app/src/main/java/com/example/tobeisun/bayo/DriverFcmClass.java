package com.example.tobeisun.bayo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


// so this is a background service( we talked about this last night), it only receives firebase messaging event
//check how i registered it in the AndroidManifest file, all service must be registered for it to work
// so what this does is, it triggers some methods when some event happen.
public class DriverFcmClass extends FirebaseMessagingService {


    //this method is triggered when a firebase message is received by the device
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {



        //remember when i sent message in the check price button of customersMap activity,
        // this is where you get notifyData object that was part of the message
        if(remoteMessage.getNotification() != null){

            //do something with the notification object
            String message = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();
            Log.e("DriverFcmClass", "onMessageReceived:  message: "
                    +message +" and title: "+title);
            Log.e("FCMClass", "onMessageReceived: message"+ remoteMessage.getNotification() );



            //this is where the notification thingy starts from
            Context context = getApplicationContext();   //get a context
            Intent addIntent = new Intent(context, CustomerDestinationMap.class); //what should happen when the notfication is pressed


            //the intent wrapped in a pending intent cos thats what notification accepts
            PendingIntent mReminderPendingIntent = PendingIntent.getActivity(context, 100,
                    addIntent, PendingIntent.FLAG_IMMUTABLE);



            //this is where you build notifiation
            //so yes, i talked about channel id, noticed i used value of app_name string,
            // check android manifest there is a metadata telling the phone to use app_name string as its firebase message notification channel id,
            //if the channel id here differs from  the one in android manifest, your notification wont show
            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(context, getString(R.string.app_name))
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round)) //icon to display in the notification
                    .setSmallIcon(R.mipmap.ic_launcher_round) //here too
                    .setContentTitle(title) //notification title
                    .setContentText(message) //notification message
                    .setTicker(title) //dont really know what this does, lool
                    .setContentIntent(mReminderPendingIntent) //our intent
                    .setAutoCancel(true)  //if the notification should be dismissable, like you can just swipe it off
//                    .setPriority(Notification.PRIORITY_MAX)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)); //sound to make




            //get notification manager
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            //rememeber what i was saying about notification not showing when you are using the app, didnt want to scare you
            //it happened and it has to do with my android os version, versions from android oreo will have to add this lines
            // to be able to show notification while you are using the app(app is in foreground)
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
                NotificationChannel mChannel = new NotificationChannel(getString(R.string.app_name), title, NotificationManager.IMPORTANCE_DEFAULT);
                mChannel.setDescription(message);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.BLUE);
                notificationManager.createNotificationChannel(mChannel);
            }

            if (notificationManager != null)
                notificationManager.notify(100, mNotificationBuilder.build());
        }



        //this is where you get the NotifyExtraData object that was part of the message
        if(remoteMessage.getData().size() > 0){
            Log.e("FCMCLass", "onMessageReceived: Data: " +remoteMessage.getData());
            double lat = Double.valueOf(remoteMessage.getData().get("latitude"));
            double longitude = Double.valueOf(remoteMessage.getData().get("longitude"));

            Log.e("FCMClass", "onMessageReceived: lat: "+lat+", long: "+longitude );

        }
    }


    //this method is triggered when a device token id changes due to FCM server magic i told you about
    //this hardly happens tho
    @Override
    public void onNewToken(String s) {
//        super.onNewToken(s);
        // s is what the token changes to
        //we can make update to it when we store it in the firebase database, you get?
        Log.e("FCM", "onNewToken: "+s);
    }
}
