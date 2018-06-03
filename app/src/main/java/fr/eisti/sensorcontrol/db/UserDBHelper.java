package fr.eisti.sensorcontrol.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import fr.eisti.sensorcontrol.model.User;

/**
 * Created by eisti on 10/12/17.
 *
 * SQL Helper class ipmlementing CRUD operations on User table
 */

public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sensorUsers.db";
    public static final int DB_VERSION = 1;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table SensorUser " +
                "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, surname TEXT, position TEXT, email TEXT, login TEXT, password TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists SensorUser");
        onCreate(db);
    }

    public boolean insertUser (String name, String surname, String email, String position,
                              String login, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("surname", surname);
        contentValues.put("position", position);
        contentValues.put("email", email);
        contentValues.put("login", login);
        contentValues.put("password", password);

        sqLiteDatabase.insert("SensorUser", null, contentValues);
        return true;
    }

    public int deleteDay(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        return sqLiteDatabase.delete("SensorUser", "name = ?", new String[] {name});
    }

    public boolean updateDay (String name, String surname, String email, String position,
                              String login, String password) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("surname", surname);
        contentValues.put("position", position);
        contentValues.put("email", email);
        contentValues.put("login", login);
        contentValues.put("password", password);

        sqLiteDatabase.update("User", contentValues, "name = ?", new String[] {name});
        return true;
    }

    public ArrayList<User> getAllUsers () {
        ArrayList<User> output = new ArrayList<User>();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor res = sqLiteDatabase.rawQuery("select * from SensorUser", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            String name = res.getString(res.getColumnIndex("name"));
            String surname = res.getString(res.getColumnIndex("surname"));
            String position = res.getString(res.getColumnIndex("position"));
            String email = res.getString(res.getColumnIndex("email"));
            String login = res.getString(res.getColumnIndex("login"));
            String password = res.getString(res.getColumnIndex("password"));

            User user = new User(name, surname, position, email, login, password);
            output.add(user);

            res.moveToNext();
        }

        return output;
    }
}
