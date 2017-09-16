package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

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
                int day = Integer.parseInt(inputDay.getText().toString());
                int month = Integer.parseInt(inputMonth.getText().toString());
                int year = Integer.parseInt(inputYear.getText().toString());
                int cm = Integer.parseInt(inputCm.getText().toString());
                String geschlecht = spinner.getSelectedItem().toString();
                String kg = inputKg.getText().toString();
                String kg_regex = "[0-9]{2,3}[.][0-9]";
                String email = inputEmail.getText().toString();
                String email_regex = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_-]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$";
                if(inputName.getText().length() != 0
                        && inputDay.getText().length() != 0
                        && inputMonth.getText().length() != 0
                        && inputYear.getText().length() != 0
                        && inputEmail.getText().length() != 0
                        && inputPassword1.getText().length() != 0
                        && inputPassword2.getText().length() != 0
                        && (email.matches(email_regex))
                        && password1.equals(password2)
                        && (day>0 && day<=31)
                        && (month>0 && month<=12)
                        && (year>1917 && year<2017)
                        && (cm>0 && cm<=220)
                        && (!(geschlecht.equals("---")))
                        && (kg.matches(kg_regex))){
                    onReg();
                } else {
                    if(!(cm>0 && cm<=220)){
                        inputCm.setError("Ungültige Eingabe!");
                    }
                    if(!(kg.matches(kg_regex))){
                        inputKg.setError("Ungültige Eingabe!");
                    }
                    if(geschlecht.equals("---")){
                        TextView errorText = (TextView)spinner.getSelectedView();
                        errorText.setError("Ungültige Eingabe");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    }
                    if(!(day>0 && day<=31)){
                        inputDay.setError("Ungültige Eingabe!");
                    }
                    if(!(month>0 && month<=12)){
                        inputMonth.setError("Ungültige Eingabe!");
                    }
                    if(!(year>1917 && year<2017)){
                        inputYear.setError("Ungültige Eingabe!");
                    }
                    if(!(email.matches(email_regex))){
                        inputEmail.setError("Keine korrekte Email!");
                    }
                    if(!(password1.equals(password2))){
                        inputPassword2.setError("Passwörter nicht identisch!");
                    }
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
        kg = kg.replace(".",",");
        String email = inputEmail.getText().toString();
        String phone = inputPhone.getText().toString();
        String password1 = inputPassword1.getText().toString();
        String picture = "";
        String type = "register";
        Log.d("picture in string", ""+picture);

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, picture, name, geschlecht, date, cm, kg, email, phone, password1);
        //Constants.setValues(gender,size,weight,phone);

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

















