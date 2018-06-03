package fr.eisti.sensorcontrol.register;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import fr.eisti.sensorcontrol.db.UserDBHelper;
import fr.eisti.sensorcontrol.model.Sensor;
import fr.eisti.sensorcontrol.model.User;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsActivity;
import fr.eisti.sensorcontrol.sensor_details.SensorDetailsFragment;
import fr.eisti.sensorcontrol.welcome.WelcomeActivity;

public class SignUpActivity extends AppCompatActivity {
    //screen widgets
    EditText signup_name;
    EditText signup_surname;
    EditText signup_postion;
    EditText signup_email;
    EditText signup_login;
    EditText signup_password;

    Button signup_submit;

    //widget values
    String name;
    String surname;
    String postion;
    String email;
    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //SQLite helper class
        final SignUpPresenter signUpPresenter = new SignUpPresenter(SignUpActivity.this);
        //initializing widgets
        signup_name = (EditText) findViewById(R.id.signup_name);
        signup_surname = (EditText) findViewById(R.id.signup_surname);
        signup_postion = (EditText) findViewById(R.id.signup_position);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_login = (EditText) findViewById(R.id.signup_login);
        signup_password = (EditText) findViewById(R.id.signup_password);

        signup_submit = (Button) findViewById(R.id.signup_submit);

        signup_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get widget values
                name = signup_name.getText().toString();
                surname = signup_surname.getText().toString();
                postion = signup_postion.getText().toString();
                email = signup_email.getText().toString();
                login = signup_login.getText().toString();
                password = signup_password.getText().toString();

                //save user in the database
                boolean isSaved = signUpPresenter.signUp(name, surname,
                        email, postion, login, password);
                if (isSaved) {
                    Toast.makeText(SignUpActivity.this, "User saved", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignUpActivity.this, "Error saving user",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
