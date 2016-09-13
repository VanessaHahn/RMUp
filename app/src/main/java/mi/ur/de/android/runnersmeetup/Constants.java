package mi.ur.de.android.runnersmeetup;

/**
 * Created by Medion on 20.08.2016.
 */
public class Constants {
    public static final String KEY_CM = "cm";
    public static final String KEY_KG = "kg";
    public static final String KEY_PHONE = "name";
    public static final String KEY_GENDER = "gender";

    private static int size = 180;
    private static int weight = 77;
    private static String phone = "";
    private static boolean gender = true;

    public static void setValues(String genderNew, int sizeNew, int weightNew, String phoneNew){
        if(genderNew.equals("männlich")){
            gender = true;
        } else {
            gender = false;
        }
        size = sizeNew;
        weight = weightNew;
        phone = phoneNew;
    }

    public static int getSize(){
        return size;
    }

    public static int getWeight(){
        return weight;
    }

    public static boolean getGender(){
        return gender;
    }

    public static String getPhone(){
        return phone;
    }
}
