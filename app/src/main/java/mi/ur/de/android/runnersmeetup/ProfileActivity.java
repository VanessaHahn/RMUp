package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProfileActivity extends AppCompatActivity {

    private TextView showName, showDate, showGeschlecht, showGeschwindigkeit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        String username = extras.getString("Username");

        showName = (TextView) findViewById(R.id.showUsername);
        showDate = (TextView) findViewById(R.id.showGeburtsdatum);
        showGeschlecht = (TextView) findViewById(R.id.showGeschlecht);
        showGeschwindigkeit = (TextView) findViewById(R.id.showGeschwindigkeit);

        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("showProfil",username);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d("dbString",""+dbString);
            if(dbString.indexOf("/")>0){
                String[] string = dbString.split("[/]");
                showName.setText(string[0]);
                showDate.setText(string[1]);
                showGeschlecht.setText(string[2]);
                showGeschwindigkeit.setText(string[3] +" km/h");
            } else{
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
