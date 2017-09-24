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

public class RunListAdapter extends ArrayAdapter<RunItem>{

    private Context mContext;
    int mResource;

    public RunListAdapter(Context context, int resource, ArrayList<RunItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String date = getItem(position).getDate();
        float gesch = getItem(position).getGesch();
        String dauer = getItem(position).getDauer();
        float km = getItem(position).getKm();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvDate = (TextView) convertView.findViewById(R.id.textView13);
        TextView tvGesch = (TextView) convertView.findViewById(R.id.textView12);
        TextView tvKm = (TextView) convertView.findViewById(R.id.textView14);
        TextView tvDauer = (TextView) convertView.findViewById(R.id.textView15);

        tvDate.setText(date);
        tvGesch.setText(String.valueOf(gesch));
        tvDauer.setText(dauer);
        tvKm.setText(String.valueOf(km));

        return convertView;
    }
}