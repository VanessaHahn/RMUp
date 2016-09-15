package mi.ur.de.android.runnersmeetup;

/**
 * Created by Medion on 20.08.2016.
 */
public class Constants {
    private static int id;
    private static String name;

    private static boolean run;
    private static boolean logged;

    private static int size = 180;
    private static int weight = 77;
    private static String phone = "";
    private static String gender = "männlich";

    public static void setValues(String genderNew, int sizeNew, int weightNew, String phoneNew){
        if(genderNew.equals("weiblich")){
            gender = "weiblich";
        }
        if(sizeNew != 0) {
            size = sizeNew;
        }
        if(weightNew != 0) {
            weight = weightNew;
        }
        if(!phoneNew.equals("") && phoneNew != null){
            phone = phoneNew;
        }
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
}
