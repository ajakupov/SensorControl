package fr.eisti.sensorcontrol.connectivity;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;

import fr.eisti.sensorcontrol.db.SessionLogDBHelper;

/**
 * Created by eisti on 19/12/17.
 *
 * Class implementing insert and retrieve operations over the session log table (no delete
 * or update)
 *
 * and get type
 */

public class SessionLogProvider extends ContentProvider {
    private static final int ITEMS = 1;
    private static final int ITEM_ID = 2;
    private static UriMatcher uriMatcher;
    private static HashMap<String, String> SESSION_PROJECTION_MAP;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(LogContract.AUTHORITY, LogContract.BASE_PATH, ITEMS);
        uriMatcher.addURI(LogContract.AUTHORITY, LogContract.BASE_PATH + "/#", ITEM_ID);
    }

    private SessionLogDBHelper sessionLogDBHelper;

    @Override
    public boolean onCreate() {
        sessionLogDBHelper = new SessionLogDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable
            String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables("SessionLog");
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case ITEMS:
                sqLiteQueryBuilder.setProjectionMap(SESSION_PROJECTION_MAP);
            case ITEM_ID:
                sqLiteQueryBuilder.appendWhere("_ID=" + uri.getLastPathSegment());
                break;
            default:
        }
        SQLiteDatabase sqLiteDatabase = sessionLogDBHelper.getReadableDatabase();
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase, projection, selection,
                selectionArgs, null, null, sortOrder);
        if (cursor != null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ITEMS:
                return "vnd.android.cursor.dir/" + LogContract.AUTHORITY;
            case ITEM_ID:
                return "vnd.android.cursor.item/" + LogContract.AUTHORITY;
            default:
                throw new IllegalArgumentException("Usopported URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase sqLiteDatabase = sessionLogDBHelper.getReadableDatabase();
        int uriType = uriMatcher.match(uri);
        long id = 0;
        switch (uriType) {
            case ITEMS :
                id = sqLiteDatabase.insert("SessionLog", null, values);
                break;
                default: throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(LogContract.BASE_PATH + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
