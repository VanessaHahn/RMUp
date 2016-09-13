package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputName, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button_login:
                login();
                break;
            case R.id.button_register:
                openRegActivity();
                break;
        }
    }

    public void login(){
        String name = inputName.getText().toString();
        String password = inputPassword.getText().toString();
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, name, password);
    }

    public void openRegActivity(){
        startActivity(new Intent(this,RegisterActivity.class));
    }

}
