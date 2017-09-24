package mi.ur.de.android.runnersmeetup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListAdapter extends ArrayAdapter<Event> {

    private Context mContext;
    int mResource;

    public EventListAdapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String titel = getItem(position).getTitel();
        String datum = getItem(position).getDatum();
        String uhrzeit = getItem(position).getUhrzeit();
        String ort = getItem(position).getOrt();
        String text = getItem(position).getDetails();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvTitel = (TextView) convertView.findViewById(R.id.show_event_titel);
        TextView tvDatum = (TextView) convertView.findViewById(R.id.show_event_date);
        TextView tvUhrzeit = (TextView) convertView.findViewById(R.id.show_event_time);
        TextView tvOrt = (TextView) convertView.findViewById(R.id.show_event_place);
        TextView tvText = (TextView) convertView.findViewById(R.id.show_event_text);

        tvTitel.setText(titel);
        tvDatum.setText(datum);
        tvUhrzeit.setText(uhrzeit);
        tvOrt.setText(ort);
        tvText.setText(text);

        return convertView;
    }
}

