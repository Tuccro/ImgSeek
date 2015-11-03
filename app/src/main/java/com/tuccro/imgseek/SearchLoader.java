package com.tuccro.imgseek;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tuccro.imgseek.asynktask.AsyncImagesDownloader;
import com.tuccro.imgseek.asynktask.AsyncJsonStringLoader;
import com.tuccro.imgseek.asynktask.IOnReadyCallBack;
import com.tuccro.imgseek.model.ImageDescriptor;
import com.tuccro.imgseek.utils.GCSLinkGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuccro on 11/2/15.
 */
public class SearchLoader extends Loader<List<ImageDescriptor>> implements IOnReadyCallBack {

    public final static String ARG_QUERY = "query";
    public final static String NO_QUERY = "nothing";

    GCSLinkGenerator gcsLinkGenerator;

    private AsyncJsonStringLoader asyncJsonStringLoader;
    private int start = 0;
    private String query;

    public SearchLoader(Context context, Bundle args) {
        super(context);

        if (args != null)
            query = args.getString(ARG_QUERY);
        if (TextUtils.isEmpty(query))
            query = NO_QUERY;

        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == ' ') {
                query = query.substring(0, i) + "%20" + query.substring(i + 1);
            }
            if (query.charAt(i) == '&') {
                query = query.substring(0, i) + "%20and%20" + query.substring(i + 1);
            }
        }

        gcsLinkGenerator = new GCSLinkGenerator(query);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();

        if (asyncJsonStringLoader != null)
            asyncJsonStringLoader.cancel(true);

        asyncJsonStringLoader = new AsyncJsonStringLoader(this);

        String tempURL = gcsLinkGenerator.getNexTenEntriesLink();
        asyncJsonStringLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, tempURL);
    }

    void getResultFromJson(String result) {

        List<ImageDescriptor> list = new ArrayList();

        try {
            JsonParser parser = new JsonParser();
            JsonObject mainObject = parser.parse(result).getAsJsonObject();
//            JsonObject internalObject = mainObject.getAsJsonObject("items");

            JsonArray results = mainObject.getAsJsonArray("items");

            String title;
            String urlIcon;
            String urlImage;

            for (JsonElement image : results) {

                JsonObject object = image.getAsJsonObject();

                title = object.get("title").toString();

                JsonObject pageMap = object.getAsJsonObject("pagemap");
                JsonArray cseImage = pageMap.getAsJsonArray("cse_image");
//                JsonObject imageobject = pageMap.getAsJsonObject("imageobject");


                urlIcon = ((JsonObject) cseImage.get(0)).get("src").toString();
                urlImage = ((JsonObject) cseImage.get(0)).get("src").toString();

//                Log.e("Image URL", ((JsonObject) cseImage.get(0)).get("src").toString());

                list.add(new ImageDescriptor(title.substring(1, title.length() - 1)
                        , urlIcon.substring(1, urlIcon.length() - 1)
                        , urlImage.substring(1, urlImage.length() - 1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        AsyncImagesDownloader imagesDownloader = new AsyncImagesDownloader(this);
        imagesDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, list);
    }

    void endLoad(List<ImageDescriptor> list) {
        deliverResult(list);
    }

    @Override
    public void onGCSResult(String result) {
        getResultFromJson(result);
    }

    @Override
    public void onImagesReady(List<ImageDescriptor> imageDescriptors) {
        endLoad(imageDescriptors);
    }
}
