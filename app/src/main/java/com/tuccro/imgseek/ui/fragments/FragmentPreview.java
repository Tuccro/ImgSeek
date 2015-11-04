package com.tuccro.imgseek.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tuccro.imgseek.R;

/**
 * Created by tuccro on 11/3/15.
 */
public class FragmentPreview extends DialogFragment {

    public static final String PARAM_IMG_URL = "img_url";

    ImageView imagePreview;
    Button buttonClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_preview, null);

        imagePreview = (ImageView) view.findViewById(R.id.imagePreview);
        buttonClose = (Button) view.findViewById(R.id.buttonClose);

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDismiss(null);
            }
        });

        Bundle bundle = this.getArguments();

        try {
            String imgUrl = bundle.getString(PARAM_IMG_URL);

            Bitmap bitmap = BitmapFactory.decodeFile(imgUrl);
            imagePreview.setImageBitmap(bitmap);
        } catch (Exception | OutOfMemoryError e) {
            Log.e("Error init image", e.getMessage());
        }

        return view;
    }
}
