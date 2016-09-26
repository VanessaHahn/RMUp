package mi.ur.de.android.runnersmeetup;

import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by Vanessa on 15.09.2016.
 */
public class RunItem {
    private String newRun;

    public RunItem(String newRun) {
        this.newRun = newRun;
    }

    public String getNewRun(){
        return newRun;
    }
}
