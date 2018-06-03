package fr.eisti.sensorcontrol.user_profile;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.model.User;

public class UserProfileActivity extends AppCompatActivity {
    //session variables storing user info
    public static User user;
    //view fragment(s)
    UserProfileFragment userProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //initializing fragment
        userProfileFragment = new UserProfileFragment();

        //check whether activity supports fragment layout
        if (findViewById(R.id.user_profile_container) != null) {
            //avoid overlapping fragments if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }
            //pass any special instruction from intent
            userProfileFragment.setArguments(getIntent().getExtras());
            //add the fragment to the fragment container
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.user_profile_container, userProfileFragment).commit();
        }
    }
}
