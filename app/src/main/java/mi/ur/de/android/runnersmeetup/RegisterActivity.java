package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.graphics.Bitmap;
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

    private Bitmap bitmap;

    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 1;
    private CircleImageView view;
    private Button bildändern;
    String pic = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDABALDA4MChAODQ4SERATGCgaGBYWGDEjJR0oOjM9PDkz\n" +
            "ODdASFxOQERXRTc4UG1RV19iZ2hnPk1xeXBkeFxlZ2P/2wBDARESEhgVGC8aGi9jQjhCY2NjY2Nj\n" +
            "Y2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2NjY2P/wAARCAIAAgADASIA\n" +
            "AhEBAxEB/8QAGwABAAIDAQEAAAAAAAAAAAAAAAECAwQFBgf/xABFEAABBAEDAgUCAwcCAwUIAwEB\n" +
            "AAIDEQQSITEFQQYTIlFhMnEUgZEjQlKhscHRFTMHYnIkU+Hw8SU0Y4KDkqKyFjZDRP/EABQBAQAA\n" +
            "AAAAAAAAAAAAAAAAAAD/xAAUEQEAAAAAAAAAAAAAAAAAAAAA/9oADAMBAAIRAxEAPwD5+iIgIiIC\n" +
            "IiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIg\n" +
            "IiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgIgFmguhh9E6jm0Yc\n" +
            "V+k/vOFBBz0XrMTwRO6jl5LIx3awWf1XXxvCHS4QDI18zh3e7+wQfPFniwsqf/ZxppP+lhK+oQdN\n" +
            "wsb/AGMWGM+4YFshoHAAQfMovD3Vpfpwnj/qIb/UraZ4S6q7mONn/U//AAvoaikHg2+DOon6psYf\n" +
            "/MT/AGWQeCsvvlQ/oV7ilFIPEHwVl9sqH9CsTvBnUR9M2Mf/AJiP7L3lKKQfPn+E+qs4ZG//AKX/\n" +
            "AOVrS+H+qxfVhvP/AEkO/oV9KUIPlUuHlQ/7uNMz/qYQsC+tloPItas/TsPJ/wB7GiefctCD5ci9\n" +
            "9keE+mzXoa+Enux39iuTleDJ22cXIa/2a8Uf1QeXRb+X0bqGHZmxn6R+80WFoHY0UBERAREQEREB\n" +
            "ERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEW/0zo2d1R9YsJLO8jtm\n" +
            "j817LpfgvDxqkzXHJk/h4YPy7oPEYXTszqD9GJjvlPcgbD7nsvTdP8DvNPz8gNH/AHce5/Ve0jij\n" +
            "hjEcTGsYOGtFAKyDm4XQ+nYAHk4zNX8b/Uf5roK1KKQVUUrUlIK0opXpRSCtJStSikFaSlakpBRQ\n" +
            "r0opBWlFKyUgrShXUUgqhU0oKCKXPzOi4GaD52O3V/E3YroqKQePzvBrxbsGcOH8Emx/VedzMDKw\n" +
            "X6cmB8Z7EjY/Yr6iqyRslYWSMa9h5a4WCg+TovcdS8JYuRb8Rxx5P4eWn8uy8p1DpOZ011ZERDez\n" +
            "27tP5oNJERAREQEREBERAREQEREBERAREQEREBERAREQEREBERAREQEREBERARF6ToHhHJ6jpny9\n" +
            "WPjHcWPU8fA7fdBw8LByc+cQ4sTpHn2HH3XtujeC4INMvUSJpOfLH0j7+69Jg9PxenQCHEhbGzvX\n" +
            "J+57rZQUjjZGwMY0NaNgAKAU0rVtuhprSXEAAWfsgppSlqQdYw5chkWujIQG1yssufBGY22NT3Vp\n" +
            "d2QZtKghTDLHOwmIhwaaLhwrlpocIMVJSvSgoK0opWpKKCqilakpBWlFK9KCEFaUUr0opBSkpXpR\n" +
            "SCiUrUopBWlBCtSIKUitSikFUU0lIK0ofG2Rpa9oc08gi1dSAg8t1vwpjmCXKwj5TmDUY/3T9vZe\n" +
            "Lc1zTThS9/1rxBDA2TExWiVxBa5/7rf8ryTGMkkaJRbLQcxFuZuA/GyHsZbwL49vdaaAiIgIiICI\n" +
            "iAiIgIiICIiAiIgIiICIiAiIgIiICIiAiIgLLjY82XO2HHjdJI40GtC2Ol9LyurZYx8Vlnlzjw0e\n" +
            "5X03oXQMXo2PUYD53D1ykbn7ewQcrw94PgwQ3Izw2bI5DOWs/wAleoVqUUghRStSmkENAJF8Lyfi\n" +
            "LqEuPkyEHy36NBYHe3B/mvWvjL4ntF2WnjlfLur5MuTlHz78xvpJPKDV/EyCQSNcWuabBB4WWbqO\n" +
            "TOGB8h9JsHv+q00Qd6HxHkw4sWLC4NZXqoUSfuvX4GXBjdPiflZGuaY667m+wXzMHfdbMOTK6drn\n" +
            "yPG/IPCD6q06omktDS7eh2UELS6HnQZGI2MP1PB77EroObRr+aDHSUrVulIMdJSvSUgpSilekpBS\n" +
            "lFK9JSClKKV6UIKaVFK9JSChCilelFIKUopXLVGlBSkpWpKQVXN6/njBwDpJ82XZtGiPldUN99h8\n" +
            "rwPiDqH4/qTnMNxM9Mf290HN3J9yVkcw+WHgVXKxx0XDcfBK7GPA2WCQuGxbaC+HT2Nyzvoh0n7i\n" +
            "t1ysvp0ZxoJGECeQkn2I7LZxsg4+DIy9RkbekdgpwMR+dIC91RM7/wCEHAmhkglMczCx45BVF6Xx\n" +
            "HDAI8eONnqDT+0PLl5tzSw0UEIiICIiAiIgIiICIiAiIgIiICIiAiIgIiICIiAun0PomT1rL8qEa\n" +
            "Y2/7khGzR/lOhdFyOtZohhGmNu8khGzR/lfVendOx+mYbMbGYGsbye7j7lBj6V0vF6TiDHxWUOXO\n" +
            "PLj7lbilKQQitSUgrSkBTSmkABfN/E/TZMHqDi6y2Syxx7r6U0f+K8h49lDm48P8Ac8/n/6IPD1X\n" +
            "KHlWNHcbBUPKApHKhT3Qet8I+UZrlc+2j0gE0V7Yj8vheY8GRFkJMjQdf0u9vhepIQYyFFK5CikF\n" +
            "aUUr0opBSkpXpRSClJSvSikFKUUr0opBWlBCsVFIK0oIVqQhBSlBV6UUgpSilek0oMc0QmgkjLi3\n" +
            "W0t1DkL57n9Kl6cXicjU1+kV+98r6PX8t9+68d40mvOihb+4yz90HBxma52tqxa9O/D/AAvS5Mlm\n" +
            "w005vIHyFw+jQGbLAXqPE0gxegMgH1zOF/YAoPEs1PIaCbdz8rtjIZhwxsDvp2AH7zu64oJaRRIX\n" +
            "SfHHLj4QYCTq9XuT3QYevyiTODBxGwMq+45WhBC2d7mPFjST7UsnUJBLnzyN4c8kfqpwvrkO2zDz\n" +
            "x2QcuWMxuo7/ACqLemaHkjlab2FhooKoiICIiAiIgIiICIiAiIgIiICIiAiIgLd6T0zI6tnMxcdu\n" +
            "53c48NHuVr42PLl5EcEDC+SQ6WtHcr6z4c6FD0TAEYp0795ZPc+32QbHSOlY/SMJmNjN2G7nHl59\n" +
            "yt1WUUgikpWpKQVpKV6SkFaUgK1LRz+s4HTbGVOGvqwwCyUG+AvCeNmyDqjgdxIwBvwN13pfGPSY\n" +
            "4w9r3yk/utG4/VeX6/4hh6rI10WMWuZsHOO5QecJokdrVVZwJN1sskMLZXgF4aO5KCsEMmRKI4ml\n" +
            "zzwAuv07w7mZWSIpceSNv8dDZdPonhZ2TEZxkNFH0FvIK9zB5nkNEtB4G/yg0Om9MjwYYwHO1NbV\n" +
            "Hj7rePKuf5KhQVSlalFIKkJStSikFaSlZKQUpKVlCClJStSUgpSilekpBjpKV6UUgoQopXpRSClK\n" +
            "QFak0hBUDcL574nmE/WJSOBsF77NmbjYU0xOzWWvmk5OTlWDfuUHe8KYpc/zCOU8ZZQl6kIGH0wN\n" +
            "0n79/wCi63QtGLB29DNR+F5DImdlZMs7zu92s38oNctNCl1MRpjnEMj6axmvYbg/+SsMPl+Q+TZr\n" +
            "o99+6viTSTNzcp4B0REfYkhBxrPJ5W1jACCZ9ixtutVbsOr/AE99MFF4t3cc7INJxtx+6o9ocKKs\n" +
            "iDTc0tNFQtqRge35WqRRooCIiAiIgIiICIiAiIgIiICIiAiL0/gjoH+qZ/4rIZeJjm6I2e7sP7lB\n" +
            "6PwP4e/A4w6hlM/7TMPQ0j6G/wCSvWFWpKQVpFakpBCK1JSCtKwCkNUPc2NrnvNNG5J4AQc7rvVW\n" +
            "dIwHzmnSHaNvuf8AC+XZWTLlZD553F73mySuv4q6q3qfVCYXl0Ebab9+64hAo+yCras+yv2pY2kA\n" +
            "FXaUE0haD22S7Vhtyg3umdXzemzB8Ezg3u07tP5L6D0bruL1WKgdE4";

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
        view = (CircleImageView) findViewById(R.id.circleImageView);
        bildändern = (Button) findViewById(R.id.button2);
        bildändern.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        initButton();
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                view.setImageBitmap(bitmap);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
                byte[] b = baos.toByteArray();
                pic = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d("picture in string", ""+pic);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                int kg = Integer.parseInt(inputKg.getText().toString());
                String email = inputEmail.getText().toString();
                String regex = "^[^0-9][a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*[@][a-zA-Z0-9_-]+([.][a-zA-Z0-9_]+)*[.][a-zA-Z]{2,4}$";
                if(inputName.getText().length() != 0
                        && inputDay.getText().length() != 0
                        && inputMonth.getText().length() != 0
                        && inputYear.getText().length() != 0
                        && inputEmail.getText().length() != 0
                        && inputPassword1.getText().length() != 0
                        && inputPassword2.getText().length() != 0
                        && (email.matches(regex))
                        && password1.equals(password2)
                        && (day>0 && day<=31)
                        && (month>0 && month<=12)
                        && (year>1917 && year<2017)
                        && (cm>0 && cm<=220)
                        && (kg>0 && kg<=300)){
                    onReg();
                } else {
                    if(!(cm>0 && cm<=220)){
                        inputCm.setError("Ungültige Eingabe!");
                    }
                    if(!(kg>0 && kg<=300)){
                        inputKg.setError("Ungültige Eingabe!");
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
                    if(!(email.matches(regex))){
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

















