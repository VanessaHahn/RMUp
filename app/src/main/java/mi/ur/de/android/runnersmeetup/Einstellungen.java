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
    private EditText nameÄndern, telefonÄndern, emailÄndern, altesPasswort, passwortÄndern1, passwortÄndern2, groesseÄndern, gewichtÄndern;

    String picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameÄndern = (EditText) findViewById(R.id.profil_name_ändern);
        telefonÄndern = (EditText) findViewById(R.id.profil_phone_ändern);
        emailÄndern = (EditText) findViewById(R.id.profil_email_ändern);
        altesPasswort = (EditText) findViewById(R.id.profil_altes_pw);
        passwortÄndern1 = (EditText) findViewById(R.id.profil_pw_ändern1);
        passwortÄndern2 = (EditText) findViewById(R.id.profil_pw_ändern2);
        groesseÄndern = (EditText) findViewById(R.id.Profil_größe);
        gewichtÄndern = (EditText) findViewById(R.id.Profil_gewicht);

        nameÄndern.setText(Constants.getName());
        telefonÄndern.setText(Constants.getPhone());
        emailÄndern.setText(Constants.getEmail());
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
        String username = nameÄndern.getText().toString();
        String username_regex = "[a-zA-Z0-9]{1,20}";
        String phone = telefonÄndern.getText().toString();
        String phone_regex = "[0-9]*";
        String cm = groesseÄndern.getText().toString();
        String cm_regex = "[1-2][0-9]{1,2}";
        String kg_string = gewichtÄndern.getText().toString();
        String kg_regex = "[0-9]{2,3}[.][0-9]";
        String altespw = altesPasswort.getText().toString();
        String pw1 = passwortÄndern1.getText().toString();
        String pw2 = passwortÄndern2.getText().toString();
        String passwort_regex = "[a-zA-Z0-9]*";
        String email = emailÄndern.getText().toString();
        String regex = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_-]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$";
        if (username.length() != 0
                && (username.matches(username_regex))
                && (phone.matches(phone_regex))
                && (cm.matches(cm_regex))
                && (email.length() != 0)
                && (altespw.length() != 0)
                && pw1.length() != 0
                && pw2.length() != 0
                && altespw.length() != 0
                && (email.matches(regex))
                && (altespw.equals(Constants.getPasswort()))
                && pw1.matches(passwort_regex)
                && pw1.equals(pw2)
                && (kg_string.matches(kg_regex))) {
            onUpdate();
        } else {
            if (!(cm.matches(cm_regex))) {
                groesseÄndern.setError("Ungültige Eingabe!");
            }
            if(!(kg_string.matches(kg_regex))){
                gewichtÄndern.setError("Ungültige Eingabe!");
            }
            if (!(email.matches(regex))) {
                emailÄndern.setError("Keine korrekte Email!");
            }
            if (username.length() == 0) {
                nameÄndern.setError("fehlende Eingabe!");
            }
            if(!(username.matches(username_regex))){
                nameÄndern.setError("Ungültige Eingabe! Keine Sonderzeichen!");
            }
            if(!(phone.matches(phone_regex))){
                telefonÄndern.setError("Ungültige Eingabe! Ohne Sonderzeichen!");
            }
            if (TextUtils.isEmpty(emailÄndern.getText())) {
                emailÄndern.setError("fehlende Eingabe!");
            }
            if (altespw.length() == 0) {
                altesPasswort.setError("fehlende Eingabe!");
            }
            if (!(altespw.equals(Constants.getPasswort()))){
                altesPasswort.setError("falsches Passwort!");
            }
            if(!(pw1.matches(passwort_regex))){
                passwortÄndern1.setError("Ungültige Eingabe! Ohne Sonderzeichen!");
            }
            if (passwortÄndern1.getText().length() == 0) {
                passwortÄndern1.setError("fehlende Eingabe!");
            }
            if (passwortÄndern2.getText().length() == 0) {
                passwortÄndern2.setError("fehlende Eingabe!");
            }
            if(!(pw1.equals(pw2))){
                passwortÄndern2.setError("Passwörter nicht identisch!");
            }
        }}


    private void onUpdate() {
        String id = "" + Constants.getId();
        String name = nameÄndern.getText().toString();
        String groesse = groesseÄndern.getText().toString();
        String gewicht = gewichtÄndern.getText().toString();
        String email = emailÄndern.getText().toString();
        String phone = telefonÄndern.getText().toString();
        String password = passwortÄndern1.getText().toString();
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
