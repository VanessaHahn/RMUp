package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;
import org.w3c.dom.Text;
import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;

public class RMU_Main extends AppCompatActivity implements CalculatorListener {
    private TextView timeView, distanceView, velocityView, caloriesView, velcityMeanView, timeInKMView;
    private CalculatorService calculatorService;
    private ServiceConnection serviceConnection;
    private ImageButton playbutton;
    private Button button;
    private Button update;
    private DatabaseHelper myDb;
    private NotificationManager notificationManager;
    private Notification.Builder notificationBuilder;
    private Notification notification;
    private RemoteViews remoteViews;
    private int notifyID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmu__main);
        myDb = new DatabaseHelper(this);
        button = (Button) findViewById(R.id.button);
        update = (Button) findViewById(R.id.update);
        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          Cursor res = myDb.getAllData();
                                          if(res.getCount() == 0){
                                              showMessage("Error","Nothing found");
                                              return;
                                          }

                                          StringBuffer buffer = new StringBuffer();
                                          while(res.moveToNext()){
                                              buffer.append("Id :"+res.getString(0)+"\n");
                                              buffer.append("Time :"+res.getString(1)+"\n");
                                              buffer.append("Avge :"+res.getString(2)+"\n");
                                              buffer.append("Distance :"+res.getString(3)+"\n");
                                              buffer.append("Kcal :"+res.getString(4)+"\n\n");
                                              //addNewRun(res.getInt(1),res.getDouble(2),res.getDouble(3),res.getInt(4));
                                          }

                                          showMessage("Data",buffer.toString());



                                      }
                                  }
        );

        distanceView = (TextView) findViewById(R.id.textView2);
        timeView = (TextView) findViewById(R.id.textView3);
        velocityView = (TextView) findViewById(R.id.textView);
        caloriesView = (TextView) findViewById(R.id.textView4);
        velcityMeanView = (TextView) findViewById(R.id.meanVelo);
        timeInKMView = (TextView) findViewById(R.id.timeInKiloMeter);
        playbutton = (ImageButton) findViewById(R.id.imageButton);

        /*if(!Constants.isLogged()){
            Intent i = new Intent(RMU_Main.this,LoginActivity.class);
            startActivity(i);
        }*/

        SharedPreferences prefs = getSharedPreferences("RunCondition",MODE_PRIVATE);
        Constants.setRun(prefs.getBoolean("run",false));
        Log.d("Hallo", String.valueOf(Constants.isRun()));
        if(Constants.isRun()){
          playbutton.setImageResource(R.drawable.stopbutton);
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
        if(!Constants.isRun()) {
            initServiceConnection();
        }
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
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
        playbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RMU_Main.this, CalculatorService.class);
                if(!Constants.isRun()){
                    playbutton.setImageResource(R.drawable.stopbutton);
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                    startService(intent);
                    Constants.setRun(true);
                } else {
                    playbutton.setImageResource(R.drawable.playbutton);
                    calculatorService.getRunData();
                    unbindService(serviceConnection);
                    Constants.setRun(false);
                    stopService(intent);
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
        if(Constants.isRun()) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Sets an ID for the notification, so it can be updated
            notifyID = 1;
                        /*mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("RunnersMeetUp")
                    .setContentText(R.drawable.tacho + "  0.0 km/h\n"
                            + R.drawable.distance + "  0 km\n"
                            + R.drawable.stopuhr + "  0:00 min")
                    .setSmallIcon(R.drawable.runnersmeetup);*/
            remoteViews = new RemoteViews(getPackageName(), R.layout.run_notification);
            notificationBuilder= new Notification.Builder(getApplicationContext());
            //notificationBuilder.setStyle(new Notification.BigPictureStyle())
            notificationBuilder.setSmallIcon(R.drawable.runnersmeetup);
            notificationBuilder.setContent(remoteViews);
            notification = notificationBuilder.build();
            notification.bigContentView = remoteViews;
            notificationManager.notify(notifyID , notification);
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        ActivityManager.setIsVisible(true);
        notificationManager = null;
        super.onResume();
    }

    @Override
    public void finish(){
        super.finish();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences prefs = getSharedPreferences("RunCondition",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("run", Constants.isRun());
        editor.commit();
        super.onDestroy();
    }

    @Override
    public void updateNotification(double velocity, double distance, String time) {
        if(notificationManager != null) {
            // Start of a loop that processes data and then notifies the user
            /*mNotifyBuilder.setContentText(R.drawable.tacho + "  " + new DecimalFormat("0.0").format(velocity * 3.6) + " km/h" + "\n"
                    + R.drawable.distance + "  " + ((int) distance / 1000.0) + " km" + "\n"
                    + R.drawable.stopuhr + "  " + time + " min")
            // Because the ID remains unchanged, the existing notification is
            // updated.
            notificationManager.notify(
                    notifyID,
                    mNotifyBuilder.build());*/

            remoteViews.setTextViewText(R.id.velocity, new DecimalFormat("0.0").format(velocity));
            remoteViews.setTextViewText(R.id.distance, new DecimalFormat("0.000").format(distance/1000));
            remoteViews.setTextViewText(R.id.time, time);
            remoteViews.setImageViewResource(R.id.notification_icon, R.drawable.runnersmeetup);
            remoteViews.setImageViewResource(R.id.velocity_icon, R.drawable.tacho);
            remoteViews.setImageViewResource(R.id.distance_icon, R.drawable.distance);
            remoteViews.setImageViewResource(R.id.time_icon, R.drawable.stopuhr);

            notificationBuilder.setContent(remoteViews);
            notificationManager.notify(notifyID , notification);
        }
    }

    @Override
    public void storeRunData(final double velocity, final double distance, final int time){
        Log.d("storeData",""+velocity+""+distance+""+time);
        String geschwindigkeit = ""+velocity;
        String strecke = ""+distance;
        String dauer = ""+time;
        String id = ""+Constants.getId();
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("laufSpeichern",geschwindigkeit,strecke,dauer,id);
        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);
            if(bool.equals("true")){
                Toast.makeText(this,"Lauf gespeichert!",Toast.LENGTH_SHORT).show();
            }else{
                // Not successful
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
            Log.d("RegisterActivity", "Registration  failed!");
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
            Log.d("RegisterActivity", "Registration  failed!");
        }
    }

    @Override
    public void updateVelocityView(final double velocity) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String formatedVelo = String.format("%.1f", velocity);
                velocityView.setText("Geschwindigkeit:  " + formatedVelo + " km/h");
            }
        });

    }


    @Override
    public void updateDistanceView(final double distance) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(distance < 1000){
                    String formatedDist = String.format("%.0f", distance);
                    distanceView.setText("Strecke:  " + formatedDist + " m");
                }else{
                    String formatedDist = String.format("%.3f", distance/1000);
                    distanceView.setText("Strecke:  " + formatedDist + " km");
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

    @Override
    public void updateVelocityMeanView(final double meanSpeed){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String formatedMeanVelo = String.format("%.1f", meanSpeed);
                velcityMeanView.setText("Durschnittsgeschwindigkeit:  " +  formatedMeanVelo + " km/h");
            }
        });
    }
    @Override
    public void updateTimeInKMView(final String time){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeInKMView.setText("Zeit in diesem Kilometer:  " + time + " min");
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