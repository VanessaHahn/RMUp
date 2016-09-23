package mi.ur.de.android.runnersmeetup;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Vanessa on 15.09.2016.
 */
public class RunItem {
    private int time;
    private double distance;
    private int calories;

    public RunItem(int time,double distance,int calories) {
        this.time = time;
        this.distance = distance;
        this.calories = calories;
    }

    public int getTime(){
        return time;
    }

    public double getDistance(){
        return distance;
    }

    public int getCalories(){
        return calories;
    }
}
