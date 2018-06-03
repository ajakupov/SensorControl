package fr.eisti.sensorcontrol.user_profile;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.eisti.sensorcontrol.R;

/**
 * Created by eisti on 11/12/17.
 */

public class UserProfileFragment extends Fragment {
    //View widgets
    private TextView profileName;
    private TextView profileSurname;
    private TextView profilePosition;
    private TextView profileEmail;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
        //initializing widgets
        profileName = (TextView) view.findViewById(R.id.profile_name);
        profileSurname = (TextView) view.findViewById(R.id.profile_surname);
        profilePosition = (TextView) view.findViewById(R.id.profile_position);
        profileEmail = (TextView) view.findViewById(R.id.profile_email);

        //setting table values
        profileName.setText(UserProfileActivity.user.getName());
        profileSurname.setText(UserProfileActivity.user.getSurname());
        profilePosition.setText(UserProfileActivity.user.getPosition());
        profileEmail.setText(UserProfileActivity.user.getEmail());

        return view;
    }
}
