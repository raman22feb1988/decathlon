package com.example.ipl2021;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class sqliteDB extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ipl2021.db";
    public Context con;

    public sqliteDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // TODO Auto-generated constructor stub
        con = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table teams(name text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS teams");
        onCreate(db);
    }

    public boolean insertTeam(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);

        db.insert("teams", null, contentValues);
        return true;
    }

    public ArrayList<String> getAllTeams() {

        ArrayList<String> teamList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM teams", null);

        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(0);

                teamList.add(data);
            } while (cursor.moveToNext());
        }
        return teamList;
    }

    public void deleteTeam(String name) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("teams", "name = ?",
                new String[] {name});
        db.close();
    }

    public int updateTeam(String old_name, String new_name) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", new_name);

        return db.update("teams", values, "name = ?",
                new String[] {old_name});
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
    }
}