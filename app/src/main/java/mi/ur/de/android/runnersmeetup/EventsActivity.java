package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class EventsActivity extends AppCompatActivity {

    EditText eventTitel, eventDay, eventMonth, eventYear, eventHour, eventMin, eventOrt, eventDetails;
    Button eventErstellen;
    public ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

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
                    Event event = new Event(string1[0],string1[1],string1[2],string1[3],string1[4]);
                    eventlist.add(event);
                }

                EventListAdapter adapter = new EventListAdapter(this, R.layout.event_item, eventlist);
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
                String titel = eventTitel.getText().toString();
                String day = eventDay.getText().toString();
                String day_regex = "[0-3]?[0-9]";
                String month = eventMonth.getText().toString();
                String month_regex = "[1,2,3,4,5,6,7,8,9,10,11,12]";
                String year = eventYear.getText().toString();
                String year_regex = "[1-2][0,9][0-9][0-9]";
                String hour = eventHour.getText().toString();
                String hour_regex = "[01]?\\d|2[0-3]";
                String min = eventMin.getText().toString();
                String min_regex = "[0-5]?\\d";
                if(eventTitel.getText().length() != 0
                        && eventDay.getText().length() != 0
                        && eventMonth.getText().length() != 0
                        && eventYear.getText().length() != 0
                        && eventHour.getText().length() != 0
                        && eventMin.getText().length() != 0
                        && eventOrt.getText().length() != 0
                        && eventDetails.getText().length() != 0
                        && (day.matches(day_regex))
                        && (month.matches(month_regex))
                        && (year.matches(year_regex))
                        && (hour.matches(hour_regex))
                        && (min.matches(min_regex))){
                    onReg();
                } else {
                    if(!(min.matches(min_regex))){
                        eventMin.setError(getString(R.string.forbiddenInput));
                    }
                    if(!(hour.matches(hour_regex))){
                        eventHour.setError(getString(R.string.forbiddenInput));
                    }
                    if(!(day.matches(day_regex))){
                        eventDay.setError(getString(R.string.forbiddenInput));
                    }
                    if(!(month.matches(month_regex))){
                        eventMonth.setError(getString(R.string.forbiddenInput));
                    }
                    if(!(year.matches(year_regex))){
                        eventYear.setError(getString(R.string.forbiddenInput));
                    }
                    if(eventTitel.getText().length() == 0){
                        eventTitel.setError(getString(R.string.missedInput));
                    }
                    if(day.length() == 0){
                        eventDay.setError(getString(R.string.missedInput));
                    }
                    if(month.length() == 0){
                        eventMonth.setError(getString(R.string.missedInput));
                    }
                    if(year.length() == 0){
                        eventYear.setError(getString(R.string.missedInput));
                    }
                    if(hour.length() == 0){
                        eventHour.setError(getString(R.string.missedInput));
                    }
                    if(min.length() == 0){
                        eventMin.setError(getString(R.string.missedInput));
                    }
                    if(eventOrt.getText().length() == 0){
                        eventOrt.setError(getString(R.string.missedInput));
                    }
                    if(eventDetails.getText().length() == 0){
                        eventDetails.setError(getString(R.string.missedInput));
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

        String type = getString(R.string.create_event);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, titel, date, uhrzeit, treffpunkt, details, userID);
        //Constants.setValues(gender,size,weight,phone);

        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);
            Log.d("returnAsyncTaskResult",""+bool);

            if(bool.equals(getString(R.string.alreadyCreated))){
                Toast.makeText(this,R.string.alreadyCreated,Toast.LENGTH_LONG).show();
            }

            if(bool.equals("true")){
                Toast.makeText(this, R.string.createdEvent,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,NavigationDrawer.class));
            }else{
                // Not successful
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
        }
    }
}
