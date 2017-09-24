package mi.ur.de.android.runnersmeetup;

public class RunItem {
    private String date;
    private float velocity;
    private String time;
    private float distance;


    public RunItem(String date, String velocity, String distance, String time) {
        this.date = date;
        this.velocity = Float.parseFloat(velocity);
        this.time = Constants.getFormatedTime(Integer.parseInt(time));
        this.distance = Float.parseFloat(distance)/1000;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(String velocity) {
        this.velocity = Float.parseFloat(velocity);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = Constants.getFormatedTime(Integer.parseInt(time));
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = Float.parseFloat(distance)/1000;
    }
}
