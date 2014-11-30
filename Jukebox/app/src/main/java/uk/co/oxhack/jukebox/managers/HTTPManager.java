package uk.co.oxhack.jukebox.managers;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class HTTPManager {

    private static Context applicationContext;

    public static void SendPostRequest(Context context, String url, String message) {
        applicationContext = context;

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
                        MessageBox.show(applicationContext, e.getMessage());
                    } catch (IOException e) {
                        MessageBox.show(applicationContext, e.getMessage());
                    }

                } catch (UnsupportedEncodingException e) {
                    MessageBox.show(applicationContext, e.getMessage());
                }

                return null;
            }
        }

        new SendPostReqAsyncTask().execute(message, url);
    }

}
