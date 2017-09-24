package mi.ur.de.android.runnersmeetup;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Medion on 09.09.2016.
 */
public class BackgroundWorker extends AsyncTask<String,Void,String[]> {
    Context context;
    AlertDialog alertDialog;
    BackgroundWorker(Context ctx){
        context = ctx;
    }

    @Override
    protected String[] doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://runnersmeetup.hol.es/login.php";
        String register_url = "http://runnersmeetup.hol.es/register.php";
        String lauf_data = "http://runnersmeetup.hol.es/lauf.php";
        String show_profil = "http://runnersmeetup.hol.es/showProfil.php";
        String show_data = "http://runnersmeetup.hol.es/showData.php";
        String update_data = "http://runnersmeetup.hol.es/updateData.php";
        String show_lauf = "http://runnersmeetup.hol.es/laeufe.php";
        String filter_url = "http://runnersmeetup.hol.es/filter.php";
        String event_url = "http://runnersmeetup.hol.es/veranstaltungen.php";
        String show_events = "http://runnersmeetup.hol.es/showEvents.php";
        String friends_url = "http://runnersmeetup.hol.es/addFriends.php";
        String show_friends = "http://runnersmeetup.hol.es/showFriends.php";
        String delete_friendship = "http://runnersmeetup.hol.es/deleteFriendship.php";
        if(type.equals("login")){
            try {
                String user_name = params[1];
                String password = params[2];
                Log.d("Backroundworker", "UserName: " + user_name);
                Log.d("Backroundworker", "Password: " + password);
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                Log.d("Backroundworker", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker", "Result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"login",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if(type.equals("register")) {
            try {
                String profilbild = params[1];
                String name = params[2];
                String geschlecht = params[3];
                String geburtsdatum = params[4];
                String cm = params[5];
                String kg = params[6];
                String email = params[7];
                String phone = params[8];
                String password = params[9];

                Log.d("Backroundworker", "profilbild: " + profilbild);
                Log.d("Backroundworker", "UserName: " + name);
                Log.d("Backgroundworker", "Geschlecht: " + geschlecht);
                Log.d("Backroundworker", "Geburtsdatum: " + geburtsdatum);
                Log.d("Backgroundworker", "cm: " + cm);
                Log.d("Backgroundworker", "kg: " + kg);
                Log.d("Backroundworker", "Email " + email);
                Log.d("Backroundworker", "Phone " + phone);
                Log.d("Backroundworker", "Password: " + password);
                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("profilbild", "UTF-8") + "=" + URLEncoder.encode(profilbild, "UTF-8") + "&"
                        + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+"&"
                        + URLEncoder.encode("geschlecht", "UTF-8") + "=" + URLEncoder.encode(geschlecht, "UTF-8")+"&"
                        + URLEncoder.encode("geburtsdatum", "UTF-8") + "=" + URLEncoder.encode(geburtsdatum, "UTF-8")+"&"
                        + URLEncoder.encode("cm", "UTF-8") + "=" + URLEncoder.encode(cm, "UTF-8")+"&"
                        + URLEncoder.encode("kg", "UTF-8") + "=" + URLEncoder.encode(kg, "UTF-8")+"&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+"&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+"&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                Log.d("Backroundworker_regist", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker", "Result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"register",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if(type.equals("laufAnzeigen")){
            try {
                String id = params[1];
                String item = params[2];
                URL url = new URL(show_lauf);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" + URLEncoder.encode("item", "UTF-8") + "=" + URLEncoder.encode(item, "UTF-8");
                Log.d("Backroundworker_setPos", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("deleteFriendship")){
            try {
                String userID = params[1];
                String friendID = params[2];
                Log.d("Backroundworker", "userID: " + userID);
                Log.d("Backroundworker", "friendID: " + friendID);
                URL url = new URL(delete_friendship);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(userID, "UTF-8") + "&" + URLEncoder.encode("friendID", "UTF-8") + "=" + URLEncoder.encode(friendID, "UTF-8");
                Log.d("Backroundwo_deleteF", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundwo_deleteF", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if(type.equals("addFriends")){
            try {
                String ownid = params[1];
                String id = params[2];
                String friendsName = params[3];
                Log.d("Backroundworker", "ownid: " + ownid);
                Log.d("Backroundworker", "id " + id);
                URL url = new URL(friends_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("ownid", "UTF-8") + "=" + URLEncoder.encode(ownid, "UTF-8") + "&" + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" + URLEncoder.encode("friendsName", "UTF-8") + "=" + URLEncoder.encode(friendsName, "UTF-8");
                Log.d("Backroundworker_setPos", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("showEvents")){
            try {
                String id = params[1];
                URL url = new URL(show_events);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                Log.d("Backroundworker_setPos", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("showFriends")){
            try {
                String id = params[1];
                URL url = new URL(show_friends);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                Log.d("Backroundworker_setPos", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("showProfil")){
            try {
                String userName = params[1];
                URL url = new URL(show_profil);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userName", "UTF-8") + "=" + URLEncoder.encode(userName, "UTF-8");
                Log.d("Backroundworker_setPos", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }  else if(type.equals("updateData")) {
            try {
                String id = params[1];
                String name = params[2];
                String groesse = params[3];
                String gewicht = params[4];
                Log.d("RegisterKg",gewicht);
                String email = params[5];
                String phone = params[6];
                String password = params[7];
                URL url = new URL(update_data);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                        + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+"&"
                        + URLEncoder.encode("groesse", "UTF-8") + "=" + URLEncoder.encode(groesse, "UTF-8")+"&"
                        + URLEncoder.encode("gewicht", "UTF-8") + "=" + URLEncoder.encode(gewicht, "UTF-8")+"&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+"&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+"&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                Log.d("Backroundworker_regist", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker", "Result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"register",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if(type.equals("laufSpeichern")){
            try {
                String geschwindigkeit = params[1];
                String strecke = params[2];
                String dauer = params[3];
                String id = params[4];
                String longitude = params[5];
                String latitude = params[6];
                Log.d("Backroundworker", "longitude " + longitude);
                Log.d("Backroundworker", "latitude " + latitude);
                URL url = new URL(lauf_data);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("geschwindigkeit", "UTF-8") + "=" + URLEncoder.encode(geschwindigkeit, "UTF-8") + "&"
                        + URLEncoder.encode("strecke", "UTF-8") + "=" + URLEncoder.encode(strecke, "UTF-8")+"&"
                        + URLEncoder.encode("dauer", "UTF-8") + "=" + URLEncoder.encode(dauer, "UTF-8")+"&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8")+"&"
                        + URLEncoder.encode("longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8")+"&"
                        + URLEncoder.encode("latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
                Log.d("Backroundworker_setPos", "post_data: <" + post_data + ">");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if(type.equals("event_erstellen")){
            try {
                String titel = params[1];
                String datum = params[2];
                String uhrzeit = params[3];
                String treffpunkt = params[4];
                String text = params[5];
                String id = params[6];
                Log.d("Backroundworker", "titel " + titel);
                Log.d("Backroundworker", "datum " + datum);
                Log.d("Backroundworker", "uhrzeit " + uhrzeit);
                Log.d("Backroundworker", "treffpunkt " + treffpunkt);
                Log.d("Backroundworker", "text " + text);
                URL url = new URL(event_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("titel", "UTF-8") + "=" + URLEncoder.encode(titel, "UTF-8") + "&"
                        + URLEncoder.encode("datum", "UTF-8") + "=" + URLEncoder.encode(datum, "UTF-8")+"&"
                        + URLEncoder.encode("uhrzeit", "UTF-8") + "=" + URLEncoder.encode(uhrzeit, "UTF-8")+"&"
                        + URLEncoder.encode("treffpunkt", "UTF-8") + "=" + URLEncoder.encode(treffpunkt, "UTF-8")+"&"
                        + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8")+"&"
                        + URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_setPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"setPos",result};
            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }
        } else if(type.equals("filter")) {
            try {
                String id = params[1];
                URL url = new URL(filter_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                Log.d("Backroundworker_getPos", "result: <" + result + ">");
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return new String[]{"filtered", result};
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        alertDialog = new AlertDialog.Builder(context).create();
    }

    @Override
    protected void onPostExecute(String[] result){
        if(result[0].equals("login")) {
            alertDialog.setMessage(result[1]);
            alertDialog.show();
        } else if(result[0].equals("updated Data")) {
            Toast toast = Toast.makeText(context, result[1], Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values){
        super.onProgressUpdate(values);
    }
}
