package com.upvmaster.carlos.recetor.bbdd;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.upvmaster.carlos.recetor.R;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Carlos on 07/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper{

    private static final String NOMBRE_BBDD = "recetor.sqlite";
    private static final int VERSION_BBDD = 1;

    private static DBHelper sInstance;
    private static Context context;


    public static synchronized DBHelper getInstance(Context context2) {
        if (sInstance == null) {
            // Use the application context, which will ensure that you don't accidentally leak an activity's context
            context = context2;
            sInstance = new DBHelper(context2.getApplicationContext());
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
        db.execSQL(TReceipt.CREATE_TABLE);
        db.execSQL(TIngredientes.CREATE_TABLE);
        db.execSQL(TSteps.CREATE_TABLE);
        db.execSQL(TVariants.CREATE_TABLE);
        db.execSQL(TGroups.CREATE_TABLE);
        //INSERT para tener información
        db.execSQL(TReceipt.INSERT_EX);
        db.execSQL(TIngredientes.INSERT_EX);
        db.execSQL(TSteps.INSERT_EX);
        db.execSQL(TVariants.INSERT_EX);
        db.execSQL(TGroups.INSERT_EX);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Do nothing
    }

    public static SQLiteDatabase getDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }
}
