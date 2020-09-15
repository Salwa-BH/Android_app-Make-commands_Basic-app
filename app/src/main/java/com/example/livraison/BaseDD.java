package com.example.livraison;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseDD extends SQLiteOpenHelper{
    public static final String Database_name="Personnes.db";
    public static final String Table_name="Personnes_Table";
    public static final String Col_1="ID";
    public static final String Col_2="USERNAME";
    public static final String Col_3="EMAIL";
    public static final String Col_4="PASSWORD";
    public BaseDD(Context context){
        super(context,Database_name,null,1);
        SQLiteDatabase db=this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ Table_name+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,EMAIL TEXT,PASSWORD TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Table_name);
        onCreate(db);
    }
    public boolean insertData(String username,String email,String password){
        SQLiteDatabase bdd= this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Col_2,username);
        contentValues.put(Col_3,email);
        contentValues.put(Col_4,password);
        long res=bdd.insert(Table_name,null,contentValues);
        if(res==-1)
            return false;
        else
            return true;
    }

    public void close(){
        SQLiteDatabase bdd= this.getWritableDatabase();
        if(bdd != null){
            bdd.close();
        }
    }
    public boolean checkUserExist(String username, String password){
        String[] columns = {"username"};
        SQLiteDatabase db= this.getWritableDatabase();

        String selection = "username=? and password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(Table_name, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }
}