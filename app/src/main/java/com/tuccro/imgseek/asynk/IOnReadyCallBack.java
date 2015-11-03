package com.tuccro.imgseek.asynk;

import com.tuccro.imgseek.model.ImageDescriptor;

import java.util.List;

public interface IOnReadyCallBack {


    void onGCSResult(String result);

    void onImagesReady(List<ImageDescriptor> imageDescriptors);
}