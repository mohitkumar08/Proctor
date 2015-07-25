package com.proctor.App.database;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.proctor.App.model.Caategory;
import com.proctor.R;

import java.util.ArrayList;

import java.util.List;



public class DatabaseHelper extends SQLiteOpenHelper {
    protected Resources resources;
    protected Context ctx;
    protected static DatabaseHelper helper;
    public static String dirName;

    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    public DatabaseHelper(Context context) {

        this(context, context.getResources().getString(R.string.database_name), null, context.getResources().getInteger(R.integer.database_version));
        ctx = context;
        resources = ctx.getResources();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        createAllTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    /*
     * This Method basically get array of define sql query from DATABASE.XML and
     * create tables
     */
    public void createAllTable(SQLiteDatabase db) {
        try {
            Log.e("Create Table", "create table");
            String[] query_array = resources.getStringArray(R.array.create_app_tables);

           db.execSQL("DROP TABLE IF EXISTS Question" );
            db.execSQL("DROP TABLE IF EXISTS Location" );
            db.execSQL("DROP TABLE IF EXISTS Audit" );

            for (String query : query_array) {
                Log.e("tables created ", query);
                db.execSQL(query);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*
     * This Method basically get array of define sql query from DATABASE.XML and
     * drop tables
     */
    public Cursor getSumOfRow(SQLiteDatabase db, int id) {
        Cursor cursor = null;
        try {
            String query = "SELECT SUM(ISCHECKED) FROM Inspection where DASHBOARDID='" + id + "'";
            cursor = db.rawQuery(query, null);
Log.e("sum of rows",DatabaseUtils.dumpCursorToString(cursor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public List<Caategory> getCategoryStatus(SQLiteDatabase db, int id) {
        Cursor cursor = null;
        String query = "SELECT DISTINCT CATEGORY FROM Inspection where DASHBOARDID='" + id + "'";
        cursor = db.rawQuery(query, null);
        List<Caategory> mapList = new ArrayList<Caategory>();
        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndex("CATEGORY"));
            String query1 = "SELECT SUM(ISCHECKED) CATEGORY FROM Inspection where DASHBOARDID='" + id + "' AND  CATEGORY='" + category + "'";
            Cursor cursor1 = db.rawQuery(query1, null);
            Caategory cat = new Caategory();

            while (cursor1.moveToNext()) {
                cat.setCategory(category);
                cat.setAttempt(cursor1.getInt(0));

            }

            mapList.add(cat);

        }
        return mapList;
    }

    public void getAttemptedCount(SQLiteDatabase db, int dashboardid,String category )
    {
        String query1 = "SELECT COUNT(ISCHECKED) FROM Inspection where DASHBOARDID='" + dashboardid + "' AND  CATEGORY='" + category + "'";
    }
    public void getAllQuestion(SQLiteDatabase db, int dashboardid,String category )
    {
        String query1 = "SELECT COUNT(*) FROM Inspection where DASHBOARDID='" + dashboardid + "' AND  CATEGORY='" + category + "'";
    }

    public List<Caategory> getCategoryQuestionStatus(SQLiteDatabase db, int id) {


          Cursor distinct_cursor = null;
        String distinct_query = "SELECT DISTINCT CATEGORY FROM Inspection where DASHBOARDID='" + id + "'";
        distinct_cursor = db.rawQuery(distinct_query, null);
        List<Caategory> mapList = new ArrayList<Caategory>();
//Log.e("database dump",)
        while (distinct_cursor.moveToNext()) {
            String category = distinct_cursor.getString(distinct_cursor.getColumnIndex("CATEGORY"));

            int val = 0;
            String isChecked_query = "SELECT COUNT(*) FROM Inspection where DASHBOARDID='" + id + "' AND  CATEGORY='" + category + "' AND ISCHECKED='"+val+"'";
            Cursor isChecked_cursor1 = db.rawQuery(isChecked_query, null);

            String total_query = "SELECT COUNT(*) FROM Inspection where DASHBOARDID='" + id + "' AND  CATEGORY='" + category + "'";
            Cursor total_cursor1 = db.rawQuery(total_query, null);

            Caategory cat = new Caategory();
            cat.setAttempt(0);
            if(isChecked_cursor1.moveToFirst())
            {
                cat.setAttempt(isChecked_cursor1.getInt(0));
            }
            if (total_cursor1.moveToFirst()) {
                cat.setCategory(category);
                cat.setCountQuestion(total_cursor1.getInt(0));
            }
            int val1 = 1;
            String score_query ="SELECT * FROM Inspection where DASHBOARDID='" + id + "' AND  CATEGORY='" + category + "' AND ATTEMPT='"+val1+"'";
            Cursor score_cursor = db.rawQuery(score_query, null);
            int scoreCount =100;
            while (score_cursor.moveToNext())
            {
                scoreCount = scoreCount- score_cursor.getInt(score_cursor.getColumnIndex("SCORE"));
            }
            cat.setScore(scoreCount);



            mapList.add(cat);
        }
        return mapList;
    }

    public  int isCompleted(SQLiteDatabase db, int id)
    {
        int val =1;
        int returnValue = 1;
        String query = "SELECT COUNT(*) FROM Inspection where DASHBOARDID='" + id + "' AND ISCHECKED='"+val+"'";
        Cursor cursor = db.rawQuery(query, null);
Log.e("lisststs",DatabaseUtils.dumpCursorToString(cursor));
        if (cursor.moveToNext() & cursor!=null)
        {
            returnValue = cursor.getInt(0);
        }

        return returnValue;
    }

}

