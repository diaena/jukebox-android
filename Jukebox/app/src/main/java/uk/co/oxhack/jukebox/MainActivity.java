package uk.co.oxhack.jukebox;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.oxhack.jukebox.components.Audio;
import uk.co.oxhack.jukebox.components.PlaylistAdapter;
import uk.co.oxhack.jukebox.managers.DataManager;
import uk.co.oxhack.jukebox.managers.HTTPManager;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadPlaylist() {
        ListView playlist = (ListView)findViewById(R.id.playlist);
        ArrayList<Audio> audioList = DataManager.getAudio(getApplicationContext());

        playlist.setAdapter(new PlaylistAdapter(this, audioList));
    }

    public void serverPost(View v) {
        String url = getResources().getString(R.string.server_post);

        HTTPManager.SendPostRequest(getApplicationContext(), url, "Yo!");
    }

    public void loadAudio(View v) {
        loadPlaylist();
    }
}
