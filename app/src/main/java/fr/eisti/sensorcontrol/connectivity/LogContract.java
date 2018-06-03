package fr.eisti.sensorcontrol.connectivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by eisti on 19/12/17.
 *
 * Contract class definig content provider settings
 */

public final class LogContract {
    public static final String AUTHORITY = "fr.eisti.sensorcontrol";
    public static final String BASE_PATH = "SessionLog";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
            + BASE_PATH;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/item";

    public static final class SessionLogs implements BaseColumns{
        public static final String TABLE_NAME = "SessionLog";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_USER = "user";
        public static final String COLUMN_DATE = "insert_date";
    }
}
