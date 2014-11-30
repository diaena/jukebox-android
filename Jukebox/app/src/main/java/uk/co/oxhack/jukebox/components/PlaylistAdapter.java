package uk.co.oxhack.jukebox.components;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

public class PlaylistAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Audio> audioList;

    public PlaylistAdapter(Context context, ArrayList<Audio> audioList) {
        this.context = context;
        this.audioList = audioList;
    }

    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public Object getItem(int position) {
        return audioList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TwoLineListItem twoLineListItem;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(
                    android.R.layout.simple_list_item_2, null);
        } else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        // Sets the name.
        text1.setText(audioList.get(position).getName());

        // Sets the album.
        String album = audioList.get(position).getAlbum();

        if (!album.equals(MediaStore.UNKNOWN_STRING)) {
            text2.setText(album);
        }

        // Sets the artist.
        String artist = audioList.get(position).getArtist();

        if (!artist.equals(MediaStore.UNKNOWN_STRING)) {
            if (text2.length() > 0) {
                text2.append(System.getProperty("line.separator") + artist);
            } else {
                text2.setText(artist);
            }
        }

        // Sets the duration.
        String duration = audioList.get(position).getDuration();

        // Changes duration from milliseconds to seconds.
        duration = String.valueOf(Double.parseDouble(duration) / 1000) + 's';

        if (text2.length() > 0) {
            text2.append(System.getProperty("line.separator") + duration);
        } else {
            text2.setText(duration);
        }

        return twoLineListItem;
    }

}
