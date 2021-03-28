package com.example.ungdungsolienlac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public abstract class DBManager extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="data";
    private static final String TABLE_NAME="contact";
    private static final String ID="id";
    private static final String NAME="name";
    private static final String NUMBER="number";
    private final Context context;

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log log = null;
            log.d("DBManager", "DBManager: ");
        this.context =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = String.format("CRETE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
                " %s TEXT,%s TEXT)",TABLE_NAME,ID,NAME,NUMBER);
        db.execSQL(sqlQuery);
        Toast.makeText(context,"Create successfylly",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(("DROP TABLE IF EXISTS") + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context,"Dorp successfylly",Toast.LENGTH_SHORT).show();
    }
    //thêm một contact mới
    public void themContact(Contact contact){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME ,contact.getHoTen());
        values.put(NUMBER ,contact.getSoDT());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    //hiển thị tên một contact
    public Contact getContactByName(String ten){
        Contact contact =new Contact();
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor =null;
        cursor = db.query(TABLE_NAME,new String[]{NAME,NUMBER},NAME+
                "=?",new String[]{ten},null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            contact.setHoTen(cursor.getString(0));
            contact.setSoDT(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return contact;
    }
//    public boolean checkContact(Contact contact){
//
//    }
}
