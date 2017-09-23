package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class Vorschlaege extends AppCompatActivity {
    public ListView listView;
    private String[] string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vorschlaege);
        listView = (ListView) findViewById(R.id.list);
        searchForUser();


    }

    private void searchForUser() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String id = ""+Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute("filter",id);
        try {
            String dbString = returnAsyncTask.get()[1];
            Log.d("dbstring",""+dbString);
            if(dbString.indexOf("/")>0){
                string = dbString.split("[/]");

                final ArrayAdapter<String> listenadapter = new ArrayAdapter<String>(Vorschlaege.this,android.R.layout.simple_list_item_1, string);
                listView.setAdapter(listenadapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String user = ""+listView.getItemAtPosition(position);
                        startProfil(user);
                    }
                });


            } else{
                Toast.makeText(this, "Keine ähnlichen Läufer!", Toast.LENGTH_LONG).show();
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public void startProfil(String name){
        Intent profilIntent = new Intent(Vorschlaege.this, ProfileActivity.class);
        profilIntent.putExtra("Username",name);
        startActivity(profilIntent);
        Log.d("name",""+name);
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
