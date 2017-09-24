package mi.ur.de.android.runnersmeetup;

public class Event {
    private String titel, datum, uhrzeit, ort, details;

    public Event(String titel, String datum, String uhrzeit, String ort, String details) {
        this.titel = titel;
        this.datum = datum;
        this.uhrzeit = uhrzeit;
        this.ort = ort;
        this.details = details;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(String uhrzeit) {
        this.uhrzeit = uhrzeit;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
