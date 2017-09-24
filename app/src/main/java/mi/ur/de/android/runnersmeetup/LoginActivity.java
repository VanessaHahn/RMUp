 package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    private EditText inputName, inputPassword;
    private Button login, register;
    private SharedPreferences.Editor prefsEditor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initSharedPreferences();
    }

    private void initSharedPreferences() {
        prefs = getSharedPreferences(getString(R.string.loginData),MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }

    private void initLayout() {
        setContentView(R.layout.activity_login);
        inputName = (EditText) findViewById(R.id.input_login_name);
        inputPassword = (EditText) findViewById(R.id.input_login_password);
        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        register = (Button) findViewById(R.id.button_login_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
    }

    private void buttonClicked(View v) {
        switch(v.getId()) {
            case R.id.button_login:
                onLogin(v);
                break;
            case R.id.button_login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    public void onLogin(View view){
        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();
        String type = getString(R.string.login);

        if((name.length() != 0) && (password.length() != 0)) {
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, name, password);
            checkLoginData(returnAsyncTask);
        } else {
            if(inputName.getText().length() == 0){
                inputName.setError(getString(R.string.missedInput));
            }
            if(inputPassword.getText().length() == 0){
                inputPassword.setError(getString(R.string.missedInput));
            }
        }
    }

    //checks if the login was successful
    private void checkLoginData(AsyncTask<String, Void, String[]> returnAsyncTask) {
        try {
            if (Constants.parseLoginString(returnAsyncTask.get()[1])) {
                Toast.makeText(this, R.string.welcomeToast, Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, NavigationDrawer.class));
                putPreferences();
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
    }

    //"Stores" profile data to avoid to edit login data every time when you open the application by SharedPreferences
    private void putPreferences() {
        prefsEditor.putInt(Constants.KEY_ID, Constants.getId());
        prefsEditor.putString(Constants.KEY_NAME, Constants.getName());
        prefsEditor.putFloat(Constants.KEY_WEIGHT, Constants.getGewicht());
        prefsEditor.putInt(Constants.KEY_SIZE, Constants.getGroesse());
        prefsEditor.putString(Constants.KEY_EMAIL, Constants.getEmail());
        prefsEditor.putString(Constants.KEY_PHONE, Constants.getPhone());
        prefsEditor.putString(Constants.KEY_PW, Constants.getPasswort());
        prefsEditor.commit();
    }

}