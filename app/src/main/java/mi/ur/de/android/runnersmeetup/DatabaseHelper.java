package mi.ur.de.android.runnersmeetup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Medion on 24.09.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Runhistory.db";
    public static final String TABLE_NAME = "run_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TIME";
    public static final String COL_3 = "AVGE";
    public static final String COL_4 = "DISTANCE";
    public static final String COL_5 = "KCAL";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 4);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME INTEGER, AVGE DOUBLE, DISTANCE DOUBLE, KCAL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int time, double avGe, double distance, int kcal){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,time);
        contentValues.put(COL_3,avGe);
        contentValues.put(COL_4,distance);
        contentValues.put(COL_5,kcal);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME,null);
        return res;
    }

    /*public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }*/


}
















