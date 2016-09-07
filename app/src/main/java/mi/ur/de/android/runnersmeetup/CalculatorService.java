package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.app.Service;
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

import java.util.Timer;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Vanessa on 01.09.2016.
 */
public class CalculatorService extends Service implements CalculatorListener {

    private static double currentDistance;
    private static int totalTime;
    private static double currentVelocity;
    private double avgVelocity;
    private int numberVel;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private CalculatorListener calculatorListener;
    private CountDownTimer timer;
    private IBinder iBinder;

    @Override
    public void onCreate() {
        iBinder = new LocalBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void setCalculatorListener(CalculatorListener listener) {
        calculatorListener = listener;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentDistance = 0.0;
        currentVelocity = 0.0;
        avgVelocity = 0.0;
        numberVel = 0;
        totalTime = 0;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            private Location lastLocation;

            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation != null) {
                    currentDistance += location.distanceTo(lastLocation);
                }
                currentVelocity = location.getSpeed() * 3.6;
                avgVelocity += currentVelocity;
                numberVel++;
                lastLocation = location;
                updateVelocityView(currentVelocity);
                updateDistanceView(currentDistance);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                enableGps();
            }
        };
        updateLocation();
        return START_STICKY;
    }

    @Override
    public void enableGps() {
        if(calculatorListener!=null){
            calculatorListener.enableGps();
        }
    }

    private void updateLocation() {
        locationManager.requestLocationUpdates("gps", 3000, 0, locationListener);
    }

    @Override
    public boolean stopService(Intent i){
        avgVelocity/=numberVel;
        return super.stopService(i);
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

    public static double getCurrentVelocity(){
        return (int) currentVelocity;
    }

    public static double getCurrentDistance(){
        return (int) currentDistance;
    }
}
