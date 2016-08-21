package mi.ur.de.android.runnersmeetup;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Trainingsuebersicht extends AppCompatActivity {

    private TextView resultBMI, resultText, showName;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingsuebersicht);
        image = (ImageView) findViewById(R.id.showImage);
        showName = (TextView) findViewById(R.id.show_name);
        resultBMI = (TextView) findViewById(R.id.result_text);
        resultText = (TextView) findViewById(R.id.result_bmi);
        getExtras();
    }
    private void getExtras(){
        int cm = getIntent().getExtras().getInt(Constants.KEY_CM);
        int kg = getIntent().getExtras().getInt(Constants.KEY_KG);
        String name = getIntent().getExtras().getString(Constants.KEY_NAME);
        String gender = getIntent().getExtras().getString(Constants.KEY_GENDER);
        showResults(cm, kg, name, gender);
    }

    private void showResults(int cm, int kg, String name, String gender){
        CalculatorBmi calc = new CalculatorBmi();
        calc.setValues(cm, kg);
        double BMI = calc.calculateBMI();
        showName.setText(name + " ");
        resultBMI.setText(BMI + " ");

        switch (gender){
            case ("weiblich"):
                bmiWeiblich(BMI);
                break;
            case ("männlich"):
                bmiMännlich(BMI);
                break;
        }
    }

    private void bmiWeiblich(double BMI) {
        if (BMI < 19){
            resultText.setText("Untergewicht!");
            image.setImageResource(R.drawable.daumen_runter);
        } if (BMI >= 19 && BMI <= 24){
            resultText.setText("Normalgewicht!");
            image.setImageResource(R.drawable.daumen_hoch);
        } if (BMI > 24 && BMI <= 29){
            resultText.setText("Übergewicht!");
            image.setImageResource(R.drawable.daumen_naja);
        } if (BMI > 29 && BMI <= 39){
            resultText.setText("Adipositas!");
            image.setImageResource(R.drawable.daumen_runter);
        } if (BMI > 39){
            resultText.setText("starke Adipositas!");
            image.setImageResource(R.drawable.daumen_runter);
        }
    }

    private void bmiMännlich(double BMI) {
        if (BMI < 20){
            resultText.setText("Untergewicht!");
            image.setImageResource(R.drawable.daumen_runter);
        } if (BMI >= 20 && BMI <= 25){
            resultText.setText("Normalgewicht!");
            image.setImageResource(R.drawable.daumen_hoch);
        } if (BMI > 25 && BMI <= 29){
            resultText.setText("Übergewicht!");
            image.setImageResource(R.drawable.daumen_naja);
        } if (BMI > 29 && BMI <= 39){
            resultText.setText("Adipositas!");
            image.setImageResource(R.drawable.daumen_runter);
        } if (BMI > 39){
            resultText.setText("starke Adipositas!");
            image.setImageResource(R.drawable.daumen_runter);
        }
    }



}
