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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Trainingsuebersicht extends AppCompatActivity {

    //private TextView resultBMI, resultText;
    //private ArrayList<RunItem> runItems;
    //private MyAdapter adapter;
    //private Button button;
    private Spinner spinner;

    //SharedPreferences prefs;
    //SharedPreferences.Editor prefsEditor;

    //DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsuebersicht);
        //myDb = new DatabaseHelper(this);

        //button = (Button) findViewById(R.id.button2);
        //button.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        data();
        //    }
        //});

        spinner = (Spinner) findViewById(R.id.sortieren_nach_items);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sortieren_nach_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button speichern = (Button) findViewById(R.id.button4);
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                laden();
            }
        });


        //prefs = this.getSharedPreferences("Settings",MODE_PRIVATE);
        //prefsEditor = prefs.edit();
        //resultText = (TextView) findViewById(R.id.result_bmi);
        //String BMIText = prefs.getString(Constants.KEYBMITEXT,"---");
        //resultText.setText(BMIText);
        //resultBMI = (TextView) findViewById(R.id.result_text);
        //String BMI = prefs.getString(Constants.KEYBMI,"---");
        //resultBMI.setText(BMI);

        //initList();
        //initUI();


    }

    private void laden(){
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String id = ""+Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("laufAnzeigen",id);
        try {
            if(Constants.parseLaeufeString(returnAsyncTask.get()[1])){
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

    //private void initList(){
        //runItems = new ArrayList<RunItem>();
    //}

    //private void initUI(){
        //data();
        //initListView();
        //initListAdapter();
    //}

    //private void data(){
        //Cursor res = myDb.getAllData();
        //StringBuffer buffer = new StringBuffer();
        //while(res.moveToNext()){
            //buffer.append(res.getString(0)+". Lauf"+"\n");
            //buffer.append("Time: "+res.getString(1)+" min"+"\n");
            //buffer.append("Ø: "+res.getString(2)+" km/h"+"\n");
            //buffer.append("Distance: "+res.getString(3)+" km"+"\n");
            //buffer.append("Kcal: "+res.getString(4)+"\n\n");
            //addNewRun(buffer.toString());

        //}
   //}

    //private void addNewRun(String run){
                //RunItem newRun = new RunItem(run);
                //runItems.add(newRun);
                //adapter.notifyDataSetChanged();
    //}

    //private void initListView(){
        //ListView list = (ListView) findViewById(R.id.runHistory);
        //list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            //@Override
            //public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //removeTaskAtPostition(position);
                //return false;
        //    }
        //});
    //}

    private void initListAdapter(){
        //ListView list = (ListView) findViewById(R.id.runHistory);
        //adapter = new MyAdapter(this, runItems);
        //list.setAdapter(adapter);
    }

    private void removeTaskAtPostition(int position){
        //if(runItems.get(position) == null){
            return;
        //} else {
          //  runItems.remove(position);
            //adapter.notifyDataSetChanged();
        }
    //}

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
