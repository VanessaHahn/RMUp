package mi.ur.de.android.runnersmeetup;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Theresa on 04.09.2017.
 */

public class LaufListAdapter extends ArrayAdapter<Lauf>{

    private Context mContext;
    int mResource;

    public LaufListAdapter(Context context, int resource, ArrayList<Lauf> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        String gesch = getItem(position).getGesch();
        String dauer = getItem(position).getDauer();
        String km = getItem(position).getKm();

        Lauf lauf = new Lauf(date,gesch,dauer,km);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.textView13);
        TextView tvGesch = (TextView) convertView.findViewById(R.id.textView12);
        TextView tvDauer = (TextView) convertView.findViewById(R.id.textView14);
        TextView tvKm = (TextView) convertView.findViewById(R.id.textView15);

        tvDate.setText(date);
        tvGesch.setText(gesch);
        tvDauer.setText(dauer);
        tvKm.setText(km);

        return convertView;
    }
}


















