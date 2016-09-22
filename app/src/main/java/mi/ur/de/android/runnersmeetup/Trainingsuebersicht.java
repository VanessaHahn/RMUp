package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Trainingsuebersicht extends AppCompatActivity {

    private TextView resultBMI, resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsuebersicht);
        resultBMI = (TextView) findViewById(R.id.result_text);
        resultText = (TextView) findViewById(R.id.result_bmi);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if(extras!=null) {
            int cm = extras.getInt(Constants.KEYCM);
            int kg = extras.getInt(Constants.KEYKG);
            //BMI
            CalculatorBmi calc = new CalculatorBmi();
            calc.setValues(cm, kg);
            double BMI = calc.calculateBMI();
            resultBMI.setText(BMI + " ");
            setText(BMI);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences mySPR = getSharedPreferences("MySPFILE",0);
        SharedPreferences.Editor editor = mySPR.edit();
        editor.putString("KEY_CM", resultBMI.getText().toString());
        editor.putString("KEY_KG", resultText.getText().toString());
        editor.commit();
    }

    private void setText(double BMI){
        if (BMI < 19) {
            resultText.setText("Untergewicht!");
        }
        if (BMI >= 19 && BMI <= 24) {
            resultText.setText("Normalgewicht!");
        }
        if (BMI > 24 && BMI <= 29) {
            resultText.setText("Übergewicht!");
        }
        if (BMI > 29 && BMI <= 39) {
            resultText.setText("Adipositas!");
        }
        if (BMI > 39) {
            resultText.setText("starke Adipositas!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.gps_icon:
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                return true;

            case R.id.music_icon:
                Intent ii = new Intent(MediaStore.INTENT_ACTION_MUSIC_PLAYER);
                startActivityForResult(ii,1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
