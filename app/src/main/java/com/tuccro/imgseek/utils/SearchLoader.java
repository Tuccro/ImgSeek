package com.tuccro.imgseek.utils;

import android.content.Context;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.tuccro.imgseek.asynk.AsyncImagesDownloader;
import com.tuccro.imgseek.asynk.AsyncJsonStringLoader;
import com.tuccro.imgseek.asynk.IOnReadyCallBack;
import com.tuccro.imgseek.model.ImageDescriptor;

import java.util.List;

/**
 * Created by tuccro on 11/2/15.
 */
public class SearchLoader extends Loader<List<ImageDescriptor>> implements IOnReadyCallBack {

    public final static String ARG_QUERY = "query";
    public final static String NO_QUERY = "nothing";

    GCSLinkGenerator gcsLinkGenerator;

    private AsyncJsonStringLoader asyncJsonStringLoader;
    private String query;

    public SearchLoader(Context context, Bundle args) {
        super(context);

        if (args != null)
            query = args.getString(ARG_QUERY);
        if (TextUtils.isEmpty(query))
            query = NO_QUERY;

        gcsLinkGenerator = new GCSLinkGenerator(Util.filterSearchQuery(query));
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

        String tempURL = gcsLinkGenerator.getNextTenEntriesLink();
        asyncJsonStringLoader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, tempURL);
    }

    void getResultFromJson(String result) {

        JsonImagesParser jsonImagesParser = new JsonImagesParser(result);
        List<ImageDescriptor> list = jsonImagesParser.getImageDescriptors();

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
