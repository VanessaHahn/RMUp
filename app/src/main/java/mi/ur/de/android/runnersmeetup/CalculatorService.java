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
import java.util.TimerTask;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Vanessa on 01.09.2016.
 */
public class CalculatorService extends Service implements CalculatorListener {

    private static double currentDistance;
    private static int totalTime;
    private static double currentVelocity;
    private double avgVelocity;
    private int kcal;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private CalculatorListener calculatorListener;
    private Timer timer;
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
        setupValues();
        setupLocation();
        //setupTimer();
        return START_STICKY;
    }

    private void setupValues() {
        currentDistance = 0.0;
        currentVelocity = 0.0;
        avgVelocity = 0.0;
        totalTime = 0;
    }

    private void setupLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            private Location lastLocation;

            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation != null) {
                    currentDistance += location.distanceTo(lastLocation);
                } else {
                    Constants.setLocationLongitude(location.getLongitude());
                    Constants.setLocationLatitude(location.getLatitude());
                }
                kcal = (int) currentDistance/1000 * Constants.getWeight();
                currentVelocity = location.getSpeed() * 3.6;
                updateVelocityView(currentVelocity);
                updateDistanceView(currentDistance);
                updateCaloriesView(kcal);
                lastLocation = location;
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
    }

    private void setupTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String formattedTime = Constants.getFormatedTime(totalTime);
                updateTimerView(formattedTime);
                totalTime++;
            }
        },1000,1000);
    }

    @Override
    public void updateTimerView(String time) {
        if(calculatorListener!=null){
            calculatorListener.updateTimerView(time);
        }
    }

    @Override
    public void enableGps() {
        if(calculatorListener!=null){
            calculatorListener.enableGps();
        }
    }

    private void updateLocation() {
        locationManager.requestLocationUpdates("gps", 10000, 0, locationListener);
    }

    @Override
    public boolean stopService(Intent i){
        avgVelocity = (currentDistance / totalTime) * 3.6;
        Constants.setAvgVelocity(avgVelocity);
        Constants.setDistance(currentDistance);
        Constants.setTime(totalTime);
        if(timer!=null) {
            timer.cancel();
        }
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        backgroundworker.execute("changeValues",Constants.getDistance(),Constants.getTime(),Constants.getAvgVelocity());

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

    public void updateCaloriesView(int kcal){
        if(calculatorListener!=null){
            calculatorListener.updateCaloriesView(kcal);
        }
    }

    public static double getCurrentVelocity(){
        return currentVelocity;
    }

    public static double getCurrentDistance(){
        return currentDistance;
    }
}
