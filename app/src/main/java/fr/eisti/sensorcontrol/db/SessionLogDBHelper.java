package fr.eisti.sensorcontrol.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import fr.eisti.sensorcontrol.model.Session;
import fr.eisti.sensorcontrol.model.User;

/**
 * Created by eisti on 10/12/17.
 *
 * SQL Helper class ipmlementing CRUD operations on Session Log table
 */

public class SessionLogDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "sensorLog.db";
    public static final int DB_VERSION = 1;

    public SessionLogDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table SessionLog " +
                "(log_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "body TEXT, user TEXT, insert_date TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists SessionLog");
        onCreate(db);
    }

    public boolean insertLog (String body, String user, String insert_date) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("body", body);
        contentValues.put("user", user);
        contentValues.put("insert_date", insert_date);

        sqLiteDatabase.insert("SessionLog", null, contentValues);
        return true;
    }

    public ArrayList<Session> getAllSessions () {
        ArrayList<Session> output = new ArrayList<Session>();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor res = sqLiteDatabase.rawQuery("select * from SessionLog", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            String body = res.getString(res.getColumnIndex("body"));
            String user = res.getString(res.getColumnIndex("user"));
            String insert_date = res.getString(res.getColumnIndex("insert_date"));

            Session session = new Session(body, user, insert_date);
            output.add(session);

            res.moveToNext();
        }

        return output;
    }
}
