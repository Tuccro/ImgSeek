package com.tuccro.imgseek.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.tuccro.imgseek.model.ImageDescriptor;

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

    public void addImageDescriptorToDB(ImageDescriptor descriptor) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBValues.DB_TABLE_IMG_TEXT, descriptor.getDescription());
        contentValues.put(DBValues.DB_TABLE_IMG_ICON, descriptor.getThumbnailUrl());
        contentValues.put(DBValues.DB_TABLE_IMG_ICON_LOCAL, descriptor.getThumbnailLocalUrl());
        contentValues.put(DBValues.DB_TABLE_IMG_IMAGE, descriptor.getImageUrl());
        contentValues.put(DBValues.DB_TABLE_IMG_IMAGE_LOCAL, descriptor.getImageLocalUrl());

        try {
            mDB.insert(DBValues.DB_TABLE_IMG, null, contentValues);
        } catch (Exception e) {
            Log.e("Can't add img to DB", e.getMessage());
        }
    }

    public Cursor getImageDescriptorsCursor() {
        return mDB.query(DBValues.DB_TABLE_IMG, null, null, null, null, null, null);
    }
}
