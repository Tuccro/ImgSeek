package com.tuccro.imgseek.async;

import android.content.Loader;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by tuccro on 11/2/15.
 */
public class AsyncJsonStringLoader extends AsyncTask<String, Void, String> {

    IOnReadyCallBack iOnReadyCallBack;

    public AsyncJsonStringLoader(Loader loader) {
        iOnReadyCallBack = (IOnReadyCallBack) loader;
    }

    @Override
    protected String doInBackground(String... params) {

        String link = params[0];
        String resp = null;

        try {
            java.net.URL url = new URL(link);

            InputStream inputStream = new BufferedInputStream(url.openConnection().getInputStream());

            StringBuffer response = new StringBuffer();
            Scanner sc = new Scanner(inputStream);

            while (sc.hasNext()) {
                response.append(sc.next());
            }

            resp = response.toString();
            return resp;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        iOnReadyCallBack.onGCSResult(result);
    }
}

