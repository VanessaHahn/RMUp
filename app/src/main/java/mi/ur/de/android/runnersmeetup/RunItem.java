package mi.ur.de.android.runnersmeetup;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Vanessa on 15.09.2016.
 */
public class RunItem {
    //private int time;
    private String time;
    //private int calories;

    public RunItem(String time) {
       // this.time = time;
        this.time = time;
        //this.calories = calories;
    }

    //public int getTime(){
      //  return time;
    //}

    public String getTime(){
        return time;
    }

    //public int getCalories(){
      //  return calories;
    //}
}
