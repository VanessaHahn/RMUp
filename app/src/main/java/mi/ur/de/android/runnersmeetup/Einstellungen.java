package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Einstellungen extends AppCompatActivity {

    private Button profilbildÄndern, einstellungenSpeichern;
    private EditText nameÄndern, telefonÄndern, emailÄndern, passwortÄndern, groesseÄndern, gewichtÄndern;

    String picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        nameÄndern = (EditText) findViewById(R.id.profil_name_ändern);
        telefonÄndern = (EditText) findViewById(R.id.profil_phone_ändern);
        emailÄndern = (EditText) findViewById(R.id.profil_email_ändern);
        passwortÄndern = (EditText) findViewById(R.id.profil_pw_ändern);
        groesseÄndern = (EditText) findViewById(R.id.Profil_größe);
        gewichtÄndern = (EditText) findViewById(R.id.Profil_gewicht);

        nameÄndern.setText(Constants.getName());
        telefonÄndern.setText(Constants.getPhone());
        emailÄndern.setText(Constants.getEmail());
        passwortÄndern.setText(Constants.getPasswort());
        groesseÄndern.setText("" + Constants.getGroesse());
        gewichtÄndern.setText("" + Constants.getGewicht());

        einstellungenSpeichern = (Button) findViewById(R.id.einstellungen_button_speichern);
        einstellungenSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();

                SharedPreferences prefs = getSharedPreferences("LoginData",MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                prefsEditor.putInt(Constants.KEY_ID, Constants.getId());
                prefsEditor.putString(Constants.KEY_NAME, Constants.getName());
                prefsEditor.putFloat(Constants.KEY_WEIGHT, Constants.getGewicht());
                prefsEditor.putInt(Constants.KEY_SIZE, Constants.getGroesse());
                prefsEditor.putString(Constants.KEY_EMAIL, Constants.getEmail());
                prefsEditor.putString(Constants.KEY_PHONE, Constants.getPhone());
                prefsEditor.putString(Constants.KEY_PW, Constants.getPasswort());
                prefsEditor.commit();
            }
        });

    }

    private void updateData() {
        int cm = Integer.parseInt(groesseÄndern.getText().toString());
        float kg = Float.parseFloat(gewichtÄndern.getText().toString());
        String email = emailÄndern.getText().toString();
        String regex = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_-]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$";
        if (nameÄndern.getText().length() != 0
                && emailÄndern.getText().length() != 0
                && passwortÄndern.getText().length() != 0
                && (email.matches(regex))
                && (cm > 0 && cm <= 220)
                && (kg > 0.0 && kg <= 300.0)) {
            onUpdate();
        } else {
            if (!(cm > 0 && cm <= 220)) {
                groesseÄndern.setError("Ungültige Eingabe!");
            }
            if (!(kg > 0.0 && kg <= 300.0)) {
                gewichtÄndern.setError("Ungültige Eingabe!");
            }
            if (!(email.matches(regex))) {
                emailÄndern.setError("Keine korrekte Email!");
            }
            if (nameÄndern.getText().length() == 0) {
                nameÄndern.setError("fehlende Eingabe!");
            }
            if (TextUtils.isEmpty(emailÄndern.getText())) {
                emailÄndern.setError("fehlende Eingabe!");
            }
            if (passwortÄndern.getText().length() == 0) {
                passwortÄndern.setError("fehlende Eingabe!");
            }
        }}


    private void onUpdate() {
        String id = "" + Constants.getId();
        String name = nameÄndern.getText().toString();
        String groesse = groesseÄndern.getText().toString();
        String gewicht = gewichtÄndern.getText().toString();
        String email = emailÄndern.getText().toString();
        String phone = telefonÄndern.getText().toString();
        String password = passwortÄndern.getText().toString();
        String type = "updateData";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, id, name, groesse, gewicht, email, phone, password);

        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);
            Log.d("returnAsyncTaskResult",""+bool);

            if(bool.equals("Benutzername vergeben")){
                nameÄndern.setError("Benutzername vergeben!");
            }

            if(bool.equals("true")){
                Constants.setName(name);
                Constants.setPasswort(password);
                Constants.setEmail(email);
                Constants.setGewicht(Float.parseFloat(gewicht));
                Constants.setGroesse(Integer.parseInt(groesse));
                Constants.setPhone(phone);
                Toast.makeText(this,"Update Data!",Toast.LENGTH_SHORT).show();
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
