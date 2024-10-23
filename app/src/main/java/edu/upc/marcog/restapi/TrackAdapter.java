package edu.upc.marcog.restapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.List;

public class TrackAdapter extends ArrayAdapter<Track> {

    public TrackAdapter(Context context, List<Track> tracks) {
        super(context, 0, tracks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Track track = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_track, parent, false);
        }

        TextView trackIdTextView = convertView.findViewById(R.id.trackIdTextView);
        TextView trackTitleTextView = convertView.findViewById(R.id.trackTitleTextView);
        TextView trackSingerTextView = convertView.findViewById(R.id.trackSingerTextView);

        trackIdTextView.setText(track.getId());
        trackTitleTextView.setText(track.getTitle());
        trackSingerTextView.setText(track.getSinger());

        return convertView;
    }
}
