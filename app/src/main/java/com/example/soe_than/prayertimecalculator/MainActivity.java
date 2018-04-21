package com.example.soe_than.prayertimecalculator;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.azan.PrayerTimes;
import com.azan.TimeCalculator;
import com.azan.types.AngleCalculationType;
import com.azan.types.PrayersType;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.soe_than.prayertimecalculator.PrayTime;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks
        , com.google.android.gms.location.LocationListener {


    private static final String TAG = MainActivity.class.getSimpleName();

    TextView locationTextView;

    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    private Double latitude = 0.0;
    private Double longtitude = 0.0;
    private static int PERMISSION_REQUEST = 1;
    AppSettings appSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationTextView = findViewById(R.id.txt_location);

        appSettings = AppSettings.getInstance(this);

    }

    @Override
    protected void onStart() {
        super.onStart();


        googleApiClient.connect();
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i(TAG, "onConnected");


        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST);

        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            if (permissions.length > 0 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else if (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");


    }

    public void getTime(View view) {

        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);
        PrayTime prayers = new PrayTime();

        Log.i(TAG, longtitude + " lee lr " + latitude + " ");
        Log.i(TAG, Calendar.getInstance().getTimeZone().getOffset(Calendar.getInstance().getTimeInMillis()) + " ");


        prayers.setTimeFormat(prayers.TIME_12);
        prayers.setCalcMethod(appSettings.getInt("timeConvent", 0));
        prayers.setAsrJuristic(appSettings.getInt("juristicSetting", 0));
        prayers.setAdjustHighLats(appSettings.getInt("latitudeAdjustment", 0));
        int[] offsets = {30, 30, 30, 30, 30, 30, 30}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);

        ArrayList prayerTimes = prayers.getPrayerTimes(cal, 16.7749802,
                96.1553996, timezone);
        ArrayList prayerNames = prayers.getTimeNames();


        locationTextView.setText("");

        for (int i = 0; i < prayerTimes.size(); i++) {
            locationTextView.append("\n" + prayerNames.get(i) + " - "
                    + prayerTimes.get(i));
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        if (googleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);


            }
        }

    }

    //
    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(TAG, location.toString());
        latitude = location.getLatitude();
        longtitude = location.getLongitude();

//        locationTextView.setText(Double.toString(location.getLatitude()));
//        locationTextView.append("\n" + Double.toString(location.getLongitude()));
//        GregorianCalendar date = new GregorianCalendar();
//        PrayerTimes prayerTimes = new TimeCalculator().date(date).location(location.getLatitude(), location.getLongitude(), 0, 7).timeCalculationMethod(AngleCalculationType.MWL).calculateTimes();
//
//
//        prayerTimes.setUseSecond(true);
//
//        locationTextView.setText(prayerTimes.getPrayTime(PrayersType.FAJR) + "\n" +
//
//                prayerTimes.getPrayTime(PrayersType.SUNRISE) + "\n" +
//
//                prayerTimes.getPrayTime(PrayersType.ZUHR) + "\n" +
//
//                prayerTimes.getPrayTime(PrayersType.MAGHRIB) + "\n" +
//
//                prayerTimes.getPrayTime(PrayersType.ISHA));
//    }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_setting) {
            startActivity(new Intent(MainActivity.this, Setting.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
