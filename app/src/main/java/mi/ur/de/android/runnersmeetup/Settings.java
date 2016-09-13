package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    private EditText inputCm, inputKg, inputPhone;
    private Spinner spinner;
    private int cm = 180, kg = 77;
    private String gender = "männlich", phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initSpinner();
        initButton();
    }

    private void initSpinner(){
        spinner = (Spinner) findViewById(R.id.gender_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender_options,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initButton(){
        inputCm = (EditText) findViewById(R.id.input_cm);
        inputKg = (EditText) findViewById(R.id.input_kg);
        inputPhone = (EditText) findViewById(R.id.input_phone);

        Button storeButton = (Button) findViewById(R.id.store_button);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initToast();
                readValues();
            }
        });
    }

    private void initToast(){
        String text = "Profil gespeichert!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this,text,duration);
        toast.show();
    }

    private void readValues(){
        kg = Integer.parseInt(inputKg.getText().toString());
        cm = Integer.parseInt(inputCm.getText().toString());
        phone = inputPhone.getText().toString();
        setValues(gender,cm,kg,phone);
    }

    public void setValues(String gender, int cm, int kg, String phone){
        Constants.setValues(gender,cm,kg,phone);
    }

}
