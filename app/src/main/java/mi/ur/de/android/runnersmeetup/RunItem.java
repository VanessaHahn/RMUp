package mi.ur.de.android.runnersmeetup;

/**
 * Created by Theresa on 04.09.2017.
 */

public class RunItem {
    private String date;
    private float gesch;
    private String dauer;
    private float km;


    public RunItem(String date, String gesch, String km, String dauer) {
        this.date = date;
        this.gesch = Float.parseFloat(gesch);
        this.dauer = Constants.getFormatedTime(Integer.parseInt(dauer));
        this.km = Float.parseFloat(km)/1000;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getGesch() {
        return gesch;
    }

    public void setGesch(String gesch) {
        this.gesch = Float.parseFloat(gesch);
    }

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = Integer.parseInt(dauer)/60 + ":" + Integer.parseInt(dauer)%60;
    }

    public float getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = Float.parseFloat(km)/1000;
    }
}
