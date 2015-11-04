package com.tuccro.imgseek.async;

import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.tuccro.imgseek.model.ImageDescriptor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by tuccro on 11/2/15.
 */
public class AsyncImagesDownloader extends AsyncTask<List<ImageDescriptor>, Void, List<ImageDescriptor>> {

    IOnReadyCallBack iOnReadyCallBack;
    private String cacheFolder;

    public AsyncImagesDownloader(Loader loader) {

        cacheFolder = loader.getContext().getExternalCacheDir().toString();
        Log.d("cache folder:", cacheFolder);

        iOnReadyCallBack = (IOnReadyCallBack) loader;
    }

    @Override
    protected List<ImageDescriptor> doInBackground(List<ImageDescriptor>... params) {
        List<ImageDescriptor> list = params[0];

        for (ImageDescriptor descriptor : list) {

            try {
                java.net.URL urlIcon = new URL(descriptor.getImageUrl());

                InputStream inputStreamIcon = new BufferedInputStream(urlIcon.openConnection().getInputStream());

                Bitmap icon = BitmapFactory.decodeStream(inputStreamIcon);

                File fileIcon = new File(cacheFolder, String.valueOf(System.currentTimeMillis())
                        + ".jpg");

                descriptor.setThumbnailLocalUrl(fileIcon.getAbsolutePath());
                descriptor.setImageLocalUrl(fileIcon.getAbsolutePath());

                OutputStream outputStreamIcon = new FileOutputStream(fileIcon);

                icon.compress(Bitmap.CompressFormat.JPEG, 85, outputStreamIcon);
                icon.recycle();

                outputStreamIcon.flush();
                outputStreamIcon.close();

            } catch (Exception | OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    protected void onPostExecute(List<ImageDescriptor> imageDescriptors) {
        super.onPostExecute(imageDescriptors);
        iOnReadyCallBack.onImagesReady(imageDescriptors);
    }
}