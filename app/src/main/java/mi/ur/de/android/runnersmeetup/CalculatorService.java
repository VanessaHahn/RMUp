package mi.ur.de.android.runnersmeetup;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.provider.Settings;

import java.util.Timer;

/**
 * Created by Vanessa on 01.09.2016.
 */
public class CalculatorService extends Service implements CalculatorListener{

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
    public void onCreate(){
        iBinder = new LocalBinder();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    public void setCalculatorListener(CalculatorListener listener){
        calculatorListener = listener;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            private Location lastLocation;

            @Override
            public void onLocationChanged(Location location) {
                if (lastLocation != null) {
                    currentDistance += (location.distanceTo(lastLocation) / 1000);
                }
                currentVelocity = location.getSpeed()* 3.6;
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
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates("gps",2000,0,locationListener);
        return START_STICKY;
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
        return currentVelocity;
    }

    public static double getCurrentDistance(){
        return currentDistance;
    }
}
