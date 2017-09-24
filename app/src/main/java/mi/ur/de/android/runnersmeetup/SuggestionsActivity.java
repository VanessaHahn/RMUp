package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class SuggestionsActivity extends AppCompatActivity {
    public ListView suggestionsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        suggestionsListView = (ListView) findViewById(R.id.suggestions_list);
        searchForUser();
    }

    private void searchForUser() {
        BackgroundWorker backgroundworker = new BackgroundWorker(this);
        String id = ""+Constants.getId();
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundworker.execute(getString(R.string.filter),id);
        try {
            String dbString = returnAsyncTask.get()[1];
            if(dbString.indexOf("/")>0){
                String[] suggestionItem = dbString.split("[/]");
                final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(SuggestionsActivity.this,android.R.layout.simple_list_item_1, suggestionItem);
                suggestionsListView.setAdapter(listAdapter);
                suggestionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String user = ""+suggestionsListView.getItemAtPosition(position);
                        startProfile(user);
                    }
                });
            } else{
                Toast.makeText(this, R.string.NoSimilarRunners, Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void startProfile(String name){
        Intent profileIntent = new Intent(SuggestionsActivity.this, ProfileActivity.class);
        profileIntent.putExtra(getString(R.string.usernameKey),name);
        startActivity(profileIntent);
    }

    @Override
    public void finish(){
        super.finish();
    }
}