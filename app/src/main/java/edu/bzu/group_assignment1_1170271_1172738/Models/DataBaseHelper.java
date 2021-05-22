package edu.bzu.group_assignment1_1170271_1172738.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Database Settings: Name and Version
    private static final String DATABASE_NAME = "COURSE";


    //Table Names
    private static final String TABLE_USERS = "USERS";


    //Common Column Names
    private static final String ID_COL = "ID";
    private static final String USER_ID_COL = "USER_ID";


    //Customers Table Column Names
    private static final String EMAIL_COL = "EMAIL_ADDRESS";
    private static final String HASHED_PASSWORD_COL = "PASSWORD_HASH";
    private static final String FIRSTNAME_COL = "FIRSTNAME";
    private static final String LASTNAME_COL = "LASTNAME";



    //SQL Statements for Tables Creation
    String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL_COL + " TEXT NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + FIRSTNAME_COL + " TEXT NOT NULL," + LASTNAME_COL + " TEXT NOT NULL)";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(EMAIL_COL, user.getEmailAddress());
        contentValues.put(HASHED_PASSWORD_COL, user.getHashedPassword());
        contentValues.put(FIRSTNAME_COL, user.getFirstName());
        contentValues.put(LASTNAME_COL, user.getLastName());

        long result;
        if ((result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues)) == -1)
            return false;

        else
            return true;

    }

    public User getUserByEmailAddress(String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{ID_COL, EMAIL_COL, HASHED_PASSWORD_COL,
                        FIRSTNAME_COL, LASTNAME_COL},
                EMAIL_COL + "=?", new String[]{emailAddress}, null, null, null);

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setEmailAddress(cursor.getString(1));
            user.setHashedPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            return user;
        }

        return null;
    }

    public boolean userAlreadyExists(String emailAddress) {
        return (getUserByEmailAddress(emailAddress) != null);
    }


}
