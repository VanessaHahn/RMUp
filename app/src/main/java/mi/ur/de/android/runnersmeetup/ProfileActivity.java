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

    private TextView showName, showDate, showGender, showEmail, showVelocity;
    private String id, username, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        username = extras.getString("Username");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);

        showName = (TextView) findViewById(R.id.showUsername);
        showDate = (TextView) findViewById(R.id.showGeburtsdatum);
        showGender = (TextView) findViewById(R.id.showGeschlecht);
        showEmail = (TextView) findViewById(R.id.showEmail);
        showVelocity = (TextView) findViewById(R.id.showGeschwindigkeit);
        showProfile();
        contactOrAdd();
    }

    private void contactOrAdd() {
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
                logout.setMessage(getString(R.string.sendSms) + username +"?")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(phone.isEmpty()) {
                                    toast();
                                } else {
                                    smsSend();
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
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

    private void showProfile() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute(getString(R.string.showProfile),username);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d(getString(R.string.dbString),""+dbString);
            if(dbString.indexOf("/")>0){
                String[] string = dbString.split("[/]");
                showName.setText(username);
                id = string[0];
                showDate.setText(string[1]);
                String gender = string[2];
                if(!gender.equals(getString(R.string.female))) {
                    gender = getString(R.string.male);
                }
                showGender.setText(gender);
                showEmail.setText(string[3]);
                phone = string[4];
                showVelocity.setText(string[5] +getString(R.string.kmh));
            } else{
                //Failed
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void addFriends(){
        String ownID = "" + Constants.getId();
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("addFriends",ownID,id,username);
        try {
            String dbString = returnAsyncTask.get()[1];
            if(dbString.equals("true")){
                Toast.makeText(this, username+getString(R.string.userAdded), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, SuggestionsActivity.class));
            } else{
                //failed
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void smsSend(){
        SmsManager manager = SmsManager.getDefault();
        String sms = getString(R.string.salutation) + Constants.getName() + getString(R.string.contacting);
        manager.sendTextMessage(phone,null,sms,null,null);
    }

    private void toast(){
        Toast.makeText(this, R.string.smsNotPossible, Toast.LENGTH_LONG).show();
    }
}
