package com.cat.fileoperator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库创建时，会调用此方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个id自动增长的person数据库
        db.execSQL("create table person(_id integer primary key autoincrement, name char(10), salary char(10), phone char(20))");
    }

    //数据库升级时，会调用此方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.print("数据库升级了");
    }
}
