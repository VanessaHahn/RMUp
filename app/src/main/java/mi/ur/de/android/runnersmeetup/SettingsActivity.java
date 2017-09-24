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

public class SettingsActivity extends AppCompatActivity {

    private Button store;
    private EditText changeName, changePhone, changeMail, oldPassword, newPassword1, newPassword2, changeSize, changeWeight;
    private String username, phone, email, oldPw, password1, password2, cm, kg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeName = (EditText) findViewById(R.id.change_name);
        changePhone = (EditText) findViewById(R.id.change_phone);
        changeMail = (EditText) findViewById(R.id.change_mail);
        oldPassword = (EditText) findViewById(R.id.old_password);
        newPassword1 = (EditText) findViewById(R.id.new_password1);
        newPassword2 = (EditText) findViewById(R.id.new_password2);
        changeSize = (EditText) findViewById(R.id.change_size);
        changeWeight = (EditText) findViewById(R.id.change_weight);

        changeName.setText(Constants.getName());
        changePhone.setText(Constants.getPhone());
        changeMail.setText(Constants.getEmail());
        changeSize.setText("" + Constants.getSize());
        changeWeight.setText("" + Constants.getWeight());

        store = (Button) findViewById(R.id.storeSettings);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
                putSharedPreferences();
            }
        });
    }

    private void putSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("LoginData",MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(Constants.KEY_ID, Constants.getId());
        prefsEditor.putString(Constants.KEY_NAME, Constants.getName());
        prefsEditor.putFloat(Constants.KEY_WEIGHT, Constants.getWeight());
        prefsEditor.putInt(Constants.KEY_SIZE, Constants.getSize());
        prefsEditor.putString(Constants.KEY_EMAIL, Constants.getEmail());
        prefsEditor.putString(Constants.KEY_PHONE, Constants.getPhone());
        prefsEditor.putString(Constants.KEY_PW, Constants.getPasswort());
        prefsEditor.commit();
    }

    private void updateData() {
        username = changeName.getText().toString();
        String username_regex = "[a-zA-Z0-9]{1,20}";
        phone = changePhone.getText().toString();
        String phone_regex = "[0-9]*";
        cm = changeSize.getText().toString();
        String cm_regex = "[1-2][0-9]{1,2}";
        kg = changeWeight.getText().toString();
        String kg_regex = "[0-9]{2,3}[.][0-9]";
        oldPw = oldPassword.getText().toString();
        password1 = newPassword1.getText().toString();
        password2 = newPassword2.getText().toString();
        if(oldPw.length() == 0 || password1.length() == 0) {
            oldPw = Constants.getPasswort();
            password1 = Constants.getPasswort();
            password2 = Constants.getPasswort();
        }
        String passwort_regex = "[a-zA-Z0-9]*";
        email = changeMail.getText().toString();
        String mail_regex = "^[^0-9][a-zA-Z0-9_]+([.-][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_-]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$";
        if (username.length() != 0
                && (username.matches(username_regex))
                && (phone.matches(phone_regex))
                && (cm.matches(cm_regex))
                && (email.length() != 0)
                && (oldPassword.length() != 0)
                && password1.length() != 0
                && password2.length() != 0
                && oldPw.length() != 0
                && (email.matches(mail_regex))
                && (oldPw.equals(Constants.getPasswort()))
                && password1.matches(passwort_regex)
                && password1.equals(password2)
                && (kg.matches(kg_regex))) {
            onUpdate();
        } else {
            if (!(cm.matches(cm_regex))) {
                changeSize.setError(getString(R.string.forbiddenInput));
            }
            if(!(kg.matches(kg_regex))){
                changeWeight.setError(getString(R.string.forbiddenInput));
            }
            if (!(email.matches(mail_regex))) {
                changeMail.setError(getString(R.string.forbiddenMail));
            }
            if (username.length() == 0) {
                changeName.setError(getString(R.string.missedInput));
            }
            if(!(username.matches(username_regex))){
                changeName.setError(getString(R.string.withoutSpecialChars));
            }
            if(!(phone.matches(phone_regex))){
                changePhone.setError(getString(R.string.withoutSpecialChars));
            }
            if (TextUtils.isEmpty(changeMail.getText())) {
                changeMail.setError(getString(R.string.missedInput));
            }
            if (oldPw.length() == 0) {
                oldPassword.setError(getString(R.string.missedInput));
            }
            if (!(oldPw.equals(Constants.getPasswort()))){
                oldPassword.setError(getString(R.string.wrongPassword));
            }
            if(!(password1.matches(passwort_regex))){
                newPassword1.setError(getString(R.string.withoutSpecialChars));
            }
            if (newPassword1.getText().length() == 0) {
                newPassword1.setError(getString(R.string.missedInput));
            }
            if (newPassword2.getText().length() == 0) {
                newPassword2.setError(getString(R.string.missedInput));
            }
            if(!(password1.equals(password2))){
                newPassword2.setError(getString(R.string.notIdenticalPasswords));
            }
        }}


    private void onUpdate() {
        String id = "" + Constants.getId();
        String type = "updateData";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute(type, id, username, cm, kg, email, phone, password1);

        try {
            String bool = String.valueOf(returnAsyncTask.get()[1]);
            Log.d("returnAsyncTaskResult",""+bool);
            if(bool.equals("Benutzername vergeben")){
                changeName.setError("Benutzername vergeben!");
            }
            if(bool.equals("true")){
                Constants.setName(username);
                Constants.setPasswort(password1);
                Constants.setEmail(email);
                Constants.setGewicht(Float.parseFloat(kg));
                Constants.setGroesse(Integer.parseInt(cm));
                Constants.setPhone(phone);
                Toast.makeText(this,"Update Data!",Toast.LENGTH_SHORT).show();
            }else{
                // Not successful
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Not successful
        } catch (ExecutionException e) {
            e.printStackTrace();
            // Not successful
        }
    }
}
