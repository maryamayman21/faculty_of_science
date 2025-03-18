package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    public UserDB(Context context ,String databaseName , int version){
        super(context , databaseName , null , version);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
          //creates table
            String createStatement = " create table users " + "("+
                    "username text," +
                    "useremail text primary key,"+
                    "password text);";
            sqLiteDatabase.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


   public boolean insert(Users user){



        //mechanism
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
       sqLiteDatabase.beginTransaction();
        try {

            String userName = user.getUserName();
            String userEmail = user.getUserEmail();
            String password = user.getPassword();

            String insertStatement = "insert into users(username , useremail , password )values('"+userName+"','"+userEmail+"','"+password+"');";

            sqLiteDatabase.execSQL(insertStatement);
            sqLiteDatabase.setTransactionSuccessful();
            return true;
        }catch (SQLException e){

        String error =   e.toString();

        }finally {
            sqLiteDatabase.endTransaction();
        }

   return false;

   }



   //when login  get user record by email
    //username email password
    //verify email , password  -> done -> get username set profile text fields
    // fail -> email not found , incorrect password

    public Cursor searchByEmail(String email) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String getUserRecordByEmailStatement = "SELECT username, password FROM users WHERE useremail = ?;";

        sqLiteDatabase.beginTransaction();
        try {
            // Use rawQuery with the email parameter
            Cursor records = sqLiteDatabase.rawQuery(getUserRecordByEmailStatement, new String[]{email});

            // Set transaction as successful
            sqLiteDatabase.setTransactionSuccessful();

            // Return the Cursor
            return records;
        } catch (SQLException e) {
            // Handle exceptions
        } finally {
            // End transaction
            sqLiteDatabase.endTransaction();
        }

        return null;
    }



}
