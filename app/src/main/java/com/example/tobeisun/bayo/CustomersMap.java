package com.example.tobeisun.bayo;



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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Console;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;


/**
 * This demo shows how GMS Location can be used to check for changes to the users location.  The
 * "My Location" button uses GMS Location to set the blue dot representing the users location.
 * Permission for {@link android.Manifest.permission#ACCESS_FINE_LOCATION} is requested at run
 * time. If the permission has not been granted, the Activity is finished with an error message.
 */
public class CustomersMap extends AppCompatActivity
        implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    PlaceAutocompleteFragment placeAutoComplete;
    DatabaseReference dataa ;
    public double lat;
    double longg;
    String placeName;
    String email ;
    Button setdestination;

    private LatLng latlngloc3 =null;
private  LatLng latlngloc2 = null;
    String date;
    Marker marker ;

    String pattern = "yyyy-MM-dd";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers_map);



setdestination= (Button)findViewById(R.id.buttonsetdest);


        dataa= FirebaseDatabase.getInstance().getReference() ;

        placeAutoComplete = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);

        //to make it come here first on launching so it will check if the user truly has an account before telling him to go to login page...

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            // Start sign in/sign up activity
            startActivity(new Intent(CustomersMap.this, CustomerLogin.class));

            finish();
        } else {
            // User is already signed in. Therefore, display
            // a welcome Toast


           // startActivity(new Intent(CustomersMap.this,CustomersMap.class));
        }








        placeAutoComplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

               /* Log.d("Maps", "Place selected: " + place.getName()); //
           lat= place.getLatLng().latitude;
                longg= place.getLatLng().longitude ;







             placeName = place.getName().toString();

                email= getIntent().getStringExtra("getemail");



                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                date= simpleDateFormat.format(new Date());



;
                Toast.makeText(CustomersMap.this, "the longitude is" +longg, Toast.LENGTH_SHORT).show();


                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, longg), 13));

                CameraPosition cameraPosition = new CameraPosition.Builder()

                        .target(new LatLng(lat, longg))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mMap.addMarker((new MarkerOptions()
                        .position(new LatLng(lat,longg))
                        ));


                //DATABASE TO SAVE THE LATITUDE AND LONGITUDE



                savelatandlong();


              /*)  Thread thread = new Thread ()
                {
                    @Override
                    public void run() {
                        try
                        {
                            sleep(3000); // to delay for 3 seconds

                        }

                        catch (Exception e)
                        {
                            e.printStackTrace();

                        }

                        finally
                        {
                           startActivity(new Intent(CustomersMap.this,CustomerDestinationMap.class));


                )


                    }
                } ;






                thread.start();

*/

                lat= place.getLatLng().latitude;
                longg= place.getLatLng().longitude ;



                 //final LatLng latlngloc1 = new LatLng(lat,longg);

              //  final LatLng latLngloc = place.getLatLng(); //this is for the searched location
                latlngloc2= place.getLatLng();


                Log.d("the second latlng is ", "" +latlngloc2 );
          //      Log.d("first latlng", "" + latlngloc1 );

              /*  setdestination.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    double v=    CalculationByDistance(latlngloc3 ,latlngloc2);
                        Log.d("the second latlng is ", "" + latlngloc2 );
                    }
                }); */
                    CalculationByDistance(latlngloc3 ,latlngloc2 );






                marker =mMap.addMarker(new MarkerOptions().position(latlngloc2).title(place.getName().toString()));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18),2000, null);


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




    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();



    }







    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils .requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            zoomToLocation();


        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        try{



            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
           /* mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); */

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));


                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder


                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                 latlngloc3 =new LatLng(location.getLatitude(), location.getLongitude()); //this is for the searched location
                Log.d("where you are latlng is ", "" + latlngloc3 );

                marker =mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18),2000, null);


if(mMap!=null) {

    PolylineOptions poption = new PolylineOptions().add(latlngloc2).add(latlngloc3).width(5).geodesic(true).visible(true);
    mMap.addPolyline(poption);
    //to zoom the camerav


    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngloc2, 13));
}


                if(marker!=null)
                {
                    marker.remove();
                }


            }

        }catch(SecurityException ex){

        }
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        try{



            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();

            location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
            if (location != null)
            {
           /* mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();              2      // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); */

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));
                latlngloc3  =new LatLng(location.getLatitude(), location.getLongitude()); //this is for the searched location
                Log.d("where you are latlng is ", "" + latlngloc3  );

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder


                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                marker =mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(18),2000, null);





            }

        }catch(SecurityException ex){

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
        double price;
        if(kmInDec==1){
            price=58;
            Log.d("THE PRICE IS", "" + price );


        }
else{
            price =kmInDec*300;
            Log.d("THE PRICE IS", "" + price );
        }

        double d =Radius * c;
        System.out.println(d);
        return d;


    }


    /**
     * Zooms to current location
     */
private void zoomToLocation(){

    try{



        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
           /* mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition)); */


            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),13));


            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder


            latlngloc3  =new LatLng(location.getLatitude(), location.getLongitude()); //this is for the searched location
            Log.d("where you are latlng is ", "" + latlngloc3  );
            //      Log.d("first latlng", "" + latlngloc1 );

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            marker =mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(),location.getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18),2000, null);





        }

    }catch(SecurityException ex){

    }

}



    private void savelatandlong()
    {





       // dataa.child(getIntent().getStringExtra("EdiTtEXTvALUE"));



        SaveLatLong saveLatLong = new SaveLatLong(lat,longg,placeName,email,date);
              dataa.child("SaveLatLong").push().setValue(saveLatLong);



            Toast.makeText(this,"latitude saved",Toast.LENGTH_LONG).show();
        Log.d("the third latlng is", "" + saveLatLong );



        }




    }

