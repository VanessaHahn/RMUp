package mi.ur.de.android.runnersmeetup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Medion on 22.09.2016.
 */
public class MyAdapter extends ArrayAdapter<RunItem> {
    private ArrayList<RunItem> runItems;
    private Context context;

    public MyAdapter(Context context, ArrayList<RunItem> runItems) {
        super(context, R.layout.runitem_runhistory, runItems);

        this.context = context;
        this.runItems = runItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.runitem_runhistory, null);

        }

        RunItem run = runItems.get(position);

        if (run != null) {


            TextView time = (TextView) v.findViewById(R.id.time);
            TextView distance = (TextView) v.findViewById(R.id.distance);
            TextView calorien = (TextView) v.findViewById(R.id.calories);

            time.setText(run.getTime());
            distance.setText(""+run.getDistance());
            calorien.setText(run.getCalories());
        }

        return v;
    }



}
