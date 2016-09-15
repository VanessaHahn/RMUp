package mi.ur.de.android.runnersmeetup;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Vanessa on 15.09.2016.
 */
public class RunItem {
    private String name;
    private double velocity;
    private int distance;
    private String time;

    public RunItem(String name, double velocity, int distance, String time) {
        this.name = name;
        this.velocity = velocity;
        this.distance = distance;
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public int getDistance(){
        return distance;
    }

    public String getTime(){
        return time;
    }

    public double getVelocity(){
        return velocity;
    }

    public String getString() {
        return name + ": Øv: " + velocity + " km/h  s: " + (double) distance/1000 + " km  t: " + time + " min";
    }
}
