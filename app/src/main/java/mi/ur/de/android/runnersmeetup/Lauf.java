package mi.ur.de.android.runnersmeetup;

/**
 * Created by Theresa on 04.09.2017.
 */

public class Lauf {
    private String date;
    private String gesch;
    private String dauer;
    private String km;


    public Lauf(String date, String gesch, String dauer, String km) {
        this.date = date;
        this.gesch = gesch;
        this.dauer = dauer;
        this.km = km;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGesch() {
        return gesch;
    }

    public void setGesch(String gesch) {
        this.gesch = gesch;
    }

    public String getDauer() {
        return dauer;
    }

    public void setDauer(String dauer) {
        this.dauer = dauer;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }
}
