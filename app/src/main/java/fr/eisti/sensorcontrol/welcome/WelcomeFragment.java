package fr.eisti.sensorcontrol.welcome;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.db.UserDBHelper;
import fr.eisti.sensorcontrol.model.User;

/**
 * Created by eisti on 10/12/17.
 */

public class WelcomeFragment extends Fragment {
    //welcome screen widgets
    private EditText welcome_login;
    private EditText welcome_password;
    private Button welcome_signin;
    private TextView welcome_signup;

    //variable indicating whether the user is registered
    private boolean isRegistered = false;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        //getting view
        View view = inflater.inflate(R.layout.welcome_fragment_layout, container, false);

        //initializing widgets
        welcome_login = (EditText) view.findViewById(R.id.welcome_login);
        welcome_password = (EditText) view.findViewById(R.id.welcome_password);
        welcome_signin = (Button) view.findViewById(R.id.welcome_signin);
        welcome_signup = (TextView) view.findViewById(R.id.welcome_signup);

        //check if the user is registered (stored in the db)
        welcome_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get login & password
                String login = welcome_login.getText().toString();
                String password = welcome_password.getText().toString();
                //create db helper instance
                UserDBHelper userDBHelper = new UserDBHelper(getActivity());
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
                    }
                }

                if (isRegistered) {
                    Toast.makeText(getActivity(), "Suceess" + login, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Failure " + login + password, Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
}
