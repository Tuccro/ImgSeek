package com.tuccro.imgseek.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuccro.imgseek.R;
import com.tuccro.imgseek.model.ImageDescriptor;

import java.util.List;

/**
 * Created by tuccro on 11/2/15.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<ImageDescriptor> descriptors;

    public SearchListAdapter(List<ImageDescriptor> descriptors) {
        this.descriptors = descriptors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ImageDescriptor descriptor = descriptors.get(position);

        holder.textDescribe.setText(descriptor.getDescription());

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(descriptor.getThumbnailLocalUrl());

            holder.imageThumbnail.setImageBitmap(bitmap);
        } catch (Exception e) {

        } catch (OutOfMemoryError error) {

        }

        // TODO: 11/2/15 ADD IMAGE
    }

    @Override
    public int getItemCount() {
        return descriptors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageThumbnail;
        TextView textDescribe;

        public ViewHolder(View itemView) {
            super(itemView);

            imageThumbnail = (ImageView) itemView.findViewById(R.id.imageThumbnail);
            textDescribe = (TextView) itemView.findViewById(R.id.textDescribe);
        }
    }
}
