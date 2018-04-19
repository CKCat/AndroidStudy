package com.cat.fileoperator;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class SqlActivity extends Activity {

    private MyOpenHelper oh;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sql_layout);
        oh = new MyOpenHelper(this, "people.db", null, 1);
        db = oh.getWritableDatabase();
        //oh.getReadableDatabase();一般使用getWritableDatabase就可以了
    }
    public void Onclick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_insert:
                insertApi();
                break;
            case R.id.btn_delete:
                deleteApi();
                break;
            case R.id.btn_update:
                updateApi();
                break;
            case R.id.btn_query:
                selectApi();
                break;
            case R.id.btn_insert_:
                insert_();
                break;
            case R.id.btn_show:
                Intent intent = new Intent(this, ShowActivity.class);
                startActivity(intent);
                break;
        }
    }

    //使用sql语句对数据库进行操作
    public void insert(){
        db.execSQL("insert into person (name, salary, phone) values (?, ?, ?)",
                new Object[]{"张三", 14000, "138888"});
        db.execSQL("insert into person (name, salary, phone) values (?, ?, ?)",
                new Object[]{"李四", 10000, "136789"});
        db.execSQL("insert into person (name, salary, phone) values (?, ?, ?)",
                new Object[]{"王五", 24000, "123456"});
    }

    public void delete(){
        db.execSQL("delete from person where name = ?", new Object[]{"张三"});
    }

    public void update(){
        db.execSQL("update person set salary = ? where name = ? ", new Object[]{"24500", "王五"});
    }

    public void select(){
        Cursor cursor = db.rawQuery("select name, salary from person", null);

        while (cursor.moveToNext()){
            //两种通过索引的方式获取列的值
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String salary = cursor.getString(1);
            System.out.println(name + ";" + salary);
        }
    }

    //使用API对数据库进行操作
    public void insertApi(){
        //把要插入的数据封装至ContentValues对象中
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("phone", "123666");
        values.put("salary", 16000);
        db.insert("person", null, values);
    }

    public void deleteApi(){
        int i = db.delete("person", "name = ? and _id = ?", new String[]{"张三", "3"});
        System.out.println(i);
    }

    public void updateApi(){
        ContentValues values = new ContentValues();
        values.put("salary", 26000);
        int i = db.update("person", values, "name = ?", new String[]{"李四"});
        System.out.println(i);
    }

    public void selectApi(){
        Cursor cursor = db.query("person", null, null, null, null, null, null, null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));
            String salary = cursor.getString(cursor.getColumnIndex("salary"));
            System.out.println(name + ";" + phone + ";" + salary);
        }
    }

    //事务
    public void transaction(){
        try{
            //开启事务
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("salary", 12000);
            db.update("person", values, "name = ?", new String[]{"张三"});

            values.clear();//当重新使用ContentValues时，最好进行clear
            values.put("salary", 15000);
            db.update("person", values, "name = ?", new String[]{"李四"});

            int i = 3/0;
            //设置  事务执行成功
            db.setTransactionSuccessful();
        }
        finally{
            //关闭事务，同时提交，如果已经设置事务执行成功，那么sql语句就生效了，反之，sql语句回滚
            db.endTransaction();
        }
    }

    public void insert_(){
        //把要插入的数据全部封装至ContentValues对象
        for (int i = 0; i < 50; i++) {
            ContentValues values = new ContentValues();
            values.put("name", "赵"+i);
            values.put("phone", "159"+i+i);
            values.put("salary", "160"+i+i);
            db.insert("person", null, values);
        }
    }
}
