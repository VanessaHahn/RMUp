package mi.ur.de.android.runnersmeetup;

import android.location.Location;

/**
 * Created by Theresa on 26.09.16.
 */
/*
Positon Connection to SQL Database:
        //AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute("setPos", "12", "2100", "120");
        AsyncTask<String, Void, String[]> returnAsyncTask = backgroundWorker.execute("getPos", "12");
        try {
            ArrayList<Runner> runners = Constants.parseGetPosStringArray(returnAsyncTask.get()[1]);
            for(int i = 0; i < runners.size(); i++){ Log.d("Test", runners.get(i).getInfo());}

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

 */
public class Runner {
    private int id;
    private Location loc;

    Runner(int id, Location loc){
        this.id = id;
        this.loc = loc;
    }
    public String getInfo(){
        return "ID: " + id + "-Lat: "+ loc.getLatitude() + "-Long: "+ loc.getLongitude() + "\n";
    }
    public Location getLocation(){
        return loc;
    }
    public double getDistance(Runner run){
        return loc.distanceTo(run.getLocation());
    }


}
