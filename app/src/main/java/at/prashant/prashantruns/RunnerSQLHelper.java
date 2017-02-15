package at.prashant.prashantruns;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by prashant on 2/1/17.
 */

public class RunnerSQLHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME = "activities";

    public static final String[] columns = {"_id", "input_type", "activity_type", "date_time",
            "duration", "distance", "avg_pace", "avg_speed", "calories", "climb", "heartrate",
            "comment", "privacy", "gps_data"};

    private static final String SQL_CREATE_ENTRIES =
            " CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, \n" +
                    "input_type INTEGER NOT NULL, \n" +
                    "activity_type INTEGER NOT NULL, \n" +
                    "date_time DATETIME NOT NULL, \n" +
                    "duration INTEGER NOT NULL, \n" +
                    "distance FLOAT, \n" +
                    "avg_pace FLOAT, \n" +
                    "avg_speed FLOAT,\n" +
                    "calories INTEGER, \n" +
                    "climb FLOAT, \n" +
                    "heartrate INTEGER, \n" +
                    "comment TEXT, \n" +
                    "privacy INTEGER,\n" +
                    "gps_data BLOB );";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static String DATABASE_NAME = "PrashantRuns.db";
    public static int DATABASE_VERSION = 1;
    public RunnerSQLHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /*
     Not sure if I will use this method.
     */
    public void insertEntry(ExerciseEntry entry) {
        SQLiteDatabase database = this.getWritableDatabase();
    }

    /*
     Calling this method from the History fragment.
     */
    public void removeEntry(long rowIndex) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE from " + TABLE_NAME + " where _id = "+ rowIndex + ";");
        database.close();
    }

    public ExerciseEntry fetchEntryByIndex(long rowId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME +
                " where _id = \""+ rowId + "\";", null);
        cursor.moveToNext();
        ExerciseEntry e = new ExerciseEntry();
        e.setId(cursor.getLong(0));
        e.setmInputType(cursor.getInt(1));
        e.setmActivityType(cursor.getInt(2));
        Calendar calendar= GregorianCalendar.getInstance();
        calendar.setTimeInMillis(cursor.getLong(3));
        e.setmDateTime(calendar);
        e.setmDuration(cursor.getInt(4));
        e.setmDistance(cursor.getInt(5));
        e.setmAvgPace(cursor.getFloat(6));
        e.setmAvgSpeed(cursor.getFloat(7));
        e.setmCalorie(cursor.getInt(8));
        e.setmClimb(cursor.getInt(9));
        e.setmHeartRate(cursor.getInt(10));
        e.setmComment(cursor.getString(11));
        e.setLocationByte(cursor.getBlob(13));
        return e;
    }

    public ArrayList<ExerciseEntry> fetchEntries() {
        ArrayList<ExerciseEntry> entries = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ RunnerSQLHelper.TABLE_NAME, null);
        while(cursor.moveToNext())
        {
            ExerciseEntry e = new ExerciseEntry();
            e.setId(cursor.getLong(0));
            e.setmInputType(cursor.getInt(1));
            e.setmActivityType(cursor.getInt(2));
            Calendar calendar= GregorianCalendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(3));
            e.setmDateTime(calendar);
            e.setmDuration(cursor.getInt(4));
            e.setmDistance(cursor.getInt(5));
            e.setmAvgPace(cursor.getFloat(6));
            e.setmAvgSpeed(cursor.getFloat(7));
            e.setmCalorie(cursor.getInt(8));
            e.setmClimb(cursor.getInt(9));
            e.setmHeartRate(cursor.getInt(10));
            e.setmComment(cursor.getString(11));
            entries.add(e);
        }
        db.close();
        return entries;
    }
}
