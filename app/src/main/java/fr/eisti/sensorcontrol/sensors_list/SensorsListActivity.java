package fr.eisti.sensorcontrol.sensors_list;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fr.eisti.sensorcontrol.R;
import fr.eisti.sensorcontrol.model.Sensor;

public class SensorsListActivity extends AppCompatActivity {
    //widget fragments
    private SensorsListFragment sensorsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors_list);

        //initializing fragment
        sensorsListFragment = new SensorsListFragment();

        //check whether activity supports fragment layout
        if (findViewById(R.id.sensors_list_container) != null) {
            //avoid overlapping fragments if restored from a previous state
            if (savedInstanceState != null) {
                return;
            }
            //pass any special instruction from intent
            sensorsListFragment.setArguments(getIntent().getExtras());
            //add the fragment to the fragment container
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.sensors_list_container, sensorsListFragment).commit();
        }
    }
}
