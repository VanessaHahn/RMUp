package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
    private String name;
    private String password1;
    private String password2;
    private String day;
    private String month;
    private String year;
    private String cm;
    private String gender;
    private String kg;
    private String email;
    private String phone;

    private static final String KG_REGEX = "[0-9]{2,3}[.][0-9]";
    private static final String MAIL_REGEX = "^[^0-9][a-zA-Z0-9_]+([.-][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_-]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$";
    private static final String USER_REGEX = "[a-zA-Z0-9]{1,20}";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]*";
    private static final String PHONE_REGEX = "[0-9]*";
    private static final String CM_REGEX = "[1-2][0-9]{1,2}";
    private static final String DAY_REGEX = "[0-3]?[0-9]";
    private static final String MONTH_REGEX = "[1,2,3,4,5,6,7,8,9,10,11,12]";
    private static final String YEAR_REGEX = "[1-2][0,9][0-9][0-9]";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    private void initLayout() {
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
        initSpinner();
        initButton();
    }

    private void getInput() {
        name = inputName.getText().toString();
        password1 = inputPassword1.getText().toString();
        password2 = inputPassword2.getText().toString();
        day = inputDay.getText().toString();
        month = inputMonth.getText().toString();
        year = inputYear.getText().toString();
        cm = inputCm.getText().toString();
        gender = spinner.getSelectedItem().toString();
        kg = inputKg.getText().toString();
        email = inputEmail.getText().toString();
        phone = inputPhone.getText().toString();
    }

    private void initSpinner() {
        spinner = (Spinner) findViewById(R.id.register_gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void initButton() {
        Button registerButton = (Button) findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
                checkInput();
            }
        });
    }

    private void checkInput() {
        if(inputName.getText().length() != 0
                && inputDay.getText().length() != 0
                && inputMonth.getText().length() != 0
                && inputYear.getText().length() != 0
                && inputEmail.getText().length() != 0
                && inputPassword1.getText().length() != 0
                && inputPassword2.getText().length() != 0
                && inputName.getText().length() != 0
                && (name.matches(USER_REGEX))
                && (password1.matches(PASSWORD_REGEX))
                && (password2.matches(PASSWORD_REGEX))
                && (phone.matches(PHONE_REGEX))
                && (email.matches(MAIL_REGEX))
                && password1.equals(password2)
                && (day.matches(DAY_REGEX))
                && (month.matches(MONTH_REGEX))
                && (year.matches(YEAR_REGEX))
                && (cm.matches(CM_REGEX))
                && (!gender.equals(getString(R.string.noGender)))
                && (kg.matches(KG_REGEX))){
            onReg();
        } else {
            if(!(cm.matches(CM_REGEX))){
                inputCm.setError(getString(R.string.forbiddenInput));
            }
            if(!(kg.matches(KG_REGEX))){
                inputKg.setError("Ungültige Eingabe! Kg in Dezimal: z.B. 70.0 kg");
            }
            if(gender.equals(getString(R.string.noGender))){
                TextView errorText = (TextView)spinner.getSelectedView();
                errorText.setError(getString(R.string.forbiddenInput));
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
            }
            if(!(day.matches(DAY_REGEX))){
                inputDay.setError(getString(R.string.forbiddenInput));
            }
            if(!(month.matches(MONTH_REGEX))){
                inputMonth.setError(getString(R.string.forbiddenInput));
            }
            if(!(year.matches(YEAR_REGEX))){
                inputYear.setError(getString(R.string.forbiddenInput));
            }
            if(!(name.matches(USER_REGEX))){
                inputName.setError("Ungültige Eingabe! Keine Sonderzeichen!");
            }
            if(!email.matches(MAIL_REGEX)){
                inputEmail.setError(getString(R.string.forbiddenMail));
            }
            if(!password1.equals(password2)){
                inputPassword2.setError(getString(R.string.notIdenticalPasswords));
            }
            if(!(phone.matches(PHONE_REGEX))){
                inputPhone.setError("Ungültige Eingabe! Ohne Sonderzeichen!");
            }
            checkInputMissed(inputName);
            checkInputMissed(inputDay);
            checkInputMissed(inputMonth);
            checkInputMissed(inputYear);
            checkInputMissed(inputPassword1);
            checkInputMissed(inputPassword2);
            if(TextUtils.isEmpty(inputEmail.getText())){
                inputEmail.setError(getString(R.string.missedInput));
            }
            if(!(password1.matches(PASSWORD_REGEX))){
                inputPassword1.setError(getString(R.string.withoutSpecialChars));
            }
            if(!(password2.matches(PASSWORD_REGEX))){
                inputPassword2.setError(getString(R.string.withoutSpecialChars));
            }
        }
    }

    public void checkInputMissed(EditText input) {
        if(input.getText().length() == 0){
            input.setError(getString(R.string.missedInput));
        }
    }

    public void onReg(){
        String date = ""+year+"."+month+"."+day;
        String type = getString(R.string.register);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, name, gender, date, String.valueOf(cm), kg, email, phone, password1);
        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);
            Log.d("returnAsyncTaskResult",""+bool);

            if(bool.equals("Benutzername vergeben")){
                inputName.setError("Benutzername vergeben!");
            }

            if(bool.equals("true")){
                Toast.makeText(this,"Registration successful",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginActivity.class));
            }else{
                Log.d("RegisterActivity", "Registration failed!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("RegisterActivity", "Registration  failed!");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d("RegisterActivity", "Registration  failed!");
        }
    }
}