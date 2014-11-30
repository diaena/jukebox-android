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
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPManager {

    private static Context applicationContext;
    private static FileInputStream fileInputStream;
    private static String fileName;

    public static void SendPostRequest(Context context, String url, String message) {
        applicationContext = context;

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                // Loads the parameters.
                String url = params[0];
                String message = params[1];

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

        new SendPostReqAsyncTask().execute(url, message);
    }

    public static void UploadFile(Context context, String url, String path, final String name) {
        applicationContext = context;

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                HttpURLConnection connection = null;

                try {
                    // Loads the parameters.
                    URL url = new URL(params[0]);

                    // Configures the connection.
                    connection = (HttpURLConnection)url.openConnection();

                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");

                    // Configures the data output stream.
                    DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

                    dataOutputStream.writeBytes("----WebKitFormBoundaryE19zNvXGzXaLvS6C\r\n");
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + name + "\"\r\n");
                    dataOutputStream.writeBytes("Content-Type: audio/mp3\r\n\r\n");

                    int bytesAvailable = fileInputStream.available();
                    int bufferSize = Math.min(bytesAvailable, 1048576);
                    byte[] buffer = new byte[bufferSize];
                    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dataOutputStream.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, 1048576);
                        bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    }

                    dataOutputStream.writeBytes("\r\n----WebKitFormBoundaryE19zNvXGzXaLvS6C--\r\n");

                    // Closes the streams.
                    fileInputStream.close();
                    dataOutputStream.flush();
                    dataOutputStream.close();
                } catch (MalformedURLException e) {
                    MessageBox.show(applicationContext, e.getMessage());
                } catch (IOException e) {
                    MessageBox.show(applicationContext, e.getMessage());
                }

                // Gets the response from the server.
                try {
                    int code = connection.getResponseCode();
                    String msg = connection.getResponseMessage();

                } catch (IOException e) {
                    MessageBox.show(applicationContext, e.getMessage());
                }

                return null;
            }
        }

        try {
            // Loads the file.
            fileInputStream = new FileInputStream(new File(path));
            fileName = name;

            new SendPostReqAsyncTask().execute(url);
        } catch (FileNotFoundException e) {
            MessageBox.show(applicationContext, e.getMessage());
        }
    }

}
