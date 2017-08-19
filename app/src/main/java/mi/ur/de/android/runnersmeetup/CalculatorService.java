package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Vanessa on 01.09.2016.
 */
public class CalculatorService extends Service implements CalculatorListener {

    private static double currentDistance;
    private static int totalTime;
    private static int totalTimeLastKM; //Timer stand bei letzen Kiliometerwechsel
    private static int currentKilometer;
    private static double currentVelocity;
    private double avgVelocity;
    private long avgCounter;
    private int kcal;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private CalculatorListener calculatorListener;
    private IBinder iBinder;
    private long lastTimeStamp; // Fuer die Berechnung der Geschwindigkeit auf Basis von GPS

    DatabaseHelper myDb;

    @Override
    public void onCreate() {
        iBinder = new LocalBinder();
        myDb = new DatabaseHelper(this);
    }

    public void AddData() {
        boolean isInserted = myDb.insertData(totalTime, avgVelocity, currentDistance, kcal);

        if (isInserted = true) {
            String text = "insert";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(CalculatorService.this, text, duration);
            toast.show();
            Log.d("db", "insert");
        } else {
            String text = "not insert";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(CalculatorService.this, text, duration);
            toast.show();
            Log.d("db", "not insert");
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // All clients have unbound with unbindService()
        return true;
    }

    public void setCalculatorListener(CalculatorListener listener) {
        calculatorListener = listener;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setupValues();
        setupLocation();
        return START_STICKY;
    }

    private void setupValues() {
        currentDistance = 0.0; //Meter
        currentVelocity = 0.0; //KM/H
        avgVelocity = 0.0;  //KM/H
        totalTime = 0; //Sekunden
        totalTimeLastKM = 0; // counter
        currentKilometer = 0; //KM
    }

    private void setupLocation() {
        String service = Context.LOCATION_SERVICE;
        LocationManager locationManager = (LocationManager) getSystemService(service);
        String provider = LocationManager.GPS_PROVIDER;

        locationListener = new LocationListener() {
            private Location lastLocation;

            @Override
            public void onLocationChanged(Location location) {
                Log.d("Location", "New Location found");

                if (lastLocation != null) {
                    // Laufstrecke aktualisieren
                    if((int)(currentDistance/1000)<(int)((currentDistance + location.distanceTo(lastLocation))/1000)) {
                        totalTimeLastKM = 0;
                        currentKilometer++;
                    } else {
                        totalTimeLastKM++;
                    }
                    currentDistance += location.distanceTo(lastLocation);
                    totalTime ++;
                    String formattedTime = Constants.getFormatedTime(totalTime);
                    String formattedKMTime = Constants.getFormatedTime(totalTimeLastKM);
                    currentVelocity = location.getSpeed() * 3.6;

                    // Kalorien Verbrauch
                    kcal = (int) calculateKCal(currentDistance);
                    // Mittlere Geschwindigkeit
                    // Basis eines Gewichteten Mitellwertes: https://de.wikipedia.org/wiki/Gewichtung
                    // avgVelocity = (avgCounter * avgVelocity + currentVelocity) / (avgCounter + 1)
                    // Der letzte Durchschnittswert mit allen bisherigen Berechnungen gewichtet,
                    // der neue Wert immer mit 1
                    avgVelocity = (currentDistance/totalTime) * 3.6;

                    // Update GUI View
                    updateVelocityView(currentVelocity);
                    updateDistanceView(currentDistance);
                    updateVelocityMeanView(avgVelocity);
                    updateTimerView(formattedTime);
                    updateTimeInKMView(formattedKMTime);
                    updateCaloriesView(kcal);


                } else {

                    Constants.setLocationLongitude(location.getLongitude());
                    Constants.setLocationLatitude(location.getLatitude());
                    //return; // Nothing to calculate if we have no lastLocalation
                }
                lastLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("Location", "Status changed: " + status);
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.d("Location", "GPS starten.");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.e("Location", "Error GPS not available");
                enableGps();
            }

        };
        updateLocation();
    }

    @Override
    public void updateTimerView(String time) {
        if (calculatorListener != null) {
            calculatorListener.updateTimerView(time);
        }
    }

    @Override
    public void enableGps() {
        if (calculatorListener != null) {
            calculatorListener.enableGps();
        }
    }

    private void updateLocation() {
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
        String provider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);
        Log.d("Location", "Location request started");
    }

    @Override
    public boolean stopService(Intent i){
        avgVelocity = (currentDistance / totalTime) * 3.6;
        Constants.setAvgVelocity(avgVelocity);
        Constants.setDistance(currentDistance);
        Constants.setTime(totalTime);
        BackgroundWorker backgroundworker = new BackgroundWorker(this);

        //um werte in db zu speichern!
        //backgroundworker.execute("changeValues",Constants.getDistance(),Constants.getTime(),Constants.getAvgVelocity());

        return super.stopService(i);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("Service", "onDestroy");
        // Stop Location Listener
        if (locationManager != null) {

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
            locationManager.removeUpdates(locationListener);
            locationManager = null;
        }

        //Intent i = new Intent(CalculatorService.this,Trainingsuebersicht.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        /*i.putExtra(Constants.KEY_TIME,totalTime);
        i.putExtra(Constants.KEY_DISTANCE,currentDistance);
        i.putExtra(Constants.KEY_CALORIES,kcal);*/
        //startActivity(i);

        AddData();

        setupValues(); // Reset Values
        // Reset GUI to Default
        updateDistanceView(0);
        updateCaloriesView(0);

        updateTimerView("00:00");
        updateTimeInKMView("00:00");

        updateVelocityView(0);
        updateVelocityMeanView(0);
    }

    class LocalBinder extends Binder {
        CalculatorService getBinder(){ return CalculatorService.this; }
    }

    public void updateVelocityView(double velocity){
        if(calculatorListener!=null) {
            calculatorListener.updateVelocityView(velocity);
        }
    }

    public void updateDistanceView(double distance){
        if(calculatorListener!=null){
            calculatorListener.updateDistanceView(distance);
        }
    }

    public void updateCaloriesView(int kcal){
        if(calculatorListener!=null){
            calculatorListener.updateCaloriesView(kcal);
        }
    }

    public void updateVelocityMeanView(final double meanSpeed){
        if(calculatorListener!=null){
            calculatorListener.updateVelocityMeanView(meanSpeed);
        }
    }

    public void updateTimeInKMView(final String time){
        if(calculatorListener!=null){
            calculatorListener.updateTimeInKMView(time);
        }
    }

    public static double getCurrentVelocity(){
        return currentVelocity;
    }

    public static double getCurrentDistance(){
        return currentDistance;
    }

    private int calculateKCal(double distance){
        // Kalorien Verbrauch
        return ((int) ((double) distance / 1000.0 * (double) Constants.getWeight()));
    }
}