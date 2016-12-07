package com.upvmaster.carlos.recetor.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Carlos on 07/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String NOMBRE_BBDD = "certimage.sqlite";
    private static final int VERSION_BBDD = 1;

    private static DBHelper sInstance;


    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            // Use the application context, which will ensure that you don't accidentally leak an activity's context
            sInstance = new DBHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public static String getNombreBbdd() {
        return NOMBRE_BBDD;
    }

    private DBHelper(Context context) {
        super(context, NOMBRE_BBDD, null, VERSION_BBDD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA FOREIGN_KEYS = ON");
        db.execSQL("PRAGMA encoding=\"UTF-8\"");
        db.execSQL(TReceipt.CREATE_TABLE);
        db.execSQL(TIngredientes.CREATE_TABLE);
        db.execSQL(TSteps.CREATE_TABLE);
        db.execSQL(TVariants.CREATE_TABLE);
        db.execSQL(TGroups.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Do nothing
    }

    public static SQLiteDatabase getDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }
}
