package fr.eisti.sensorcontrol.sensor_details;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.connectivity.LogContract;
import fr.eisti.sensorcontrol.connectivity.SessionLogProvider;
import fr.eisti.sensorcontrol.main_menu.MainMenuActivity;
import fr.eisti.sensorcontrol.model.Sensor;
import fr.eisti.sensorcontrol.sensors_list.SensorsListActivity;
import fr.eisti.sensorcontrol.sensors_list.SensorsListFragment;
import fr.eisti.sensorcontrol.welcome.WelcomeActivity;

/**
 * Created by eisti on 17/12/17.
 */

public class SensorDetailsFragment extends Fragment {
    //screen widgets
    TextView sensorName;
    TextView sensorClass;
    TextView sensorLocation;
    TextView back;
    Button sensorActivate;

    //session variable storing sensor values
    public static Sensor sensor;
    @Override
    public View onCreateView (LayoutInflater inflater, final ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sensor_details_fragment_layout,
                container, false);
        //if the user has no root acces redirect him to the welcome page
        if (!SensorDetailsActivity.hasAccess) {
            Intent intent = new Intent(getActivity(), WelcomeActivity.class);
            startActivity(intent);
            Toast.makeText(getActivity(),
                    "Sorry, you are not allowed to access this page",
                    Toast.LENGTH_LONG).show();
        }
        //color int values obtained from hex
        int green = Color.parseColor("#12c60c");
        int red = Color.parseColor("#d17249");
        //Initializing widgets
        sensorName = (TextView) view.findViewById(R.id.sensor_details_name);
        sensorClass = (TextView) view.findViewById(R.id.sensor_details_class);
        sensorLocation = (TextView) view.findViewById(R.id.sensor_details_location);
        back = (TextView) view.findViewById(R.id.sensor_details_back);
        sensorActivate = (Button) view.findViewById(R.id.sensor_details_activate);

        //set text view values
        sensorName.setText(sensor.getName());
        sensorLocation.setText(sensor.getLocation());
        sensorClass.setText(sensor.getSensorClass());

        if (sensor.isOn()) {
            sensorActivate.setBackgroundColor(green);
            sensorActivate.setText("Deactivate");
        } else {
            sensorActivate.setBackgroundColor(red);
            sensorActivate.setText("Activate");
        }

        sensorActivate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //sending update request
                String updateRequest = "http://sensors.getsandbox.com/sensors_list/"
                        +sensor.getName() + "/" + approve(!sensor.isOn());
                new SimpleGetRequest(getActivity()).execute(updateRequest);
                Toast.makeText(getActivity(), updateRequest, Toast.LENGTH_LONG).show();
                //saving session log
                ContentValues contentValues = new ContentValues();
                contentValues.put(LogContract.SessionLogs.COLUMN_USER,
                        MainMenuActivity.user.getName());
                contentValues.put(LogContract.SessionLogs.COLUMN_BODY, "sensor switch request "
                        + sensor.getName());
                contentValues.put(LogContract.SessionLogs.COLUMN_DATE, new Date().toString());
                Uri uri = getActivity().
                        getContentResolver().insert(LogContract.CONTENT_URI, contentValues);
                Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_LONG).show();
                //redirect to the previous screen
                Intent intent = new Intent(getActivity(), SensorsListActivity.class);
                startActivity(intent);
            }
        });

        //back to sensors list
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SensorsListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    //function converting true to yes and false to no
    //needed to prepare the update request
    private String approve(boolean isOn) {
        String output = "no";
        if (isOn) {
            output = "yes";
        } else {
            output = "no";
        }

        return output;
    }

    private class SimpleGetRequest extends AsyncTask<String, Integer, String> {
        private final Context context;
        //to avoid null exception
        public SimpleGetRequest(Context context) {
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
                URL url = new URL(address);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                String urlParameters = "fizz=buzz";
                //Setting connection parameters
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                urlConnection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                //reading server response
                final BufferedReader bufferedReader = new BufferedReader(
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

            return serverResponse;
        }

        @Override
        protected void onPostExecute(String response) {

            try {
                JSONObject jsonObject = new JSONObject();
                String status = jsonObject.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
