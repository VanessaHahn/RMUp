package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RMU_Main extends AppCompatActivity implements CalculatorListener {
    private TextView timeView, distanceView, velocityView, caloriesView, velcityMeanView, timeInKMView;
    private Button button;
    private CalculatorService calculatorService;
    private ServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmu__main);
        distanceView = (TextView) findViewById(R.id.textView2);
        timeView = (TextView) findViewById(R.id.textView3);
        velocityView = (TextView) findViewById(R.id.textView);
        caloriesView = (TextView) findViewById(R.id.textView4);
        velcityMeanView = (TextView) findViewById(R.id.meanVelo);
        timeInKMView = (TextView) findViewById(R.id.timeInKiloMeter);
        button = (Button) findViewById(R.id.button);



        /*if(!Constants.isLogged()){
            Intent i = new Intent(RMU_Main.this,LoginActivity.class);
            startActivity(i);
        }*/

        SharedPreferences prefs = getSharedPreferences("RunCondition",MODE_PRIVATE);
        Constants.setRun(prefs.getBoolean("run", false));
        if(Constants.isRun()){
            button.setText("STOP");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        } else {
            configureButton();
        }
        initServiceConnection();
    }

    @Override
    public void onRequestPermissionsResult( int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    configureButton();
                }
                return;
        }
    }

    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RMU_Main.this, CalculatorService.class);
                if(!Constants.isRun()){
                    startService(i);
                    stopService(i);
                    button.setText("STOP");
                    Constants.setRun(true);
                } else {
                    stopService(i);
                    button.setText("START");
                    Constants.setRun(false);
                }
            }
        });
    }

    private void initServiceConnection() {

        serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceDisconnected(ComponentName name) {
                System.out.println("Service disconnected");
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
               calculatorService = ((CalculatorService.LocalBinder) service).getBinder();
                if (calculatorService != null)
                    calculatorService.setCalculatorListener(RMU_Main.this);
            }
        };
    }

    @Override
    protected void onPause() {
        ActivityManager.setIsVisible(false);
        unbindService(serviceConnection);
        super.onPause();
    }

    @Override
    protected void onResume() {
        ActivityManager.setIsVisible(true);
        bindService(new Intent(RMU_Main.this, CalculatorService.class), serviceConnection, BIND_AUTO_CREATE);
        super.onResume();
    }

    @Override
    public void finish(){
        SharedPreferences prefs = getSharedPreferences("RunCondition",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("run", Constants.isRun());
        editor.commit();
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void updateVelocityView(final double velocity) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                velocityView.setText("Geschwindigkeit:  " + velocity + " km/h");
            }
        });

    }

    @Override
    public void updateDistanceView(final double distance) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(distance < 1000){
                    distanceView.setText("Strecke:  " + distance + " m");
                }else{
                    distanceView.setText("Strecke:  " + distance/1000 + " km");
                }
                
            }
        });

    }
    @Override
    public void updateTimerView(final String time){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeView.setText("Zeit:  " + time + " min");
            }
        });
    }


    public void updateVelcityMeanView(final String meanSpeed){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                velcityMeanView.setText("Durschnittsgeschwindigkeit:  " +  meanSpeed + " km/h");
            }
        });
    }
    public void updateTimeInKMView(final String time){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeView.setText("Zeit in diesem Kilometer:  " + time + " min");
            }
        });
    }


    @Override
    public void updateCaloriesView(final int kcal){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                caloriesView.setText("Kalorien:  " + kcal + " kcal");
            }
        });

    }

    @Override
    public void enableGps(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    //ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.gps_icon:
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return true;

            case R.id.music_icon:
                Intent ii = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                startActivityForResult(ii,1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}