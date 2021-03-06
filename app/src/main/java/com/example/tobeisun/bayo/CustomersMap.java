package com.example.tobeisun.bayo;


import com.example.tobeisun.bayo.communication.FirebaseMessageAPI;
import com.example.tobeisun.bayo.communication.Message;
import com.example.tobeisun.bayo.communication.NotifyData;
import com.example.tobeisun.bayo.communication.NotifyExtraData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
//import com.firebase.client.Query;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.firebase.client.ValueEventListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class CustomersMap extends AppCompatActivity
        implements
//        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
      GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static double lat;
    static double longg;
    PlaceAutocompleteFragment placeAutoComplete;
  private ChildEventListener childEventListener;
    PlaceAutocompleteFragment originPlaceAutoComplete;

    DatabaseReference dataa;
    Marker marker2;
    String placeName;
    String profilesong;
    String getstorename;
    String getGetstorenumber;
    String email;
    Button setdestination;
    PopupWindow popUp;
    LayoutInflater layoutInflater; //will allow a new layout inside the popup window
    double price;
    String value;
    String date;
    Marker originMarker;



    String pattern = "yyyy-MM-dd";

    Marker destinationMarker;       //added by abeeb to mark destination on the map
    ArrayList<SaveLatLong> driverList= new ArrayList<>(); // added by abeeb, to hold a list of nearby drivers
    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;
    private GoogleMap mMap;
    private LinearLayout linearLayout;
    private LatLng originLoc = new LatLng(6.555997913408461,3.3317615303979746);
    private LatLng destinationLoc = new LatLng(0.0,0.0);
    private Firebase ref;
    String getsong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_map);


        //added by abeeb
        //get the email that was added to the intent from customer login activity
        email = getIntent().getStringExtra("email");
        profilesong = getIntent().getStringExtra("profilesong");

        Log.i("Got email", "onCreate: email: "+email);


        ref = new Firebase("https://bayo-f1055.firebaseio.com/SaveLatLong");

        setdestination = (Button) findViewById(R.id.buttonsetdest);
        linearLayout = (LinearLayout) findViewById(R.id.Linear);



        dataa = FirebaseDatabase.getInstance().getReference();

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);

        originPlaceAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.origin_autocomplete);

        setdestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passing price to the pop up activity

//                Intent sendingIntent = new Intent(CustomersMap.this, popupforcustomer.class);
//                sendingIntent.putExtra("Double_price", price);
//
//                startActivity(sendingIntent);



                //added by abeeb
                //sending message to a particulat device starts here
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                final  String key = "AIzaSyArCZO8yh0tUlvxqv7H_8iiSnT2-kRB4ZY"; //your firebase cloud messaging server key
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {

                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()

                                .header("Authorization", "key="+key); //http request header

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

                httpClient.addInterceptor(logging);
                OkHttpClient client = httpClient.build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://fcm.googleapis.com") //firebase cloud messaging(FCM) host address
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                FirebaseMessageAPI firebaseMessageAPI = retrofit.create(FirebaseMessageAPI.class);


                NotifyData notification = new NotifyData("Hi everyone,", "Tabitha is smarter than she thinks.");
                NotifyExtraData data = new NotifyExtraData("Ride ordered", "from android app", 0.0, 0.0);



                //http call to a particular device token(each device has its own device, so here i am using this device so you can click the bottom and receive the notification)
                //check onMapReady method to see how i got the device's firebase token id
                Call<Message> call = firebaseMessageAPI.sendMessage(new Message(token2, notification, data));

                call.enqueue(new Callback<Message>() {

                    // i need to work on this later
                    @Override
                    public void onResponse(@NonNull Call<Message> call, @NonNull retrofit2.Response<Message> response) {
                        Log.e("Response", "onResponse: message sent" + response.body().getNotification());

                    }

                    @Override
                    public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                        Log.e("Response", "onFailure: message not sent");
                    }
                });


            }
        });

        //to make it come here first on launching so it will check if the user truly has an account before telling him to go to login page...
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivity(new Intent(CustomersMap.this, CustomerLogin.class));

            finish();
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast


            //get user email from user, if user is already logged in
            email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }

        originPlaceAutoComplete.setHint("Your Location");

        placeAutoComplete.setHint("Your destination");


        originPlaceAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                originLoc = place.getLatLng();
                saveUserLatAndLong(originLoc.latitude, originLoc.longitude, place.getAddress().toString());

                originPlaceAutoComplete.setText(place.getAddress());

                findDriversNearBy();


                //add origin marker to map
                originMarker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName().toString()));

                zoomToLocation(originLoc);

            }

            @Override
            public void onError(Status status) {

            }
        });


        //for the pop up window


        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                // location of the destination the user picked,sorry i got confused with thelatlngloc2 and 3 thing
                destinationLoc = place.getLatLng();

                Log.i("the destination cordin", "" + destinationLoc);


              //clear the map of all markers
                mMap.clear();

                //add all the drivers in the driverlist to the map
                for(SaveLatLong saveLatLong : driverList){
                    addDriverLocationMarker(saveLatLong.latitude, saveLatLong.longitude, saveLatLong.placeName);
                }

                //add destination marker to map
                destinationMarker = mMap.addMarker(new MarkerOptions().position(destinationLoc).title(place.getName().toString()));

                zoomToLocation(destinationLoc);


                CalculationByDistance(originLoc, destinationLoc);




                // this is to set the view/zoom of the map to show user, drivers and destination locations
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(originMarker.getPosition());
                builder.include(destinationMarker.getPosition());
                for(SaveLatLong latLong : driverList){
                    builder.include(new LatLng(latLong.latitude, latLong.longitude));
                }
                LatLngBounds bounds = builder.build();
                int padding  = 100; //offset from edges of the map in pixels
//                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(bounds.getCenter())      // Sets the center of the map to location user
                        .zoom(12)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(50)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder

                //our bird, plane, helicopter and village witches routes
                PolylineOptions poption = new PolylineOptions().add(destinationLoc).add(originLoc).width(5).geodesic(true).visible(true);
                mMap.addPolyline(poption);

                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


            }


            @Override
            public void onError(Status status) {
                Log.d("Maps", "An error occurred: " + status);
            }
        });




        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    String token2;
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationClickListener(this);

        if(mMap != null) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {

                    Log.i("OnMyLocationChanged", "OriginLoc: lat: "+location.getLatitude() +" long: "+location.getLongitude());
                }

            });
//            Log.i("OnMapReady", "OriginLoc: lat: "+destinationLoc.latitude +" long: "+destinationLoc.longitude);



            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.clear();
                    originMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Your location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    originPlaceAutoComplete.setText("Pick up point (" + latLng.latitude +","+latLng.longitude +")");

                    originLoc = originMarker.getPosition();
                    saveUserLatAndLong(originLoc.latitude, originLoc.longitude, "Your location");

                    findDriversNearBy();

                }
            });

        }
        enableMyLocation();



        // this connects to your FCM server and obtains an token id, the id only changes when you install on another phone or one magic happens from ur server
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if(!task.isSuccessful()){
                    Log.e("getInstanceId", "getInstanceId failed");
                    return;
                }

                token2  = task.getResult().getToken();
                Log.e("Token", "onComplete: token id: "+token2);

                Toast.makeText(CustomersMap.this, "token id : "+token2, Toast.LENGTH_SHORT).show();

            }
        });


    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else{

            if(!isLocationOn()){
                Toast.makeText(this, "Your location is turned off.\nKindly switch it off and restart the app", Toast.LENGTH_SHORT).show();
            }

            if (mMap != null && isLocationOn()){
                // Access to the location has been granted to the app.
//                mMap.setMyLocationEnabled(true);


                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

                if(location != null) {
                    originLoc = new LatLng(location.getLatitude(), location.getLongitude());


                }

                findDriversNearBy();
                originPlaceAutoComplete.setText("Your location( " + originLoc.latitude +"," +originLoc.longitude +")");
                zoomToLocation(originLoc);



            }
        }
    }

    private boolean isLocationOn(){
        //check if location is on
        int locationMode = 0;
        String locationProviders;

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.KITKAT){
            try {
                locationMode = Settings.Secure.getInt(this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            }catch(Settings.SettingNotFoundException ex){
                ex.printStackTrace();
                Log.e("Location On?", "enableMyLocation: ",ex );
                return false;

            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }else{
            locationProviders = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
        try {


            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));
            if (location != null) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
                originLoc = new LatLng(location.getLatitude(), location.getLongitude()); //this is for user location
                Log.d("where you are latlng ", "" + originLoc);

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(15)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder


                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                originMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);




            }

        } catch (SecurityException ex) {

            Log.e("ZoomLocation", "onMyLocationClick: ",ex );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }



    //added by abeeb, to calculate distance between two locations in KM
    private int distanceBetweenLocations(LatLng location1, LatLng location2){
        int Radius = 6371;// radius of earth in Km
        double lat1 = location1.latitude;
        double lat2 = location2.latitude;
        double lon1 = location1.longitude;
        double lon2 = location2.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        return Integer.valueOf(newFormat.format(km));
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.d("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);


        Log.d("the fire", "" + value);


        if (kmInDec == 1) {
            price = 58;
            Log.d("THE PRICE IS", "" + price);


        } else {
            price = kmInDec * 300;
            Log.d("THE PRICE IS", "" + price);
        }


        double d = Radius * c;
        System.out.println(d);
        return d;


    }

    /**
     * Zooms to current location
     */
    private void zoomToLocation(LatLng location) {

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), 13));


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.latitude, location.longitude))      // Sets the center of the map to location user
                        .zoom(18)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(50)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder


                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.latitude, location.longitude)));
    }


    //refactored by abeeb, to accept parameters
    String key = "";
    private void saveUserLatAndLong(double latitude, double longitude, String placeName ) {


        // dataa.child(getIntent().getStringExtra("EdiTtEXTvALUE"));


        if(latitude != 0.0 && longitude != 0.0) {
            SaveLatLong saveLatLong = new SaveLatLong(latitude, longitude, placeName, email, new Date().toString(), getsong
, getstorename, getGetstorenumber
            );

            //generate key for first insert,any subsequent inserts will update the former one
            if (key.isEmpty()) key = dataa.child("").push().getKey();
            dataa.child("SaveLatLong").child(key).setValue(saveLatLong);


            Toast.makeText(this, "latitude saved", Toast.LENGTH_LONG).show();
            Log.d("the third latlng is", "" + saveLatLong);
        }


    }


    //added by abeeb
    //this adds driver markers to the map
    LatLngBounds.Builder builder = new LatLngBounds.Builder();
    private void addDriverLocationMarker(double latitude, double longitude, String placeName){

        LatLng latLng = new LatLng(latitude, longitude);

        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(placeName);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car));

        Marker marker = mMap.addMarker(markerOptions);

        builder.include(marker.getPosition());
        if(originMarker != null)builder.include(this.originMarker.getPosition());
        if(destinationMarker != null) builder.include(this.destinationMarker.getPosition());
        final LatLngBounds bounds = builder.build();
        int padding  = 300; //offset from edges of the map in pixels

        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(originLoc)      // Sets the center of the map to location user
                .zoom(18)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(50)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder



        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
          }
        });


    }

    private void findDriversNearBy() {

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clear map of all markers
                mMap.clear();

                driverList.clear();
                //add userlocation marker
                originMarker = mMap.addMarker(new MarkerOptions().position(originLoc).title("Your location"));
                if(destinationMarker != null) mMap.addMarker(new MarkerOptions().position(destinationMarker.getPosition()));

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    // child is a snapshot of a sport location; Basketball or Football
                    // latitude and longitude are children of the sport shapshot

                    double latitude = 0.0; // = child.child("latitude").getValue(Double.class);


                    double longitude = 0.0; // = child.child("longitude").getValue(Double.class);

                    String placeName = "";


                    if (child.child("latitude").getValue() != null) {
                        latitude = child.child("latitude").getValue(Double.class);
                        longitude = child.child("longitude").getValue(Double.class);
                        placeName = "" + child.child("placeName").getValue();


                        //calculate the distance between the user and driver location(from firebase db)
                        // returns h the distance in km
                        int distance = distanceBetweenLocations(new LatLng(latitude, longitude),
                                originLoc);

                        // if the (driver)distance is within a 2km radius, add it to the driverlist
                        // and add a green car marker on the map to represent the driver location
                        if (distance <= 2) {
                            Log.i("location nearby(<2km)", "onDataChange--> lat: " + latitude
                                    + " long: " + longitude + " placename: " + placeName);
                            SaveLatLong saveLatLong = new SaveLatLong(latitude, longitude, ""," ", " "," ","", "");
                            driverList.add(saveLatLong);
                            addDriverLocationMarker(latitude, longitude, placeName);
                        }

                    }

                }


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}






