package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Trainingsuebersicht extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView geschwindigkeitView;
    private Spinner spinner;
    public ListView lv1;
    private float avgVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsuebersicht);
        geschwindigkeitView = (TextView) findViewById(R.id.textView16);
        lv1 = (ListView) findViewById(R.id.listView);

        spinner = (Spinner) findViewById(R.id.sortieren_nach_items);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sortieren_nach_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calculateBMI();
    }


    private void calculateBMI() {
        TextView BMIview = (TextView) findViewById(R.id.BMI);
        float weight = Constants.getWeight();
        float size = (float) Constants.getSize()/100;
        if(weight != 0 && weight != 0) {
            float squareSize = (size * size);
            float bmi = weight/squareSize;
            bmi *= 10;
            bmi = Math.round(bmi);
            bmi /= 10;
            BMIview.setText(String.valueOf(bmi));
        } else {
            BMIview.setText("-");
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String userID = ""+Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("laufAnzeigen",userID,item);
        try {
            String dbString = returnAsyncTask.get()[1];
            if(dbString.indexOf("/")>0){
                String[] dbString1 = dbString.split("[|]");
                avgVelocity = Float.parseFloat(dbString1[0]);
                geschwindigkeitView.setText(""+avgVelocity);
                Log.d("string1",""+dbString1[0]);
                Log.d("string2",""+dbString1[1]);
                String[] string = dbString1[1].split("[.]");

                ArrayList<Lauf> lauflist = new ArrayList<>();

                int laufeintraege = string.length;
                if(laufeintraege>10){
                    laufeintraege = 10;
                }

                for (int i = 0; i<laufeintraege; i++) {
                    String[] string1 = new String[string[i].length()];
                    for (int x = 0; x < 4; x++) {
                        string1 = string[i].split("[/]");
                    }
                    String[] date = string[i].split(" ");
                    Log.d("date",""+date[0]);
                    Log.d("date",""+date[1]);
                    Lauf lauf = new Lauf(date[0],string1[1],string1[2],string1[3]);
                    lauflist.add(lauf);
                }

                LaufListAdapter adapter = new LaufListAdapter(this, R.layout.adapter_view_layout, lauflist);
                lv1.setAdapter(adapter);

            } else{
                Toast.makeText(this, "Keine Läufe gespeichert!", Toast.LENGTH_LONG).show();
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
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
