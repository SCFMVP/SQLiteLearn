package com.example.administrator.sqlitelearn.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 项目名称：SQLiteLearn
 * 创建人：later
 * 创建时间：2018/10/10
 * 修改备注：
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test.db";  //数据库名称
    private static final int DATABASE_VERSION = 1;          //数据库版本
    //public static final String SqlCreatTable = "create table student ("
     //       + "number integer primary key, "
     //       + "gender text , "
     //       + "name text,"
     //       + "birth text,"
     //       + "native_place text,"
     //       + "specialty text,"
     //       + "phone text)";
    /**
     * 构造方法
     * @param context
     */
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    /*
    以下是继承方法 --> 重写
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR,age INTEGER, info TEXT)");//建表person,字段和字段类型, 对数据库进行操作等
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person COLUMN other STRING");
    }
}