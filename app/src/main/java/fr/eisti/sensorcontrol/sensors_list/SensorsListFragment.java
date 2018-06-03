package fr.eisti.sensorcontrol.sensors_list;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.connectivity.GetRequest;
import fr.eisti.sensorcontrol.model.Sensor;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsActivity;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsFragment;

/**
 * Created by eisti on 12/12/17.
 */

public class SensorsListFragment extends Fragment {
    //static String to be updated directly for asynchronous method
    public static String serverResponse = "";
    //fragment widgets
    private ListView sensorsList;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sensors_list_fragment_layout,
                container, false);

        //initializing widgets
        sensorsList = (ListView) view.findViewById(R.id.sensors_list_listview);

        new SimpleGetRequest(getActivity()).execute("http://sensors.getsandbox.com/sensors_list");

        return view;
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
                JSONArray jsonarray = new JSONArray(response);
                //temp array to store sensors
                final ArrayList<Sensor> sensors = new ArrayList<Sensor>();
                //temp array list to be passed to the list view adapter
                ArrayList<String> sensorNames = new ArrayList<String>();

                //initializing the list view
                for (int i = 0; i < jsonarray.length(); i++) {
                    //get sensor properties from json response
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String name = jsonobject.getString("name");
                    String isOn = jsonobject.getString("isOn");
                    String sensorClass = jsonobject.getString("class");
                    String location = jsonobject.getString("location");

                    //create sensor object
                    Sensor sensor = new Sensor(name, approve(isOn), sensorClass, location);
                    //adding a sensor to the array
                    sensors.add(sensor);
                    //adding a name to the names array
                    sensorNames.add(name);
                }
                //pass the names array to the list view
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, sensorNames);
                sensorsList.setAdapter(arrayAdapter);
                sensorsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        //as both arrays are created simultaneously there is the same indexing
                        Sensor sensor = sensors.get(position);
                        //send sensor session variable
                        SensorDetailsFragment.sensor = sensor;
                        Intent intent = new Intent(getActivity(), SensorDetailsActivity.class);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private boolean approve(String isOn) {
            boolean output = false;

            if (isOn.trim().toLowerCase().equals("yes")) {
                output = true;
            } else if (isOn.trim().toLowerCase().equals("yes")) {
                output = false;
            }

            return output;
        }
    }

}
