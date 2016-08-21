package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class Profil extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private EditText inputCm, inputKg, inputName;
    private ImageButton buttonCalcBMI;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        initSpinner();
        initButton();
    }

    private void initSpinner(){
        spinner = (Spinner) findViewById(R.id.gender_spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.gender_options,android.R.layout.simple_spinner_item);
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
        inputName = (EditText) findViewById(R.id.input_name);
        inputCm = (EditText) findViewById(R.id.input_cm);
        inputKg = (EditText) findViewById(R.id.input_kg);

        buttonCalcBMI = (ImageButton) findViewById(R.id.button_bmi);
        buttonCalcBMI.setOnClickListener(new View.OnClickListener() {
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
        String name = inputName.getText().toString();
        int kg = Integer.parseInt(inputKg.getText().toString());
        int cm = Integer.parseInt(inputCm.getText().toString());

        Intent i = new Intent(this, Trainingsuebersicht.class);
        i.putExtra(Constants.KEY_CM, cm);
        i.putExtra(Constants.KEY_KG, kg);
        i.putExtra(Constants.KEY_NAME, name);
        i.putExtra(Constants.KEY_GENDER, gender);

        startActivity(i);
    }

}
