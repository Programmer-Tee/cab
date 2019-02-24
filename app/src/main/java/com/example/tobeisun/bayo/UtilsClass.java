package com.example.tobeisun.bayo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class UtilsClass {
    private static String TAG = "UtilsClass";

    public static void broadcastUserDetails(Context context) {
        Log.e(TAG, Constants.BROADCAST_USER_DETAILS);
        Intent intent = new Intent(Constants.BROADCAST_USER_DETAILS);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void broadcastDriverDetails(Context context) {
        Log.e(TAG, Constants.BROADCAST_DRIVER_DETAILS);
        Intent intent = new Intent(Constants.BROADCAST_DRIVER_DETAILS);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void broadcastRideOrdered(Context context) {
        Log.e(TAG, Constants.BROADCAST_RIDE_ORDERED);
        Intent intent = new Intent(Constants.BROADCAST_RIDE_ORDERED);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void updateSharedPref(Context context, String key, String value){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor= sharedPreferences.edit();

        editor.putString(key, value);
        editor.commit();
//        broadcastUserDetails(context);
    }

    public static String getSharedPref(Context context, String key){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, "");
    }


    public static long getDateTimeInMilliseconds(int year, int month, int day, int hourOfDay, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hourOfDay, minute, 0);
        return calendar.getTimeInMillis();
    }

    public static String getDateTimeString(long dateInTimeMillis) {
        Date date = new Date(dateInTimeMillis);

        String format = "dd MMM, yyyy hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);

        return String.format("%s%s", "", dateFormat.format(date));
    }

    public static long DateTimeStringToMillis(String dateTime){
        DateFormat format = new SimpleDateFormat("dd MMM, yyyy hh:mm a", Locale.US);
        long timeInMillis = 0;
        try{
            Date date = format.parse(dateTime);
            timeInMillis = date.getTime();

        }catch (ParseException e){
            Log.e("DateTimeUtils", "DateTimeStringToMillis: ", e);
        }

        return timeInMillis;
    }
}
