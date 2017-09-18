package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    private TextView showName, showDate, showGeschlecht, showEmail, showGeschwindigkeit;
    private String id, username, handynummer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        username = extras.getString("Username");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        showName = (TextView) findViewById(R.id.showUsername);
        showDate = (TextView) findViewById(R.id.showGeburtsdatum);
        showGeschlecht = (TextView) findViewById(R.id.showGeschlecht);
        showEmail = (TextView) findViewById(R.id.showEmail);
        showGeschwindigkeit = (TextView) findViewById(R.id.showGeschwindigkeit);

        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("showProfil",username);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d("dbString",""+dbString);
            if(dbString.indexOf("/")>0){
                String[] string = dbString.split("[/]");
                showName.setText(username);
                id = string[0];
                showDate.setText(string[1]);
                showGeschlecht.setText(string[2]);
                showEmail.setText(string[3]);
                handynummer = string[4];
                showGeschwindigkeit.setText(string[5] +" km/h");
            } else{
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriends();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder logout = new AlertDialog.Builder(ProfileActivity.this);
                ActivityCompat.requestPermissions(ProfileActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                logout.setMessage("SMS senden an: " + username +"?")
                        .setCancelable(false)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(handynummer.isEmpty()) {
                                    toast();
                                } else {
                                    smsSenden();
                                }
                            }
                        })
                        .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = logout.create();
                alert.setTitle("Logout");
                alert.show();
            }
        });
    }

    private void addFriends(){
        String ownID = "" + Constants.getId();
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("addFriends",ownID,id,username);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d("dbString",""+dbString);
            if(dbString.equals("true")){
                Toast.makeText(this, username+" hinzugefügt!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, Vorschlaege.class));
            } else{
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void smsSenden(){
        SmsManager manager = SmsManager.getDefault();
        String sms = "Hallo, ich bin " + Constants.getName() + " von RunnersMeetUp und möchte mit dir Kontakt aufnehmen!";
        manager.sendTextMessage(handynummer,null,sms,null,null);
    }

    private void toast(){
        Toast.makeText(this, "SMS senden nicht möglich!", Toast.LENGTH_LONG).show();
    }


}
