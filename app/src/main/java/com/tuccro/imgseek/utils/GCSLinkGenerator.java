package com.tuccro.imgseek.utils;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuccro on 11/2/15.
 */
public class GCSLinkGenerator {

    private static final String SERVER_URL = "https://www.googleapis.com/customsearch/v1?";

    private static final String PARAM_NAME_KEY = "key";
    private static final String PARAM_NAME_CX = "cx";
    private static final String PARAM_NAME_START = "start";
    private static final String PARAM_NAME_ALT = "alt";
    private static final String PARAM_NAME_NUM = "num";
    private static final String PARAM_NAME_QUERY = "q";

    private static final String PARAM_VALUE_KEY = "AIzaSyBusJU0W2Cz9qsRdNv4PEfyZb-xd8kd_ig";
    private static final String PARAM_VALUE_CX = "015626417939121497534:a3nw5u8dwp8";
    private static final String PARAM_VALUE_ALT = "json";
    private static final int PARAM_VALUE_NUM = 10;

    private int paramValueStart = 1;
    private String paramValueQuery;

    public GCSLinkGenerator(String query) {

        this.paramValueQuery = query;
    }

    public String getNextTenEntriesLink() {

        String paramString = getParamString();
        paramValueStart += PARAM_VALUE_NUM;


        return SERVER_URL + paramString;
    }

    @SuppressWarnings("deprecation")
    private String getParamString() {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair(PARAM_NAME_KEY, PARAM_VALUE_KEY));
        params.add(new BasicNameValuePair(PARAM_NAME_CX, PARAM_VALUE_CX));
        params.add(new BasicNameValuePair(PARAM_NAME_ALT, PARAM_VALUE_ALT));
        params.add(new BasicNameValuePair(PARAM_NAME_NUM, String.valueOf(PARAM_VALUE_NUM)));

        params.add(new BasicNameValuePair(PARAM_NAME_QUERY, paramValueQuery));
        params.add(new BasicNameValuePair(PARAM_NAME_START, String.valueOf(paramValueStart)));

        return URLEncodedUtils.format(params, "utf-8");
    }
}
