package app.jimit.www.rxlist.data.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by jimit on 18-01-2018.
 */

public class Provider extends ContentProvider {

    private static final int CITY_ITEM = 100;
    private static final int CITY_DIR = 101;

    private static final UriMatcher mUriMatcher = buildUriMatcher();
    private DatabaseHelper mDbHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DatabaseContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, DatabaseContract.PATH_CITY + "/#", CITY_ITEM);
        matcher.addURI(authority, DatabaseContract.PATH_CITY, CITY_DIR);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;
        switch (mUriMatcher.match(uri)) {
            case CITY_ITEM:
                retCursor = mDbHelper.getReadableDatabase().query(
                        DatabaseContract.City.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case CITY_DIR:
                retCursor = mDbHelper.getReadableDatabase().query(
                        DatabaseContract.City.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = mUriMatcher.match(uri);
        switch (match) {
            //Case for user
            case CITY_ITEM:
                return DatabaseContract.City.CONTENT_CITY_ITEM_TYPE;
            case CITY_DIR:
                return DatabaseContract.City.CONTENT_CITY_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Uri returnUri;
        switch (mUriMatcher.match(uri)) {
            //Case for Post
            case CITY_DIR:
                long _id = db.insert(DatabaseContract.City.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = DatabaseContract.City.buildCityUri(_id);
                else
                    throw new SQLException("Failed to insert row " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsDeleted;
        switch (mUriMatcher.match(uri)) {
            case CITY_DIR:
                rowsDeleted = db.delete(DatabaseContract.City.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        if (selection == null || 0 != rowsDeleted)
            getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int update;
        switch (mUriMatcher.match(uri)) {
            //Case for User
            case CITY_DIR:
                update = db.update(DatabaseContract.City.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI " + uri);
        }
        if (update > 0)
            getContext().getContentResolver().notifyChange(uri, null);
        return update;
    }
}
