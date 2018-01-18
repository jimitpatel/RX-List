package app.jimit.www.rxlist.data.local;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jimit on 18-01-2018.
 */

public class DatabaseContract {

    public static final String CONTENT_AUTHORITY = "app.jimit.www.rxlist";
    private static final String CONTENT_SCHEME = "content://";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);
    public static final String PATH_CITY = "city";

    public DatabaseContract() {

    }

    public static abstract class City implements BaseColumns {

        public static final String CONTENT_URI_STRING = CONTENT_SCHEME + CONTENT_AUTHORITY + "/" + PATH_CITY;
        public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STRING);

        public static final String CONTENT_CITY_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CITY;
        public static final String CONTENT_CITY_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CITY;

        public static final String TABLE_NAME = "city";

        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SLUG = "slug";

        public static String getCityCreateQuery() {
            return "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_ID + " LONG NOT NULL PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_SLUG + " TEXT NOT NULL " +
                    ");";
        }

        public static String getCityDeleteQuery() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static Uri buildCityUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
