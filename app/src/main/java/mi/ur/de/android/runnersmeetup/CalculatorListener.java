package mi.ur.de.android.runnersmeetup;

/**
 * Created by Vanessa on 03.09.2016.
 */
public interface CalculatorListener {

    void storeRunData(double velocity, double distance, int time);

    void updateVelocityView(double velocity);

    void updateDistanceView(double distance);

    void updateTimerView(String time);

    void updateCaloriesView(int kcal);

    void updateTimeInKMView(final String time);

    void updateVelocityMeanView(final double meanSpeed);

    void updateNotification(double velocity, double distance, String time);

    void enableGps();

    void storeRunData(double velocity, double distance, int time);
}
