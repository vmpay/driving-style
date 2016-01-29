package com.diploma.vmpay.drivingstyle;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class GpsActivity extends AppCompatActivity {

    private TextView tvGpsName, tvGpsCoord;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private static final String TAG = "gpsLogs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        tvGpsName = (TextView) findViewById(R.id.tvGpsName);
        tvGpsCoord = (TextView) findViewById(R.id.tvGpsName);

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.getAllProviders().toString() != null)
        {
            tvGpsName.setText(locationManager.getAllProviders().toString());
            Log.d(TAG, "Listig all the providers");
            // Define a listener that responds to location updates
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the network location provider.
                    Log.d(TAG, "onLocationChanged");
                    String gpsout = "";
                    gpsout = Double.toString(location.getLatitude()) + "\n" + Double.toString(location.getLongitude()) +
                            "\n" + Double.toString(location.getAltitude());
                    tvGpsCoord.setText(gpsout);

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d(TAG, "onStatusChanged");
                    if (status==LocationProvider.OUT_OF_SERVICE){
                        tvGpsCoord.setText(provider + "status:OUT_OF_SERVICE");
                    }
                    else if (status==LocationProvider.TEMPORARILY_UNAVAILABLE){
                        tvGpsCoord.setText(provider + "status:TEMPORARILY_UNAVAILABLE");
                    }
                    else if (status== LocationProvider.AVAILABLE){
                        tvGpsCoord.setText(provider + "status:AVAILABLE");
                    }
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d(TAG, "onProviderEnabled");
                    tvGpsCoord.setText("GPS Provider Enabled");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d(TAG, "onProviderDisabled");
                    tvGpsCoord.setText("GPS Provider Disabled");
                }
            };
            Log.d(TAG, "Location listener initialized");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Log.d(TAG, "Requesting Location Updates");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Log.d(TAG, "Adding GPS Status Listener");
            locationManager.addGpsStatusListener((GpsStatus.Listener) locationListener);
            Log.d(TAG, "OnCreate() finished almost succesfull");
        } else {
            //TODO: error
            tvGpsName.setText("GPS module not found.");
        }
    }

    @Override
    protected void onPause() {
        //Remove listener
        //locationManager.removeGpsStatusListener((GpsStatus.Listener) this);
        locationManager.removeGpsStatusListener((GpsStatus.Listener) locationListener);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //register for the GPS status listener
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        locationManager.addGpsStatusListener((GpsStatus.Listener) locationListener);
    }



}
