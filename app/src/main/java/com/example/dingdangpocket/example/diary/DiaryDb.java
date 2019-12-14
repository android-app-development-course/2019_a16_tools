package com.example.dingdangpocket.example.diary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDb extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "diary";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String ID = "_id";
    public static final String TIME = "time";
    public DiaryDb(Context context) {
        super(context, "diary", null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql ="create table "+TABLE_NAME+" ( "+ ID + " integer primary key AUTOINCREMENT, "
                + CONTENT +" TEXT NOT NULL, "+ TITLE + " TEXT NOT NULL, " +
                TIME+" TEXT NOT NULL )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
