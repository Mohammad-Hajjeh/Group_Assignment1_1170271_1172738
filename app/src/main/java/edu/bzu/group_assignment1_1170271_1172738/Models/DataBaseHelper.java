package edu.bzu.group_assignment1_1170271_1172738.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    //Database Settings: Name and Version
    private static final String DATABASE_NAME = "CDX";
    private static final int DATABASE_VERSION = 1;

    //Table Names
    private static final String TABLE_USERS = "USERS";
    private static final String TABLE_CARS = "CARS";
    private static final String TABLE_RESERVATIONS = "RESERVATIONS";
    private static final String TABLE_FAVORITES = "FAVORITES";

    //Common Column Names
    private static final String ID_COL = "ID";
    private static final String USER_ID_COL = "USER_ID";
    private static final String CAR_ID_COL = "CAR_ID";

    //Customers Table Column Names
    private static final String EMAIL_COL = "EMAIL_ADDRESS";
    private static final String HASHED_PASSWORD_COL = "PASSWORD_HASH";
    private static final String FIRSTNAME_COL = "FIRSTNAME";
    private static final String LASTNAME_COL = "LASTNAME";
    private static final String GENDER_COL = "GENDER";
    private static final String COUNTRY_COL = "COUNTRY";
    private static final String CITY_COL = "CITY";
    private static final String PHONE_NUMBER_COL = "PHONE_NUMBER";

    //Cars Table Column Names
    private static final String YEAR_COL = "YEAR";
    private static final String MAKE_COL = "MAKE";
    private static final String MODEL_COL = "MODEL";
    private static final String DISTANCE_COL = "DISTANCE";
    private static final String PRICE_COL = "PRICE";
    private static final String ACCIDENTS_COL = "HAD_ACCIDENTS";

    //Reservations Table Column Names
    private static final String DATE_COL = "DATE";
    private static final String TIME_COL = "TIME";


    //SQL Statements for Tables Creation
    String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + EMAIL_COL + " TEXT NOT NULL," +
            HASHED_PASSWORD_COL + " TEXT NOT NULL," + FIRSTNAME_COL + " TEXT NOT NULL," + LASTNAME_COL + " TEXT NOT NULL," +
            GENDER_COL + " TEXT NOT NULL," + COUNTRY_COL + " TEXT NOT NULL," + CITY_COL + " TEXT NOT NULL," + PHONE_NUMBER_COL + " TEXT NOT NULL)";

    String SQL_CREATE_TABLE_CARS = "CREATE TABLE " + TABLE_CARS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + YEAR_COL + " INTEGER NOT NULL," +
            MAKE_COL + " TEXT NOT NULL," + MODEL_COL + " TEXT NOT NULL," + DISTANCE_COL + " TEXT NOT NULL," + PRICE_COL + " INTEGER NOT NULL," +
            ACCIDENTS_COL + " BOOLEAN NOT NULL)";

    String SQL_CREATE_TABLE_RESERVATIONS = "CREATE TABLE " + TABLE_RESERVATIONS + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID_COL + " INTEGER NOT NULL," +
            CAR_ID_COL + " INTEGER NOT NULL," +
            DATE_COL + " TEXT NOT NULL," + TIME_COL + " TEXT NOT NULL)";

    String SQL_CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES + "(" + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT," + USER_ID_COL + " INTEGER NOT NULL,"
            + CAR_ID_COL + " INTEGER NOT NULL)";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_USERS);
        db.execSQL(SQL_CREATE_TABLE_CARS);
        db.execSQL(SQL_CREATE_TABLE_RESERVATIONS);
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
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
        contentValues.put(GENDER_COL, user.getGender());
        contentValues.put(COUNTRY_COL, user.getCountry());
        contentValues.put(CITY_COL, user.getCity());
        contentValues.put(PHONE_NUMBER_COL, user.getPhoneNumber());

        long result;
        if ((result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues)) == -1)
            return false;

        else
            return true;

    }

    public User getUserByEmailAddress(String emailAddress) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_USERS, new String[]{ID_COL, EMAIL_COL, HASHED_PASSWORD_COL,
                        FIRSTNAME_COL, LASTNAME_COL, GENDER_COL, COUNTRY_COL, CITY_COL, PHONE_NUMBER_COL},
                EMAIL_COL + "=?", new String[]{emailAddress}, null, null, null);

        if (cursor.moveToFirst()) {
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setEmailAddress(cursor.getString(1));
            user.setHashedPassword(cursor.getString(2));
            user.setFirstName(cursor.getString(3));
            user.setLastName(cursor.getString(4));
            user.setGender(cursor.getString(5));
            user.setCountry(cursor.getString(6));
            user.setCity(cursor.getString(7));
            user.setPhoneNumber(cursor.getString(8));

            return user;
        }

        return null;
    }

    public boolean userAlreadyExists(String emailAddress) {
        return (getUserByEmailAddress(emailAddress) != null);
    }


}
