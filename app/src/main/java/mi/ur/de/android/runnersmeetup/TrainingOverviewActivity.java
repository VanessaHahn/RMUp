package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class TrainingOverviewActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView velocityView;
    private Spinner sortSpinner;
    private ListView listView;
    private float avgVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
        setContentView(R.layout.activity_training_overview);
        velocityView = (TextView) findViewById(R.id.avg_velocity);
        listView = (ListView) findViewById(R.id.run_list);
        initSpinner();
        calculateBMI();
    }

    private void initSpinner() {
        sortSpinner = (Spinner) findViewById(R.id.sort_items);
        sortSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.sortieren_nach_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);
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
            BMIview.setText(R.string.placeholder);
        }
    }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String userID = "" + Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute(getString(R.string.showRun),userID,item);
        try {
            String dbString = returnAsyncTask.get()[1];
            if(dbString.indexOf("/")>0){
                String[] dbStrings = dbString.split("[|]");
                avgVelocity = Float.parseFloat(dbStrings[0]);
                velocityView.setText("" + avgVelocity);
                String[] runItems = dbStrings[1].split("[.]");
                ArrayList<RunItem> runList = new ArrayList<>();
                int runItemsLength = runItems.length;
                if(runItemsLength>10){
                    runItemsLength = 10;
                }
                for (int i = 0; i<runItemsLength; i++) {
                    String[] runItem = new String[runItems[i].length()];
                    for (int x = 0; x < 4; x++) {
                        runItem = runItems[i].split("[/]");
                    }
                    String[] date = runItems[i].split(" ");
                    RunItem run = new RunItem(date[0],runItem[1],runItem[2],runItem[3]);
                    runList.add(run);
                }
                RunListAdapter adapter = new RunListAdapter(this, R.layout.run_item, runList);
                listView.setAdapter(adapter);
            } else{
                Toast.makeText(this, R.string.noStoredRuns, Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
