package mi.ur.de.android.runnersmeetup;

public interface CalculatorListener {

    void storeRunData(double velocity, double distance, int time);

    void updateVelocityView(double velocity);

    void updateDistanceView(double distance);

    void updateTimerView(String time);

    void updateCaloriesView(int kcal);

    void updateTimeInKMView(final String time);

    void updateVelocityMeanView(final double meanSpeed);

    void updateNotification(double velocity, double meanVelocity, double distance, int calories, String time);

    void enableGps();
}
