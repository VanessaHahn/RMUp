package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class RMU_Main extends AppCompatActivity implements CalculatorListener {
    private TextView timeView;
    private TextView distanceView;
    private TextView velocityView;
    private ImageButton button;
    private boolean run = false;
    private CalculatorService calculatorService;
    private ServiceConnection serviceConnection;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmu__main);
        distanceView = (TextView) findViewById(R.id.textView2);
        timeView = (TextView) findViewById(R.id.textView3);
        velocityView = (TextView) findViewById(R.id.textView);
        button = (ImageButton) findViewById(R.id.button);

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
                run = !run;
                if(run){
                    startService(i);
                } else {
                    stopService(i);
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
    public void updateVelocityView(double velocity) {
        velocityView.setText("Geschwindigkeit:  " + velocity + " km/h");
    }

    @Override
    public void updateDistanceView(double distance) {
        distanceView.setText("Strecke:  " + distance + " km");
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
                //GPS an/aus
                return true;

            case R.id.music_icon:
                //music an/aus
                return true;

            case R.id.action_about:

                return true;

            case R.id.action_profil:
                return true;

            case R.id.action_trainingsuebersicht:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



