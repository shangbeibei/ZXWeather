package com.shangbeibei.zxweather.common.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/1/18.
 */
public class DBHelper extends SQLiteOpenHelper {
    public final static String DATABASE_NAME = "zxweather.db";

    public final static String WEATHER_PROVINCES_TABLE = "weather_provinces";
    public final static String WEATHER_CITYS_TABLE = "weather_citys";
    private final static int DBTABASE_VERSION = 1;//  数据库版本号，用来升级用

    private final static String CREATE_WEATHER_PROVINCES_SQL = "CREATE TABLE "
            + WEATHER_PROVINCES_TABLE
            + "(_id Integer primary key autoincrement,"
            + "name text,"
            + "province_id integer);";

    private final static String CREATE_WEATHER_CITYS_SQL = "CREATE TABLE "
            + WEATHER_CITYS_TABLE
            + "(_id Integer primary key autoincrement,"
            +"province_id integer,"
            + "name text,"
            + "city_num integer);";

    public DBHelper(Context context) {

        super(context,DATABASE_NAME,null,DBTABASE_VERSION);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 初次建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WEATHER_PROVINCES_SQL);
        db.execSQL(CREATE_WEATHER_CITYS_SQL);

    }

    /**
     *升级数据库  方法  onUpgrade
     * @param db
     *               删除这个表
     * db.execSQL("DROP TABLE IF EXISTS " + WEATHER_CITYS_TABLE);
     *  并创建  onCreate(db);
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WEATHER_PROVINCES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WEATHER_CITYS_TABLE);
        onCreate(db);
    }
}
