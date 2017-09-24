package mi.ur.de.android.runnersmeetup;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FriendsProfileActivity extends AppCompatActivity {

    private TextView showName, showDate, showGeschlecht, showEmail, showGeschwindigkeit, showVeranstaltungen;
    private String id, username, handynummer;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        initDeleteFriendship();
        showProfile();
        initSmsButton();
        searchForEvents();
    }

    private void initLayout() {
        setContentView(R.layout.activity_friends_profile);
        listView = (ListView) findViewById(R.id.listFriendsEvents);
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        username = extras.getString(getString(R.string.username));
        showName = (TextView) findViewById(R.id.showFriendUsername);
        showDate = (TextView) findViewById(R.id.showFriendGeburtsdatum);
        showGeschlecht = (TextView) findViewById(R.id.showFriendGeschlecht);
        showEmail = (TextView) findViewById(R.id.showFriendEmail);
        showGeschwindigkeit = (TextView) findViewById(R.id.showFriendGeschwindigkeit);
        showVeranstaltungen = (TextView) findViewById(R.id.textView20);
        showVeranstaltungen.setText(username+"s Veranstaltungen:");
    }

    private void initDeleteFriendship() {
        FloatingActionButton delete = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteFriend = new AlertDialog.Builder(FriendsProfileActivity.this);
                ActivityCompat.requestPermissions(FriendsProfileActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                deleteFriend.setMessage(R.string.friendshipWith + username +getString(R.string.delete))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFriendship();
                            }
                        })
                        .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = deleteFriend.create();
                alert.show();
            }
        });
    }

    private void showProfile() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("showProfil",username);
        try {
            String dbString = returnAsyncTask.get()[1];
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
                //failed
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void initSmsButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.friendfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder logout = new AlertDialog.Builder(FriendsProfileActivity.this);
                ActivityCompat.requestPermissions(FriendsProfileActivity.this,new String[]{Manifest.permission.SEND_SMS},1);
                logout.setMessage(R.string.sendSms + username +"?")
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(handynummer.isEmpty()) {
                                    toast();
                                } else {
                                    smsSenden();
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

    private void searchForEvents() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("showEvents",id);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d("event",""+dbString);
            if(dbString.indexOf("/")>0){
                String[] dbStrings = dbString.split("[.]");

                ArrayList<Event> eventlist = new ArrayList<>();

                int eventeintraege = dbStrings.length;
                Log.d("eventeintraege",""+eventeintraege);
                if(eventeintraege>10){
                    eventeintraege = 10;
                }

                for (int i = 0; i<eventeintraege; i++) {
                    String[] eventItem = new String[dbStrings[i].length()];

                    for (int x = 0; x < 4; x++) {
                        eventItem = dbStrings[i].split("[/]");
                    }

                    Event event = new Event(eventItem[0],eventItem[1],eventItem[2],eventItem[3],eventItem[4]);
                    eventlist.add(event);
                }

                EventListAdapter adapter = new EventListAdapter(this, R.layout.event_item, eventlist);
                listView.setAdapter(adapter);

            } else{
                Toast.makeText(this, R.string.noEventCreated, Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
        }
    }

    private void deleteFriendship(){
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String userID = ""+Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("deleteFriendship",userID, id);
        try {
            String dbString = returnAsyncTask.get()[1];
            if(dbString.equals("true")) {
                Toast.makeText(this, getString(R.string.friendshipWith) + username + getString(R.string.deleted), Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, FriendsActivity.class));
            } else{
                Toast.makeText(this, R.string.couldNotDeleteFriendship, Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
        }
    }

    private void smsSenden(){
        SmsManager manager = SmsManager.getDefault();
        String sms = R.string.salutation + Constants.getName() + R.string.contacting;
        Toast.makeText(this, R.string.sendedSms, Toast.LENGTH_LONG).show();
        manager.sendTextMessage(handynummer,null,sms,null,null);
    }

    private void toast(){
        Toast.makeText(this, R.string.smsNotPossible, Toast.LENGTH_LONG).show();
    }
}