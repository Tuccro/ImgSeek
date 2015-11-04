package com.tuccro.imgseek.db;

/**
 * Created by tuccro on 11/3/15.
 */
public class DBValues {

    public static final int DB_VERSION = 1;

    public static final String DB_NAME = "db.sqlite";

    public static final String DB_CREATE = "CREATE TABLE images\n" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "text TEXT,\n" +
            "icon_url TEXT,\n" +
            "icon_local_url TEXT,\n" +
            "image_url TEXT,\n" +
            "image_local_url TEXT);";

    public static final String DB_TABLE_IMG = "images";

    public static final String DB_TABLE_IMG_ID = "_id";
    public static final String DB_TABLE_IMG_TEXT = "text";
    public static final String DB_TABLE_IMG_ICON = "icon_url";
    public static final String DB_TABLE_IMG_ICON_LOCAL = "icon_local_url";
    public static final String DB_TABLE_IMG_IMAGE = "image_url";
    public static final String DB_TABLE_IMG_IMAGE_LOCAL = "image_local_url";
}
