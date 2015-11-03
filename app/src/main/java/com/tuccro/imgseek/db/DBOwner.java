package com.tuccro.imgseek.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by tuccro on 11/3/15.
 */
public class DBOwner {

    Context mContext;

    SQLiteDatabase mDB;

    public DBOwner(Context mContext) {
        this.mContext = mContext;
    }

    public void openConnection() {
        mDB = (new DBHelper(mContext)).getWritableDatabase();
    }

    public void closeConnection() {
        mDB.close();
    }
}
