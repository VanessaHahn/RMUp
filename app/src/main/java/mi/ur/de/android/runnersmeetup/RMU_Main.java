package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RMU_Main extends AppCompatActivity {
    private TextView distanceView;
    private TextView velocityView;
    private ImageButton startButton;
    private boolean run = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmu__main);
        distanceView = (TextView) findViewById(R.id.textView3);
        velocityView = (TextView) findViewById(R.id.textView);
        startButton = (ImageButton) findViewById(R.id.startButton);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return;
            }
        } else {
            configureButton();
        }
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
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RMU_Main.this, CalculatorService.class);
                if(!run){
                    startService(i);
                    CalculatorService service = new CalculatorService();
                    while(!run){
                        distanceView.setText("Strecke:  " + service.getCurrentDistance() + " km");
                        velocityView.setText("Geschwindigkeit:  " + service.getCurrentVelocity()*3.6 + " km/h");
                        try {
                            wait(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    stopService(i);
                }
            }
        });
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

