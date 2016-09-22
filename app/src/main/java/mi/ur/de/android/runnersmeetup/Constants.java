package mi.ur.de.android.runnersmeetup;

/**
 * Created by Medion on 20.08.2016.
 */
public class Constants {
    private static int id;
    private static String name;

    private static boolean run;
    private static boolean logged;

    private static double avgVelocity = 0;
    private static double distance = 0;
    private static int time;
    private static double locationLongitude;
    private static double locationLatitude;

    private static int size = 180;
    private static int weight = 77;
    private static String phone = "";
    private static String gender = "männlich";

    public static final String KEYCM = "cm";
    public static final String KEYKG = "kg";

    public static void setValues(String genderNew, int sizeNew, int weightNew, String phoneNew){
        if(genderNew.equals("weiblich")){
            gender = "weiblich";
        } else {
            gender = "männlich";
        }
        if(sizeNew != 0) {
            size = sizeNew;
        }
        if(weightNew != 0) {
            weight = weightNew;
        }
        phone = phoneNew;
    }

    public static void setRun(boolean runCondition){
        run = runCondition;
    }

    public static void setLogged(boolean loggedCondition){
        logged = loggedCondition;
    }

    public static void setName(String userName){
        name = userName;
    }

    public static void setId(int userId){
        id = userId;
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

    public static int getWeight(){
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
        if(sek < 10){
            return min + ":0" + sek;
        }else {
            return min + ":" + sek;
        }
    }

    public static boolean getLogged() {
        return logged;
    }

    public static void setPhone(String phoneNew) {
        phone = phoneNew;
    }
}
