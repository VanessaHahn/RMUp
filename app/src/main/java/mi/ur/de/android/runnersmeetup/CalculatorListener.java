package mi.ur.de.android.runnersmeetup;

/**
 * Created by Vanessa on 03.09.2016.
 */
public interface CalculatorListener {

    void updateVelocityView(double velocity);

    void updateDistanceView(double distance);

    void updateTimerView(String time);

    void updateCaloriesView(int kcal);

    void enableGps();
}