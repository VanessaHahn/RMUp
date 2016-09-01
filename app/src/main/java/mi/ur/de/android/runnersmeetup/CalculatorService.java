package mi.ur.de.android.runnersmeetup;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;

/**
 * Created by Vanessa on 01.09.2016.
 */
public class CalculatorService extends Service {

    double currentDistance;
    int totalTime;
    double currentVelocity;
    double avgVelocity;
    int numberVel;
    LocationManager locationManager;
    LocationListener locationListener;
    long timeStart;

    @Override
    public void onCreate(){
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
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
                currentVelocity = location.getSpeed();
                avgVelocity += currentVelocity;
                numberVel++;
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
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates("gps",2000,0,locationListener);
        return START_STICKY;
    }

    @Override
    public boolean stopService(Intent i){
        avgVelocity /= numberVel;
        return super.stopService(i);
    }

    public double getCurrentVelocity(){
        return currentVelocity;
    }

    public double getCurrentDistance(){
        return currentDistance;
    }
}
