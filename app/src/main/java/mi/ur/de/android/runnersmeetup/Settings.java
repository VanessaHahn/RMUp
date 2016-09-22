package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
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

    private EditText cm,kg,phone;
    private Button speichern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cm = (EditText) findViewById(R.id.input_cm);
        kg = (EditText) findViewById(R.id.input_kg);
        phone = (EditText) findViewById(R.id.input_phone);
        SharedPreferences mySPR = getSharedPreferences("MySPFILE",0);
        cm.setText(mySPR.getString("KEY_CM",""));
        kg.setText(mySPR.getString("KEY_KG",""));
        phone.setText(mySPR.getString("KEY_PHONE",""));
        initButton();
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences mySPR = getSharedPreferences("MySPFILE",0);
        SharedPreferences.Editor editor = mySPR.edit();
        editor.putString("KEY_CM", cm.getText().toString());
        editor.putString("KEY_KG", kg.getText().toString());
        editor.putString("KEY_PHONE",phone.getText().toString());
        editor.commit();
    }

    private void initButton(){
        speichern = (Button) findViewById(R.id.button);
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputCm = Integer.parseInt(cm.getText().toString());
                int inputKg = Integer.parseInt(kg.getText().toString());
                Intent i = new Intent(Settings.this, Trainingsuebersicht.class);
                i.putExtra(Constants.KEYCM,inputCm);
                i.putExtra(Constants.KEYKG,inputKg);
                startActivity(i);
            }
        });
    }

}
