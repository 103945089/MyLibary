package shopping.hlhj.com.mylibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    public static final String DB_NAME = "history.db";
    public static final String TABLE_NAME = "table_history";
    public static final int DB_VERSION = 1;
    public static final String NAME = "name";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        database = this.getWritableDatabase();
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_NAME + "(_id integer primary key autoincrement, " + NAME + " varchar"  + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void add(String name){
        ContentValues values = new ContentValues();
        values.put(NAME,name);
        database.insert(TABLE_NAME,null,values);
        values.clear();
    }

    /**
     * 通过搜索的字节删除
     *
     * @param
     */
    public void delByName(String name) {
//        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + NAME + " = " + name;
//        database.execSQL(sql);
        database.delete(TABLE_NAME,name,null);
    }

    /**
     * 清空
     */
    public void delAll() {
        database.execSQL("DELETE  FROM " + TABLE_NAME);
    }

    /**
     * 查询所有数据
     *
     * @return List<Teleplay>
     */
    public List<String> findAll() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            list.add(curToStu(cursor));
        }
        return list;
    }

    private String curToStu(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(NAME));
        return s;
    }
}
