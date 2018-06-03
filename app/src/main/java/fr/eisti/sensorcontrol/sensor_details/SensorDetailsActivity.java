package fr.eisti.sensorcontrol.sensor_details;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.sensors_list.SensorsListFragment;

public class SensorDetailsActivity extends AppCompatActivity {
    //activity fragment
    SensorDetailsFragment sensorDetailsFragment;
    //static variable allowing access to the sensor manipulation only for authorized users
    public static boolean hasAccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);


        //initializing fragment
        sensorDetailsFragment = new SensorDetailsFragment();

        //check whether activity supports fragment layout
        if (findViewById(R.id.sensor_details_container) != null) {
            //avoid overlapping fragments if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }
            //pass any special instruction from intent
            sensorDetailsFragment.setArguments(getIntent().getExtras());
            //add the fragment to the fragment container
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.sensor_details_container, sensorDetailsFragment).commit();
        }
    }
}
