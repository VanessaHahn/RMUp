package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Trainingsuebersicht extends AppCompatActivity {

    private TextView resultBMI, resultText;
    private ArrayList<RunItem> runItems;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsuebersicht);
        resultBMI = (TextView) findViewById(R.id.result_text);
        resultText = (TextView) findViewById(R.id.result_bmi);
        double BMI = BMIauslesen();

        SharedPreferences mySPR = getSharedPreferences("data",0);
        resultBMI.setText(mySPR.getString("BMI",""+BMI));
        if (BMI < 19) {
            resultText.setText(mySPR.getString("BMITEXT","Untergewicht!"));
        }
        if (BMI >= 19 && BMI <= 24) {
            resultText.setText(mySPR.getString("BMITEXT","Normalgewicht!"));
        }
        if (BMI > 24 && BMI <= 29) {
            resultText.setText(mySPR.getString("BMITEXT","Übergewicht!"));
        }
        if (BMI > 29 && BMI <= 39) {
            resultText.setText(mySPR.getString("BMITEXT","Adipositas!"));
        }
        if (BMI > 39) {
            resultText.setText(mySPR.getString("BMITEXT","starke Adipositas!"));
        }

        initList();
        initUI();


    }

    public double BMIauslesen() {
        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if (extras != null) {
            int cm = extras.getInt(Constants.KEYCM);
            int kg = extras.getInt(Constants.KEYKG);

            CalculatorBmi calc = new CalculatorBmi();
            calc.setValues(cm, kg);
            double BMI = calc.calculateBMI();
            return BMI;
        }
        return 0.0;
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences mySPR = getSharedPreferences("data", 0);
        SharedPreferences.Editor editor = mySPR.edit();
        editor.putString("BMI", "" + BMIauslesen());
        editor.putString("BMITEXT",resultText.getText().toString());
        editor.commit();
    }

    private void initList(){
        runItems = new ArrayList<RunItem>();
    }

    private void initUI(){
        data();
        initListView();
        initListAdapter();
    }

    private void data(){
        Intent ii = getIntent();
        Bundle extra = ii.getExtras();
        if(extra!=null){
            int time = extra.getInt(Constants.KEY_TIME);
            int distance = extra.getInt(Constants.KEY_DISTANCE);
            int calories = extra.getInt(Constants.KEY_CALORIES);
            //addNewRun(time,distance,calories);
        }
    }

    private void initListView(){
        ListView list = (ListView) findViewById(R.id.runHistory);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removeTaskAtPostition(position);
                return false;
            }
        });
    }

    private void initListAdapter(){
        ListView list = (ListView) findViewById(R.id.runHistory);
        adapter = new MyAdapter(this, runItems);
        list.setAdapter(adapter);
    }

    /*private void addNewRun(int time, int distance, int calorien){
        RunItem newRun = new RunItem(time, distance,calorien);
        runItems.add(newRun);
        adapter.notifyDataSetChanged();
    }*/

    private void removeTaskAtPostition(int position){
        if(runItems.get(position) == null){
            return;
        } else {
            runItems.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    /*@Override
    protected void onStop(){
        super.onStop();
        SharedPreferences mySPR = getSharedPreferences("MySPFILE",0);
        SharedPreferences.Editor editor = mySPR.edit();
        editor.putString("KEY_CM", resultBMI.getText().toString());
        editor.putString("KEY_KG", resultText.getText().toString());
        editor.commit();
    }*/

    /*private void setText(double BMI){
        if (BMI < 19) {
            resultText.setText("Untergewicht!");
        }
        if (BMI >= 19 && BMI <= 24) {
            resultText.setText("Normalgewicht!");
        }
        if (BMI > 24 && BMI <= 29) {
            resultText.setText("Übergewicht!");
        }
        if (BMI > 29 && BMI <= 39) {
            resultText.setText("Adipositas!");
        }
        if (BMI > 39) {
            resultText.setText("starke Adipositas!");
        }
    }*/


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
