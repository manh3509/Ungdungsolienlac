package com.example.ungdungsolienlac;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
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
        String sqlQuery = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT,"+
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
        cursor = db.query(TABLE_NAME,new String[]{NAME,NUMBER},NAME+ "=?",new String[]{ten},null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            contact.setHoTen(cursor.getString(0));
            contact.setSoDT(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return contact;
    }
    public boolean checkContact(Contact contact){
        String ht =contact.getHoTen();
        String dt =contact.getSoDT();
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor =null;
        cursor = db.query(TABLE_NAME,new String[]{NAME,NUMBER},NAME+
                " =? AND " + NUMBER + "=?",new String[]{ht,dt},null,null,null,null);
        int sl =cursor.getCount();
        cursor.close();
        db.close();
        return sl>0;
    }
    //thay đổi tên
    public int Update (Contact contact){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME ,contact.getHoTen());
        return db.update(TABLE_NAME,values,NAME+"=?",new String[]{String.valueOf(contact.getHoTen())});
    }
    //nhập tất cả contact
    public ArrayList<Contact> getAllContact()
    {
        ArrayList<Contact> listContact = new ArrayList<Contact>();
        String selectQuery ="SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do{
                Contact contact = new Contact(cursor.getString(1),cursor.getString(2));
                listContact.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return  listContact;
    }
    //xóa tên môt contact
    public void deteleContact(Contact contact){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_NAME,NAME + "=?" ,new String[]{String.valueOf(contact.getHoTen())});
        db.close();
    }
    //lấy những giá trị trong bản contact
    public int getContactsCoust(){
        String CountQuery = " SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db =this.getWritableDatabase();
        Cursor cursor = db.rawQuery(CountQuery, null);
        cursor.close();
        return cursor.getCount();
    }
}
