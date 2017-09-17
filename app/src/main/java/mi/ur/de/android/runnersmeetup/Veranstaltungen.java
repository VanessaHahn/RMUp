package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Veranstaltungen extends AppCompatActivity {

    EditText eventTitel, eventDay, eventMonth, eventYear, eventHour, eventMin, eventOrt, eventDetails;
    Button eventErstellen;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veranstaltungen);

        eventTitel = (EditText) findViewById(R.id.event_titel);
        eventDay = (EditText) findViewById(R.id.input_event_day);
        eventMonth = (EditText) findViewById(R.id.input_event_month);
        eventYear = (EditText) findViewById(R.id.input_event_year);
        eventHour = (EditText) findViewById(R.id.input_event_h);
        eventMin = (EditText) findViewById(R.id.input_event_m);
        eventOrt = (EditText) findViewById(R.id.event_ort);
        eventDetails = (EditText) findViewById(R.id.event_details);
        eventErstellen = (Button) findViewById(R.id.event_button);
        listView = (ListView) findViewById(R.id.listEvents);
        searchForEvents();
        insertEvent();
    }

    private void searchForEvents() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String id = ""+Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("showEvents",id);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d("string1",""+dbString);
            if(dbString.indexOf("/")>0){
                Log.d("hallo","hallo");
                String[] dbString1 = dbString.split("[.]");

                ArrayList<Event> eventlist = new ArrayList<>();

                int eventeintraege = dbString1.length;
                Log.d("eventeintraege",""+eventeintraege);
                if(eventeintraege>10){
                    eventeintraege = 10;
                }

                for (int i = 0; i<eventeintraege; i++) {
                    String[] string1 = new String[dbString1[i].length()];

                    for (int x = 0; x < 4; x++) {
                        string1 = dbString1[i].split("[/]");
                    }

                    Log.d("string1",""+string1[0]);
                    Log.d("string1",""+string1[1]);
                    Log.d("string1",""+string1[2]);
                    Log.d("string1",""+string1[3]);
                    Log.d("string1",""+string1[4]);

                    Event event = new Event(string1[0],string1[1],string1[2],string1[3],string1[4]);
                    eventlist.add(event);
                }

                EventListAdapter adapter = new EventListAdapter(this, R.layout.event_adapter_view_layout, eventlist);
                listView.setAdapter(adapter);

            } else{
                Toast.makeText(this, "Keine Veranstaltungen erstellt!", Toast.LENGTH_LONG).show();
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

    private void insertEvent(){
        eventErstellen = (Button) findViewById(R.id.event_button);
        eventErstellen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = Integer.parseInt(eventDay.getText().toString());
                int month = Integer.parseInt(eventMonth.getText().toString());
                int year = Integer.parseInt(eventYear.getText().toString());
                int hour = Integer.parseInt(eventHour.getText().toString());
                int min = Integer.parseInt(eventMin.getText().toString());
                if(eventTitel.getText().length() != 0
                        && eventDay.getText().length() != 0
                        && eventMonth.getText().length() != 0
                        && eventYear.getText().length() != 0
                        && eventHour.getText().length() != 0
                        && eventMin.getText().length() != 0
                        && eventOrt.getText().length() != 0
                        && eventDetails.getText().length() != 0
                        && (day>0 && day<=31)
                        && (month>0 && month<=12)
                        && (year>1917 && year<2018)
                        && (hour>=0 && hour<24)
                        && (min>=0 && hour<59)){
                    onReg();
                } else {
                    if(!(min>=0 && hour<59)){
                        eventMin.setError("Ungültige Eingabe!");
                    }
                    if(!(hour>=0 && hour<24)){
                        eventHour.setError("Ungültige Eingabe!");
                    }
                    if(!(day>0 && day<=31)){
                        eventDay.setError("Ungültige Eingabe!");
                    }
                    if(!(month>0 && month<=12)){
                        eventMonth.setError("Ungültige Eingabe!");
                    }
                    if(!(year>1917 && year<2018)){
                        eventYear.setError("Ungültige Eingabe!");
                    }
                    if(eventTitel.getText().length() == 0){
                        eventTitel.setError("fehlende Eingabe!");
                    }
                    if(eventDay.getText().length() == 0){
                        eventDay.setError("fehlende Eingabe!");
                    }
                    if(eventMonth.getText().length() == 0){
                        eventMonth.setError("fehlende Eingabe!");
                    }
                    if(eventYear.getText().length() == 0){
                        eventYear.setError("fehlende Eingabe!");
                    }
                    if(eventHour.getText().length() == 0){
                        eventHour.setError("fehlende Eingabe!");
                    }
                    if(eventMin.getText().length() == 0){
                        eventMin.setError("fehlende Eingabe!");
                    }
                    if(eventOrt.getText().length() == 0){
                        eventOrt.setError("fehlende Eingabe!");
                    }
                    if(eventDetails.getText().length() == 0){
                        eventDetails.setError("fehlende Eingabe!");
                    }
                }
            }
        });
    }

    public void onReg(){
        String titel = eventTitel.getText().toString();
        String day = eventDay.getText().toString();
        String month = eventMonth.getText().toString();
        String year = eventYear.getText().toString();
        String date = ""+year+"."+month+"."+day;
        String hour = eventHour.getText().toString();
        String min = eventMin.getText().toString();
        String uhrzeit = ""+hour+":"+min;
        String treffpunkt = eventOrt.getText().toString();
        String details = eventDetails.getText().toString();
        String userID = ""+Constants.getId();

        String type = "event_erstellen";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, titel, date, uhrzeit, treffpunkt, details, userID);
        //Constants.setValues(gender,size,weight,phone);

        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);
            Log.d("returnAsyncTaskResult",""+bool);

            if(bool.equals("Veranstaltung bereits erstellt!")){
                Toast.makeText(this,"Veranstaltung bereits erstellt!",Toast.LENGTH_LONG).show();
            }

            if(bool.equals("true")){
                Toast.makeText(this,"Veranstaltung erstellt!",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,NavigationDrawer.class));
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




}
