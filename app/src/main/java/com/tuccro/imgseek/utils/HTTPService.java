package com.tuccro.imgseek.utils;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by tuccro on 11/2/15.
 */
public class HTTPService {

    private String query;

    public HTTPService(String query) {

        this.query = query;
    }

    public void getNexTenEntriesLink() {

        try {
            java.net.URL url = new URL(query);

            InputStream inputStream = new BufferedInputStream(url.openConnection().getInputStream());

            StringBuffer response = new StringBuffer();
            Scanner sc = new Scanner(inputStream);

            while (sc.hasNext()) {
                response.append(sc.next());
            }

            String resp = response.toString();

            Log.e("JSON:", resp);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
