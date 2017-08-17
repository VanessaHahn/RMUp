package mi.ur.de.android.runnersmeetup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FriendsActivity extends AppCompatActivity {
    private ExpandableListView listView;
    private ArrayList<String> friends;
    private ArrayAdapter<String> arrayAdapter;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        spinner = (Spinner) findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        listView = (ExpandableListView) findViewById(R.id.expandableListView);
        friends = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, friends);
        listView.setAdapter(arrayAdapter);
        SharedPreferences prefs = getSharedPreferences("DatabaseConnection",MODE_PRIVATE);
        Constants.setId(prefs.getInt("id", -1));
        Constants.setLogged(prefs.getBoolean("logged", false));
        Constants.setName(prefs.getString("name", ""));
        Constants.setPhone(prefs.getString("phone", ""));
        searchForFriends();
    }

    private void searchForFriends() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String [] params = {"filter", Constants.getAvgVelocity(), Constants.getLocationLongitude(), Constants.getLocationLatitude()};
        try {
            manageResult(backgroundworker.execute(params).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void manageResult(String[] result) {
        for(String s: result){
            friends.add(s);
        }
    }

    @Override
    public void finish(){
        SharedPreferences prefs = getSharedPreferences("DatabaseConnection",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("id", Constants.getId());
        editor.putBoolean("logged", Constants.getLogged());
        editor.putString("name",Constants.getName());
        editor.putString("phone",Constants.getPhone());
        editor.commit();
        super.finish();
    }
}
