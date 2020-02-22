package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDB extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "notes";
    public static final String CONTENT = "content";
    public static final String PATH = "path";
    public static final String VIDEO = "video";
    public static final String ID = "_id";//每一个数据需要一个ID
    public static final String TIME = "time";//时间提示

    public NotesDB(Context context) {

        super(context, "notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
                + " TEXT NOT NULL," + PATH + " TEXT NOT NULL," + VIDEO
                + " TEXT NOT NULL," + TIME + " TEXT NOT NULL)");
    }
//大小写都可，每个类型前都要加空格
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// 更新，暂时不用
    }
}