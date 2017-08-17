package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    private EditText inputName, inputDay, inputMonth, inputYear, inputCm, inputKg, inputEmail, inputPhone, inputPassword1, inputPassword2;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        inputName = (EditText) findViewById(R.id.input_register_name);
        inputDay = (EditText) findViewById(R.id.input_register_day);
        inputMonth = (EditText) findViewById(R.id.input_register_month);
        inputYear = (EditText) findViewById(R.id.input_register_year);
        inputKg = (EditText) findViewById(R.id.input_register_kg);
        inputCm = (EditText) findViewById(R.id.input_register_cm);
        inputEmail = (EditText) findViewById(R.id.input_register_email);
        inputPhone = (EditText) findViewById(R.id.input_register_phone);
        inputPassword1 = (EditText) findViewById(R.id.input_register_pw1);
        inputPassword2 = (EditText) findViewById(R.id.input_register_pw2);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initButton();
    }

    private void initButton() {
        Button registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = inputPassword1.getText().toString();
                String password2 = inputPassword2.getText().toString();

                if(inputName.getText().length() != 0
                        && inputDay.getText().length() != 0
                        && inputMonth.getText().length() != 0
                        && inputYear.getText().length() != 0
                        && inputEmail.getText().length() != 0
                        && inputPassword1.getText().length() != 0
                        && inputPassword2.getText().length() != 0){
                    if(password1.equals(password2)){
                        onReg();
                    } else {
                        inputPassword2.setError("Passwörter nicht identisch!");
                    }
                } else {
                    if(inputName.getText().length() == 0){
                        inputName.setError("fehlende Eingabe!");
                    }
                    if(inputDay.getText().length() == 0){
                        inputDay.setError("fehlende Eingabe!");
                    }
                    if(inputMonth.getText().length() == 0){
                        inputMonth.setError("fehlende Eingabe!");
                    }
                    if(inputYear.getText().length() == 0){
                        inputYear.setError("fehlende Eingabe!");
                    }
                    if(TextUtils.isEmpty(inputEmail.getText())){
                        inputEmail.setError("fehlende Eingabe!");
                    }
                    if(inputPassword1.getText().length() == 0){
                        inputPassword1.setError("fehlende Eingabe!");
                    }
                    if(inputPassword2.getText().length() == 0){
                        inputPassword2.setError("fehlende Eingabe!");
                    }
                }
            }
        });
    }

    public void onReg(){
        String name = inputName.getText().toString();
        String geschlecht = spinner.getSelectedItem().toString();
        String day = inputDay.getText().toString();
        String month = inputMonth.getText().toString();
        String year = inputYear.getText().toString();
        String date = ""+year+"."+month+"."+day;
        String cm = inputCm.getText().toString();
        String kg = inputKg.getText().toString();
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String password1 = inputPassword1.getText().toString();
        String type = "register";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, name, geschlecht, date, cm, kg, email, phone, password1);
        //Constants.setValues(gender,size,weight,phone);

        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);

            if(bool.equals("Benutzername vergeben")){
                inputName.setError("Benutzername vergeben!");
            }

            if(bool.equals("true")){
                Toast.makeText(this,"Registration successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginActivity.class));
            }else{
                // Not successful
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
            Log.d("RegisterActivity", "Registration  failed!");
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
            Log.d("RegisterActivity", "Registration  failed!");
        }
    }
}

















