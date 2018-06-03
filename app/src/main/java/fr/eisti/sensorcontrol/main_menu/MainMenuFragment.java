package fr.eisti.sensorcontrol.main_menu;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.model.User;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsActivity;
import fr.eisti.sensorcontrol.sensors_list.SensorsListActivity;
import fr.eisti.sensorcontrol.user_profile.UserProfileActivity;
import fr.eisti.sensorcontrol.welcome.WelcomeActivity;

/**
 * Created by eisti on 11/12/17.
 */

public class MainMenuFragment extends Fragment {
    //widget views
    TextView getSensorsList;
    TextView userProfile;
    TextView greeting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_fragment_layout, container, false);
        //initializing widgets
        getSensorsList  = (TextView) view.findViewById(R.id.main_menu_sensors_list);
        userProfile = (TextView) view.findViewById(R.id.main_menu_profile);
        greeting = (TextView) view.findViewById(R.id.main_menu_greeting);

        //set the greeting message
        greeting.setText("Welcome, " + MainMenuActivity.user.getName());

        new SimpleGetRequest(getActivity()).execute("http://sensors.getsandbox.com/users");

        //setting links to text views
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sending session variable to the next activitu
                UserProfileActivity.user = MainMenuActivity.user;
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                startActivity(intent);
            }
        });

        getSensorsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SensorsListActivity.class);
                startActivity(intent);
            }
        });

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
                final ArrayList<User> users = new ArrayList<User>();

                //initializing the admins array
                //only admins have access to manipulate sensors
                for (int i = 0; i < jsonarray.length(); i++) {
                    //get sensor properties from json response
                    JSONObject jsonobject = jsonarray.getJSONObject(i);
                    String name = jsonobject.getString("name");
                    String surname = jsonobject.getString("surname");
                    String position = jsonobject.getString("position");
                    String email = jsonobject.getString("email");
                    String login = jsonobject.getString("login");
                    String password = jsonobject.getString("password");

                    User user = new User(name, surname, position, email, login, password);
                    users.add(user);
                }

                for (User admin : users) {
                    //if the user is in the admins list then allow user to manipulate sensors
                    if ((admin.getName().equals(MainMenuActivity.user.getName()))
                            && (admin.getSurname().equals(MainMenuActivity.user.getSurname())
                            && (admin.getPosition().equals(MainMenuActivity.user.getPosition())))) {
                        SensorDetailsActivity.hasAccess = true;
                        Toast.makeText(getActivity(), "User has been granted the root access",
                                Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
