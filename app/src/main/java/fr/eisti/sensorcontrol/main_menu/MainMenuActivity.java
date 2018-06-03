package fr.eisti.sensorcontrol.main_menu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.model.User;

public class MainMenuActivity extends AppCompatActivity {
    //session variables troing the current user info
    public static User user;

    private MainMenuFragment mainMenuFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //initializing fragment
        mainMenuFragment = new MainMenuFragment();

        //check whether activity supports fragment layout
        if (findViewById(R.id.main_menu_container) != null) {
            //avoid overlapping fragments if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }
            //pass any special instruction from intent
            mainMenuFragment.setArguments(getIntent().getExtras());
            //add the fragment to the fragment container
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.main_menu_container, mainMenuFragment).commit();
        }
    }
}
