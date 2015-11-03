package com.tuccro.imgseek.utils;

/**
 * Created by tuccro on 11/3/15.
 */
public class Util {

    public static String filterSearchQuery(String query) {

        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ') {
                query = query.substring(0, i) + "%20" + query.substring(i + 1);
            }
            if (query.charAt(i) == '&') {
                query = query.substring(0, i) + "%20and%20" + query.substring(i + 1);
            }
        }
        return query;
    }
}
