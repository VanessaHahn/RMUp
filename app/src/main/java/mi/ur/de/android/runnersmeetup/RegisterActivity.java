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

    private EditText inputName, inputPassword1, inputPassword2, inputSize, inputWeight, inputPhone;
    private Spinner inputGender;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName = (EditText) findViewById(R.id.input_name);
        inputPassword1 = (EditText) findViewById(R.id.input_password1);
        inputPassword2 = (EditText) findViewById(R.id.input_password2);
        inputSize = (EditText) findViewById(R.id.input_cm);
        inputWeight = (EditText) findViewById(R.id.input_kg);
        inputPhone = (EditText) findViewById(R.id.input_phone);
        initSpinner();
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

    private void initSpinner(){
        inputGender = (Spinner) findViewById(R.id.input_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_options,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inputGender.setAdapter(adapter);
        inputGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = inputGender.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onReg(){
        if(inputPassword1.getText().toString().equals(inputPassword2.getText().toString())) {
            String name = inputName.getText().toString();
            String password = inputPassword1.getText().toString();
            int size = Integer.parseInt(inputSize.getText().toString());
            int weight = Integer.parseInt(inputWeight.getText().toString());
            String phone = inputPhone.getText().toString();

            String type = "register";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, name, password, phone);
            Constants.setValues(gender,size,weight,phone);
        } else{
            Toast toast = new Toast(this);
            toast.makeText(this,"Passwörter stimmen nicht überein. Versuchen Sie es bitte noch einmal.",Toast.LENGTH_LONG);
            toast.show();
        }
    }
}

















