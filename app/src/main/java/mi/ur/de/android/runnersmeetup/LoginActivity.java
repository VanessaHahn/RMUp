package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private EditText inputName, inputPassword;
    private Button login, register;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("LoginData",MODE_PRIVATE);
        prefsEditor = prefs.edit();
        setContentView(R.layout.activity_login);
        inputName = (EditText) findViewById(R.id.input_login_name);
        inputPassword = (EditText) findViewById(R.id.input_login_password);
        login = (Button) findViewById(R.id.button_login);
        register = (Button) findViewById(R.id.button_login_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    public void OnLogin(View view){

        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();
        String type = "login";

        if((inputName.getText().length() != 0) && (inputPassword.getText().length() != 0)) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, name, password);

            try {
                if (Constants.parseLoginString(returnAsyncTask.get()[1])) {
                    Toast.makeText(this, "Welcome", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, NavigationDrawer.class));
                    Log.d("LoginActivity", "Login Succsefull");
                    prefsEditor.putInt(Constants.KEY_ID, Constants.getId());
                    prefsEditor.putString(Constants.KEY_NAME, Constants.getName());
                    prefsEditor.putFloat(Constants.KEY_WEIGHT, Constants.getGewicht());
                    prefsEditor.putInt(Constants.KEY_SIZE, Constants.getGroesse());
                    prefsEditor.commit();
                } else {
                    // Not Succsefull
                    Log.d("LoginActivity", "Login Fail!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                // Not Succsefull
                Log.d("LoginActivity", "Login Fail!");
            } catch (ExecutionException e) {
                e.printStackTrace();
                // Not Succsefull
                Log.d("LoginActivity", "Login Fail!");
            }
        } else {
            if(inputName.getText().length() == 0){
                inputName.setError("Eingabe fehlt");
            }
            if(inputPassword.getText().length() == 0){
                inputPassword.setError("Eingabe fehlt");
            }
        }
    }

}
