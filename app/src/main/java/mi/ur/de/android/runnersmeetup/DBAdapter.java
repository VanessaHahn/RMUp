package mi.ur.de.android.runnersmeetup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Vanessa on 15.09.2016.
 */
public class DBAdapter extends ArrayAdapter<RunItem>{
    private ArrayList<RunItem> runItems;
    private Context context;

    public DBAdapter(Context context, ArrayList<RunItem> runItems) {
        super(context, R.layout.list_row, runItems);

        this.context = context;
        this.runItems = runItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_row, null);

        }

        RunItem run = runItems.get(position);

        if (run != null) {
            TextView itemmView = (TextView) v.findViewById(R.id.item);


            itemmView.setText(run.getString());
        }

        return v;
    }
}
