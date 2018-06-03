package fr.eisti.sensorcontrol.welcome;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import fr.eisti.sensorcontrol.main_menu.MainMenuActivity;
import fr.eisti.sensorcontrol.model.Sensor;
import fr.eisti.sensorcontrol.register.SignUpActivity;
import fr.eisti.sensorcontrol.db.UserDBHelper;
import fr.eisti.sensorcontrol.model.User;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsActivity;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsFragment;

//application entry point
public class WelcomeActivity extends AppCompatActivity {
    //activity fragments
    WelcomeFragment welcomeFragment;

    //welcome screen widgets
    private EditText welcome_login;
    private EditText welcome_password;
    private Button welcome_signin;
    private TextView welcome_signup;

    //variable indicating whether the user is registered
    private boolean isRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /*//initializing fragment
        welcomeFragment = new WelcomeFragment();

        //check whether activity supports fragment layout
        if (findViewById(R.id.welcome_fragment_container) != null) {
            //avoid overlapping fragments if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }
            //pass any special instruction from intent
            welcomeFragment.setArguments(getIntent().getExtras());
            //add the fragment to the fragment container
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.welcome_fragment_container, welcomeFragment).commit();
        }*/

        //initializing widgets
        welcome_login = (EditText) findViewById(R.id.welcome_login);
        welcome_password = (EditText) findViewById(R.id.welcome_password);
        welcome_signin = (Button) findViewById(R.id.welcome_signin);
        welcome_signup = (TextView) findViewById(R.id.welcome_signup);

        //check if the user is registered (stored in the db)
        welcome_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get login & password
                String login = welcome_login.getText().toString();
                String password = welcome_password.getText().toString();
                //create db helper instance
                UserDBHelper userDBHelper = new UserDBHelper(WelcomeActivity.this);
                /*userDBHelper.insertDay("Test", "Testson", "test@test.com", "manager",
                        "testUser", "123");*/
                //get all the users
                ArrayList<User> users = userDBHelper.getAllUsers();
                //looking for the login and password in the database
                Log.i("Login", login);
                Log.i("Pass", password);
                for (User user : users) {
                    if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                        isRegistered = true;
                        //pass the current user instance to the next activity
                        MainMenuActivity.user = user;
                        //and stop the search
                        break;
                    }
                }

                if (isRegistered) {
                    Intent intent = new Intent(WelcomeActivity.this, MainMenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(WelcomeActivity.this, "Failure", Toast.LENGTH_LONG).show();
                }
            }
        });

        //link redirecting to the sign up page
        welcome_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
