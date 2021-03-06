package mi.ur.de.android.runnersmeetup;

import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.content.Intent;

import java.util.ArrayList;

public class Constants {
    private static int id = -1;
    private static String name;

    private static boolean run;
    private static boolean logged;

    private static double avgVelocity = 0;
    private static double distance = 0;
    private static int time;
    private static double locationLongitude;
    private static double locationLatitude;

    private static int size = 0;
    private static float weight = 0;
    private static String phone;
    private static String email;
    private static String pw;
    private static String gender = "männlich";

    public static final String KEY_ID = "ID";
    public static final String KEY_NAME = "NAME";
    public static final String KEY_WEIGHT = "WEIGHT";
    public static final String KEY_SIZE = "SIZE";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PW = "passwort";


    public static void setEmail(String userEmail){
        email = userEmail;
    }

    public static void setPasswort(String passwort){
        pw = passwort;
    }

    public static String getPasswort(){
        return pw;
    }

    public static String getEmail(){
        return email;
    }

    public static void setRun(boolean runCondition){
        run = runCondition;
    }

    public static void setName(String userName){
        name = userName;
    }

    public static void setId(int userId){
        id = userId;
    }

    public static void setGroesse(int groesse){
        size = groesse;
    }

    public static void setGewicht(float gewicht){
        weight = gewicht;
    }

    public static String getName(){
        return name;
    }

    public static int getId() {
        return id;
    }

    public static boolean isLogged(){
        return logged;
    }

    public static boolean isRun(){
        return run;
    }

    public static String getAvgVelocity(){
        return "" + avgVelocity;
    }

    public static String getLocationLongitude() {
        return "" + locationLongitude;
    }

    public static String getLocationLatitude(){
        return "" + locationLatitude;
    }

    public static int getSize(){
        return size;
    }

    public static float getWeight(){
        return weight;
    }

    public static String getGender(){
        return gender;
    }

    public static String getPhone(){
        return phone;
    }

    public static void setLocationLatitude(double latitude) {
        locationLatitude = latitude;
    }

    public static void setLocationLongitude(double longitude) {
        locationLongitude = longitude;
    }

    public static void setAvgVelocity(double velocity) {
        avgVelocity = velocity;
    }

    public static void setDistance(double newDistance) {
        distance = newDistance;
    }

    public static void setTime(int newTime) {
        time = newTime;
    }
    

    public static String getDistance() {
        return "" + distance;
    }

    public static String getTime() {
        String formatedTime = getFormatedTime(time);
        return formatedTime;
    }

    public static String getFormatedTime(int time) {
        int min = time/60;
        int sek = time - (60*min);
        if(min < 10) {
            if(sek < 10){
                return "0" + min + ":0" + sek;
            }else {
                return "0" + min + ":" + sek;
            }
        } else {
            if(sek < 10){
                return min + ":0" + sek;
            }else {
                return min + ":" + sek;
            }
        }
    }

    public static boolean getLogged() {
        return logged;
    }

    public static void setPhone(String phoneNew) {
        phone = phoneNew;
    }

    public static boolean parseLaeufeString(String dbString){
        if(dbString.indexOf("/")>0){
            String[] splitdbString = dbString.split("[.]");
            for (int i = 0; i<4; i++){
                String[] splitString = splitdbString[i].split("[/]");
            }
        }
        return false;
    }

    public static boolean parseLoginString(String dbString) {
        if(dbString.indexOf("/") > 0){
            String[] splitString = dbString.split("/");
            if(splitString.length  == 8){
                int id = Integer.parseInt(splitString[1]);
                String name = splitString[2];
                String telefon = splitString[3];
                String email = splitString[4];
                String passwort = splitString[5];
                int groesse = Integer.parseInt(splitString[6]);
                float gewicht = Float.parseFloat(splitString[7]);
                Constants.setId(id);
                Constants.setName(name);
                Constants.setGroesse(groesse);
                Constants.setGewicht(gewicht);
                Constants.setPhone(telefon);
                Log.d("PhoneNew", telefon);
                Constants.setEmail(email);
                Constants.setPasswort(passwort);
                return true;
            }
        }
        return false;
    }
}
