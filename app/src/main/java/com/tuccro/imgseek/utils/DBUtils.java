package com.tuccro.imgseek.utils;

import android.database.Cursor;
import android.util.Log;

import com.tuccro.imgseek.db.DBValues;
import com.tuccro.imgseek.model.ImageDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuccro on 11/3/15.
 */
public class DBUtils {

    public static List<ImageDescriptor> imageDescriptorListFromCursor(Cursor cursor) {
        List<ImageDescriptor> imageDescriptors = new ArrayList<>();

        if (cursor != null) {
            if (cursor.getCount() < 1) return imageDescriptors;

            try {
                cursor.moveToFirst();

                do {

                    String description = cursor.getString(cursor.getColumnIndex(DBValues.DB_TABLE_IMG_TEXT));

                    String thumbnailUrl = cursor.getString(cursor.getColumnIndex(DBValues.DB_TABLE_IMG_ICON));
                    String thumbnailLocalUrl = cursor.getString(cursor.getColumnIndex(DBValues.DB_TABLE_IMG_ICON_LOCAL));

                    String imageUrl = cursor.getString(cursor.getColumnIndex(DBValues.DB_TABLE_IMG_IMAGE));
                    String imageLocalUrl = cursor.getString(cursor.getColumnIndex(DBValues.DB_TABLE_IMG_IMAGE_LOCAL));

                    imageDescriptors.add(new ImageDescriptor(description, thumbnailUrl, thumbnailLocalUrl, imageUrl, imageLocalUrl));

                } while (cursor.moveToNext());
            } catch (Exception e) {
                Log.e("Error parsing cursor", e.getMessage());
            }
        }

        return imageDescriptors;
    }
}
