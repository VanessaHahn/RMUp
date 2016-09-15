package mi.ur.de.android.runnersmeetup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Vanessa on 15.09.2016.
 */
public class PersonalDatabase {
    private static final String DATABASE_NAME = "personalData.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_TABLE = "runItems";

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_DISTANCE = "Strecke";
    public static final String KEY_TIME = "time";
    public static final String KEY_VELOCITY = "velocity";

    public static final int COLUMN_NAME_INDEX = 1;
    public static final int COLUMN_DISTANCE_INDEX = 2;
    public static final int COLUMN_TIME_INDEX = 3;
    public static final int COLUMN_VELOCITY_INDEX = 4;

    private DBOpenHelper dbHelper;

    private SQLiteDatabase db;

    public PersonalDatabase(Context context) {
        dbHelper = new DBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void open() throws SQLException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    public void close() {
        db.close();
    }

    public long runValues(RunItem item) {
        ContentValues newRunValues = new ContentValues();

        newRunValues.put(KEY_NAME, item.getName());
        newRunValues.put(KEY_DISTANCE, item.getDistance());
        newRunValues.put(KEY_TIME, item.getTime());
        newRunValues.put(KEY_VELOCITY, item.getVelocity());

        return db.insert(DATABASE_TABLE, null, newRunValues);
    }

    public void removeRunItem(RunItem item) {
        String whereClause = KEY_NAME + "='" + item.getName() + "' AND " + KEY_DISTANCE + " = '" + item.getDistance() + "' AND "
                + KEY_TIME + " = '" + item.getTime() + "' AND " + KEY_VELOCITY + " = '" + item.getVelocity() + "'";

        db.delete(DATABASE_TABLE, whereClause, null);
    }

    public ArrayList<RunItem> getAllToDoItems() {
        ArrayList<RunItem> items = new ArrayList<RunItem>();
        Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ID,
                KEY_NAME, KEY_DISTANCE, KEY_TIME, KEY_VELOCITY }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(COLUMN_NAME_INDEX);
                int distance = Integer.parseInt(cursor.getString(COLUMN_DISTANCE_INDEX));
                String time = cursor.getString(COLUMN_TIME_INDEX);
                double velocity = Double.parseDouble(cursor.getString(COLUMN_VELOCITY_INDEX));

                items.add(new RunItem(name, velocity, distance, time));

            } while (cursor.moveToNext());
        }
        return items;
    }

    private class DBOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_CREATE = "create table "
                + DATABASE_TABLE + " (" + KEY_ID
                + " integer primary key autoincrement, " + KEY_NAME + "text not null, " + KEY_DISTANCE
                + " text not null, " + KEY_TIME + " text not null, " + KEY_VELOCITY + "text not null, ";

        public DBOpenHelper(Context c, String dbname, SQLiteDatabase.CursorFactory factory, int version) {
            super(c, dbname, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
