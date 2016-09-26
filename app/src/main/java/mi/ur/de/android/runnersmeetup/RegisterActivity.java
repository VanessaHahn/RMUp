package mi.ur.de.android.runnersmeetup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputName, inputPassword,inputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputPhone = (EditText) findViewById(R.id.input_phone);

        initButton();
    }

    private void initButton() {
        Button registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReg();
            }
        });
    }


    public void onReg(){
            String name = inputName.getText().toString();
            String password = inputPassword.getText().toString();
            String phone = inputPhone.getText().toString();

            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, name, password, phone);
            //Constants.setValues(gender,size,weight,phone);
    }
}

















