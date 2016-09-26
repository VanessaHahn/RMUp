package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private EditText cm,kg,phone;
    private Button speichern;
    private Button löschen;
    private TextView showCm, showKg, showPhone;
    private int inputCm,inputKg;

    SharedPreferences prefs;
    SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cm = (EditText) findViewById(R.id.input_cm);
        kg = (EditText) findViewById(R.id.input_kg);
        phone = (EditText) findViewById(R.id.input_phone);

        prefs = this.getSharedPreferences("Settings",MODE_PRIVATE);
        prefsEditor = prefs.edit();
        initButton();
        showCm = (TextView) findViewById(R.id.cmSettings);
        showKg = (TextView) findViewById(R.id.kgSettings);
        showPhone = (TextView) findViewById(R.id.phoneSettings);
        String inputCM = prefs.getString(Constants.KEYCM,"---");
        String inputKG = prefs.getString(Constants.KEYKG,"---");
        String inputPhone = prefs.getString(Constants.KEYPHONE,"---");
        showCm.setText(inputCM+" cm");
        showKg.setText(inputKG+" kg");
        showPhone.setText(inputPhone);
    }

    private void initButton(){
        speichern = (Button) findViewById(R.id.button);
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cm.getText().length()>0){
                    String inputcm = cm.getText().toString();
                    prefsEditor.putString(Constants.KEYCM,inputcm);
                    prefsEditor.commit();
                    inputCm = Integer.parseInt(cm.getText().toString());

                } else {
                    if(TextUtils.isEmpty(cm.getText().toString())){
                        cm.setError("fehlende Eingabe");
                    }
                }

                if(kg.getText().length()>0){
                    String inputkg = kg.getText().toString();
                    prefsEditor.putString(Constants.KEYKG,inputkg);
                    prefsEditor.commit();
                    inputKg = Integer.parseInt(kg.getText().toString());

                } else {
                    if(TextUtils.isEmpty(kg.getText().toString())){
                        kg.setError("fehlende Eingabe");
                    }
                }
                if(phone.getText().length()>0){
                    String inputphone = phone.getText().toString();
                    prefsEditor.putString(Constants.KEYPHONE,inputphone);
                    prefsEditor.commit();

                } else {
                    if(TextUtils.isEmpty(phone.getText().toString())){
                        phone.setError("fehlende Eingabe");
                    }
                }

                CalculatorBmi calc = new CalculatorBmi();
                calc.setValues(inputCm,inputKg);
                double BMI = calc.calculateBMI();
                String text = BMIText(BMI);

                prefsEditor.putString(Constants.KEYBMITEXT,text);
                prefsEditor.putString(Constants.KEYBMI,""+BMI);
                prefsEditor.commit();

                Toast toast = Toast.makeText(Settings.this,"Eingaben gespeichert",Toast.LENGTH_SHORT);
                toast.show();

            }
        });


        löschen = (Button) findViewById(R.id.buttonlöschen);
        löschen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefsEditor.remove(Constants.KEYCM);
                prefsEditor.remove(Constants.KEYKG);
                prefsEditor.remove(Constants.KEYPHONE);
                prefsEditor.remove(Constants.KEYBMI);
                prefsEditor.remove(Constants.KEYBMITEXT);
                prefsEditor.commit();
                showCm.setText(null);
                showKg.setText(null);
                showPhone.setText(null);
                Toast toast = Toast.makeText(Settings.this,"Eingaben gelöscht",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    private String BMIText(double BMI){
        String text = "";
        if (BMI < 19) {
            text = "Untergewicht!";
        } else if (BMI >= 19 && BMI <= 24) {
            text = "Normalgewicht!";
        } else if (BMI > 24 && BMI <= 29) {
            text = "Übergewicht!";
        } else if (BMI > 29 && BMI <= 39) {
            text = "Adipositas";
        } else if (BMI > 39) {
            text = "starke Adipositas!";
        }
        return text;
    }
}
