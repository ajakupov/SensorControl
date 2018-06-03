package fr.eisti.sensorcontrol.connectivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fr.eisti.sensorcontrol.sensors_list.SensorsListActivity;
import fr.eisti.sensorcontrol.sensors_list.SensorsListFragment;

/**
 * Created by eisti on 12/12/17.
 *
 * Class implementing the simple server request in background
 *
 */

public class GetRequest extends AsyncTask <String, Void, String> {
    private final Context context;
    //to avoid null exception
    public GetRequest(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        //get server address from the parameters list
        String address = params[0];
        //server response to be sent to the caller activity
        String serverResponse = "";
        try {
            //setting up the connection properties
            URL url = new URL("https://www.google.fr/");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            String urlParameters = "fizz=buzz";
            //Setting connection parameters
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

            //reading server response
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));

            //reading response body line by line
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                serverResponse += line + "\r\n";
            }
            //close the buffered reader
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SensorsListFragment.serverResponse = serverResponse;



        return address;
    }
}
