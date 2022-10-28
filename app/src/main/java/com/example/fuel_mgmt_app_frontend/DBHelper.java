package com.example.fuel_mgmt_app_frontend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DBNAME = "Registration.db";

    public DBHelper(Context context) {
        super(context, "Registration.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table users(email TEXT primary key, password TEXT,isLoging TEXT)");
    }
//    public void onCreate(SQLiteDatabase DB) {
//        DB.execSQL("create Table users(email TEXT primary key, password TEXT,isFuelStation BOOLEAN,fuelStationName TEXT,district TEXT)");
//    }
//

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists users");
    }

    public boolean insertData(String email,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("isLoging","false");
        long results = DB.insert("users",null,contentValues);
        if (results == -1)
            return false;
        else
            return true;
    }
//    public boolean insertData(String email,String password,boolean isFuelStation,String fuelStationName,String district){
//        SQLiteDatabase DB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("email",email);
//        contentValues.put("password",password);
//        contentValues.put("isFuelStation",isFuelStation);
//
//        long results = DB.insert("users",null,contentValues);
//        if (results == -1)
//            return false;
//        else
//            return true;
//    }

    public boolean checkEmail(String email){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where email = ?",new String[]{email});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public boolean checkEmailPassword(String email,String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where email = ? and password = ?",new String[]{email,password});

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public boolean loging(String email){
                SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where email = ?",new String[]{email});
        ContentValues contentValues = new ContentValues();
//        contentValues.put("email",email);
//        contentValues.put("password",password);
        contentValues.put("isLoging","true");
        if(cursor.getCount()>0){
            long results = DB.update("users",contentValues,"email = ?",new String[] {email});
            if (results == -1)
                return false;
            else
                return true;
        }
       else{
           return false;
        }
    }
    public boolean logedOut(){
        String islog = "true";
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from users where isLoging = ?",new String[]{islog});
        Log.d("maxScore", String.valueOf(cursor.getCount()));
        ContentValues contentValues = new ContentValues();
//        contentValues.put("email",email);
//        contentValues.put("password",password);
        contentValues.put("isLoging","false");
        if(cursor.getCount()>0) {
            long results = DB.update("users", contentValues, "isLoging = ?", new String[]{islog});
            if (results == -1)
                return false;
            else
                return true;
        }
        else{
            return false;
        }
    }
    public String logingEmail(){
        String islog = "true";
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select email from users where isLoging = ?",new String[]{islog});

        if(cursor.getCount()==1) {
            Log.d("loging email", String.valueOf(cursor.getCount()));
            int i = cursor.getColumnIndex("email");
//            Log.d("loging email column", cursor.getString(i));
            if (cursor.moveToFirst()){
                return cursor.getString(0);
            }
            else
                return null;
        }
        else
            return null;
    }
}
