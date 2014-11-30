package uk.co.oxhack.jukebox;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.oxhack.jukebox.components.Audio;
import uk.co.oxhack.jukebox.components.PlaylistAdapter;
import uk.co.oxhack.jukebox.managers.DataManager;
import uk.co.oxhack.jukebox.managers.HTTPManager;
import uk.co.oxhack.jukebox.managers.MessageBox;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadPlaylist();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_name:
                DataManager.setOrderBy(MediaStore.Audio.Media.DISPLAY_NAME);
                loadPlaylist();

                return true;

            case R.id.action_album:
                DataManager.setOrderBy(MediaStore.Audio.Media.ALBUM);
                loadPlaylist();

                return true;

            case R.id.action_artist:
                DataManager.setOrderBy(MediaStore.Audio.Media.ARTIST);
                loadPlaylist();

                return true;

            case R.id.action_duration:
                DataManager.setOrderBy(MediaStore.Audio.Media.DURATION);
                loadPlaylist();

                return true;

            case R.id.action_date:
                DataManager.setOrderBy(MediaStore.Audio.Media.DATE_MODIFIED);
                loadPlaylist();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadPlaylist() {
        ListView playlist = (ListView)findViewById(R.id.playlist);
        ArrayList<Audio> audioList = DataManager.fetchAudio(getApplicationContext());

        playlist.setAdapter(new PlaylistAdapter(this, audioList));
        playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Audio selectedAudio = DataManager.getAudioList().get(position);

                MessageBox.show(getApplicationContext(), "Queueing " + selectedAudio.getName());
                queueSong(selectedAudio.getPath(), selectedAudio.getName());
            }
        });
    }

    public void queueSong(String path, String name) {
        String url = getResources().getString(R.string.server_post);

        HTTPManager.UploadFile(getApplicationContext(), url, path, name);
    }
}
