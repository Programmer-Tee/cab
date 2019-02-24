package com.example.tobeisun.bayo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.tobeisun.bayo.communication.FirebaseMessageAPI;
import com.example.tobeisun.bayo.communication.Message;
import com.example.tobeisun.bayo.communication.NotifyData;
import com.example.tobeisun.bayo.communication.NotifyExtraData;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;


import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;
import static com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle;

public class DriversMap extends Fragment
        implements //        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static double lat;
    static double longg;
    PlaceAutocompleteFragment placeAutoComplete;

    PlaceAutocompleteFragment originPlaceAutoComplete;

    DatabaseReference dataa;
    String placeName;
    String email;
    String profilesong;
    Button checkCustomers;
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
    public String getsong;
    public String getstorename;
    public String getstorenumber;
    private DatabaseReference refDatabase;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SaveLatLongDestination");;

    Query lastQuery = databaseReference.child("mp").orderByKey();

Marker markerdrivers;
List<SaveLatLongDestination> Listshowlocations;

    public DriversMap() {
        // Required empty public constructor
    }


    public static DriversMap newInstance() {
        DriversMap fragment = new DriversMap();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


Log.i("CHECK", "CHECKTHISOUT");
//to retrieve the last node

        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  String message = dataSnapshot.child("message").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                System.out.println("DATABASE ERROR");
            }
        });

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_drivers_map, container, false);
        Log.i("CHECK", "CHECKTHISOUTTOO");
        //get the email that was added to the intent from customer login activity
        email = getActivity().getIntent().getStringExtra("email");
        profilesong= getActivity().getIntent().getStringExtra("ProfileSong");
        getstorename= getActivity().getIntent().getStringExtra("storename");
        getstorenumber= getActivity().getIntent().getStringExtra("storenumber");
        Log.i("Got email", "onCreate: email: "+email);


        ref = new Firebase("https://bayo-f1055.firebaseio.com/SaveLatLongDestination");

        placeAutoComplete = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_driver);

        originPlaceAutoComplete = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.origin_autocomplete_driver);

        checkCustomers = (Button) view.findViewById(R.id.buttonsetdest);
        linearLayout = (LinearLayout) view.findViewById(R.id.Linear);



        dataa = FirebaseDatabase.getInstance().getReference();


        checkCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //passing price to the pop up activity

               // Intent sendingIntent = new Intent(getContext(), popupforcustomer.class);
               // sendingIntent.putExtra("Double_price", price);

              //  startActivity(sendingIntent);
                //CalculationByDistance(originLoc, destinationLoc);



                //sending message to a particulat device starts here
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                final String key = "AIzaSyArCZO8yh0tUlvxqv7H_8iiSnT2-kRB4ZY"; //your firebase cloud messaging server key
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {

                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()

                                .header("Authorization", "key=" + key); //http request header

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                        //return chain.proceed(request);


                        //passing price to the pop up activity

//                Intent sendingIntent = new Intent(CustomersMap.this, popupforcustomer.class);
//                sendingIntent.putExtra("Double_price", price);
//
//                startActivity(sendingIntent);
                        //  CalculationByDistance(originLoc, destinationLoc);


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


                // moved it here, you added it in a wrong place
                DatabaseReference newPost = databaseReference.push();
                //the push() command is already creating unique key
//                newPost.setValue("");
                Log.i("", "showwwwll" );
             //   Listshowlocations= new ArrayList<>();
                databaseReference.push().setValue(markerdrivers);

            }
        });

        //to make it come here first on launching so it will check if the user truly has an account before telling him to go to login page...
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivity(new Intent(getContext(), CustomerLogin.class));

            getActivity().finish();
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

                zoomToLocation(destinationLoc); CalculationByDistance(originLoc, destinationLoc);






                //added by abeeb
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


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapDriver);
        mapFragment.getMapAsync(this);
        Log.i("", "showwwwss" );
ChildEventListener childEventListener;

         databaseReference = FirebaseDatabase.getInstance().getReference("SaveLatLongDestination");
                                                        databaseReference.push().setValue(markerdrivers);
        Log.i("", "showwww6" );
        return view;
    }
    //how is it now

    String token2;
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationClickListener(this);
        SaveProfiledetails();
        Log.i("", "showwww7" );
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SaveLatLongDestination");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


        Log.i("Marker1 displayed ", "markerrr3");
        for (DataSnapshot child : dataSnapshot.getChildren()){

            if(child.hasChild("latitude") && child.hasChild("longitude")) {

                double latitude = child.child("latitude").getValue(Double.class);


                double longitude = child.child("longitude").getValue(Double.class);
                Log.i("Marker1 displayed ", "markerhere");
                // for (int i = 0; i < latitude; i++){
                LatLng latLng = new LatLng(latitude, longitude);
                //added Marker because
                Marker marker=mMap.addMarker(new MarkerOptions().position(latLng));
            }


        }
        Log.i("", "showwww3" );
//        if (dataSnapshot.hasChildren()) { LatLngBounds bounds;
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//            Log.i("", "showwww4" );
//        for (DataSnapshot s: dataSnapshot.getChildren()) {
//            Log.i("", "showwww2" );
//
//            //  Double  takelat=   s.child("latitude").getValue(Double.class);
//            //   Double takelong=  s.child("longitude").getValue(Double.class);
//            // SaveLatLong saveLatLong2 = new SaveLatLong(takelat, takelong);
//
//            SaveLatLongDestination save = s.getValue(SaveLatLongDestination.class);
//
//            SaveLatLongDestination location = new SaveLatLongDestination();
//
//            Double takelat = s.child("latitude").getValue(Double.class);
//            Double takelong = s.child("longitude").getValue(Double.class);
//
//            Log.i("", "latitudedriver "+takelat);
//            Log.i("", "longitudedriver" +takelong);
//            Log.i("", "showwww" );
//
//            LatLng latLng = new LatLng(takelat, takelong);
//            builder.include(latLng);
//            bounds = builder.build();
//
//            MarkerOptions markerOptions = new MarkerOptions()
//                    .position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//            Marker marker = mMap.addMarker(markerOptions);
//
//          mMap.addMarker(markerOptions);
//        }
//            //mMap.addMarker(new MarkerOptions().position(location)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//
//
//
//    }
 }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});


        Log.i("Marker1 displayed ", "markerrr");
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("SaveLatLongDestination");
        Log.i("Marker1 displayed ", "markerrr2");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Marker1 displayed ", "markerrr3");
                for (DataSnapshot child : dataSnapshot.child("SaveLatLongDestination").getChildren()){
                   double latitude = child.child("latitude").getValue(Double.class);
                    double longitude = child.child("longitude").getValue(Double.class);
                    Log.i("Marker1 displayed ", "markerhere");
                   // for (int i = 0; i < latitude; i++){
                        LatLng latLng = new LatLng(latitude, longitude);
               Marker   markerdrivers=  mMap.addMarker(new MarkerOptions()
                            .position(latLng)



                  );
                    }
                    Log.i("Marker1 displayed ", "marker1");
                }




            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        LocationManager locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if(location != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }





        if(mMap != null) {
            mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location location) {

                    Log.i("OnMyLocationChanged", "OriginLoc: lat: " + location.getLatitude() + " long: " + location.getLongitude());

                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            LatLng newLocation = new LatLng(
                                    dataSnapshot.child("latitude").getValue(Double.class),
                                    dataSnapshot.child("longitude").getValue(Double.class)
                            );
                            mMap.addMarker(new MarkerOptions()
                                    .position(newLocation)
                                    .title(dataSnapshot.getKey()));
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }

            });
//            Log.i("OnMapReady", "OriginLoc: lat: "+destinationLoc.latitude +" long: "+destinationLoc.longitude);

        }

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

                Toast.makeText(getContext(), "token id : "+token2, Toast.LENGTH_SHORT).show();

            }
        });


    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermissionn(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else{

            if(!isLocationOn()){
                Toast.makeText(getContext(), "Your location is turned off.\nKindly switch it on and restart the app", Toast.LENGTH_SHORT).show();
            }

            if (mMap != null && isLocationOn()){
                // Access to the location has been granted to the app.
//                mMap.setMyLocationEnabled(true);


                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
                locationMode = Settings.Secure.getInt(getContext().getContentResolver(), Settings.Secure.LOCATION_MODE);
            }catch(Settings.SettingNotFoundException ex){
                ex.printStackTrace();
                Log.e("Location On?", "enableMyLocation: ",ex );
                return false;

            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        }else{
            locationProviders = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }


    @Override
    public void onMyLocationClick(@NonNull Location location) {
        try {


            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
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

    //comment this out
    @Override
    public void onResume() {
        super.onResume();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

//    protected void onResumeFragments(){
//
//    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
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

        SaveProfiledetails();
        // dataa.child(getIntent().getStringExtra("EdiTtEXTvALUE"));


        if(latitude != 0.0 && longitude != 0.0) {
            SaveLatLongDestination saveLatLongDestination = new SaveLatLongDestination(latitude, longitude, placeName, email,new Date().toString());

            //generate key for first insert,any subsequent inserts will update the former one
            if (key.isEmpty()) key = dataa.child("").push().getKey();
            dataa.child("SaveLatLongDestination").child(key).setValue(saveLatLongDestination);


            Toast.makeText(getContext(), "latitude saved", Toast.LENGTH_LONG).show();
            Log.d("the third latlng is", "" + saveLatLongDestination);
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


        ref.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.firebase.client.DataSnapshot dataSnapshot) {
                //clear map of all markers
                mMap.clear();

                driverList.clear();
                //add userlocation marker
                originMarker = mMap.addMarker(new MarkerOptions().position(originLoc).title("Your location"));
                if(destinationMarker != null) mMap.addMarker(new MarkerOptions().position(destinationMarker.getPosition()));

                for (com.firebase.client.DataSnapshot child : dataSnapshot.getChildren()) {

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
                            SaveLatLong saveLatLong = new SaveLatLong(latitude, longitude, "","", "","","", "");
                            driverList.add(saveLatLong);
                            addDriverLocationMarker(latitude, longitude, placeName);
                        }

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull FirebaseError firebaseError) {

            }
        });


    }

    public void SaveProfiledetails ()
    {
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor= sharedPreferences.edit();
        //to get the information stored in preferences
        getsong =sharedPreferences.getString(getString(R.string.song), " default value");
        getstorename =sharedPreferences.getString(getString(R.string.storename), " default value");
        getstorenumber =sharedPreferences.getString(getString(R.string.storenumber), " default value");


        Log.d(getsong, " the detail is");
    }

}
