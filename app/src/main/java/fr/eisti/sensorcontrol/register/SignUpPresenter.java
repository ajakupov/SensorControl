package fr.eisti.sensorcontrol.register;

import android.content.Context;

import fr.eisti.sensorcontrol.db.UserDBHelper;

/**
 * Created by root on 04/02/18.
 */

public class SignUpPresenter {
    private Context context;
    public SignUpPresenter(Context context) {
        this.context = context;
    }

    public boolean signUp (String name, String surname, String email, String position,
                           String login, String password) {
        UserDBHelper userDBHelper = new UserDBHelper(this.context);

        return userDBHelper.insertUser(name, surname, email, position, login, password);
    }
}
