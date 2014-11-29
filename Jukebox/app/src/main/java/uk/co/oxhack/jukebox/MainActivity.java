package uk.co.oxhack.jukebox;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void serverPost(View v) {
        String url = getResources().getString(R.string.server_post);

        sendPostRequest("Yo!", url);
    }

    private void sendPostRequest(String message, String url) {

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                // Loads the parameters.
                String message = params[0];
                String url = params[1];

                // Initiates the connection.
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                try {
                    httpPost.setEntity(new StringEntity(message));

                    try {
                        // Executes the httpPost.
                        HttpResponse httpResponse = httpClient.execute(httpPost);

                        // Returns a message.
                        InputStream inputStream = httpResponse.getEntity().getContent();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        StringBuilder stringBuilder = new StringBuilder();
                        String value = null;

                        while ((value = bufferedReader.readLine()) != null) {
                            stringBuilder.append(value);
                        }

                        return stringBuilder.toString();
                    } catch (ClientProtocolException e) {
                        MessageBox.show(getApplicationContext(), e.getMessage());
                    } catch (IOException e) {
                        MessageBox.show(getApplicationContext(), e.getMessage());
                    }

                } catch (UnsupportedEncodingException e) {
                    MessageBox.show(getApplicationContext(), e.getMessage());
                }

                return null;
            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(message, url);
    }
}
