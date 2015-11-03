package com.tuccro.imgseek.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuccro.imgseek.R;
import com.tuccro.imgseek.db.DBOwner;
import com.tuccro.imgseek.model.ImageDescriptor;

import java.util.List;

/**
 * Created by tuccro on 11/2/15.
 */
public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private List<ImageDescriptor> descriptors;
    Context mContext;

    IOnDbUpgrade iOnDbUpgrade;
    IOnIconClick iOnIconClick;

    public SearchListAdapter(List<ImageDescriptor> descriptors, Context mContext) {
        this.descriptors = descriptors;
        this.mContext = mContext;

        iOnDbUpgrade = (IOnDbUpgrade) mContext;
        iOnIconClick = (IOnIconClick) mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ImageDescriptor descriptor = descriptors.get(position);

        holder.textDescribe.setText(descriptor.getDescription());

        try {
            Bitmap bitmap = BitmapFactory.decodeFile(descriptor.getThumbnailLocalUrl());
            holder.imageThumbnail.setImageBitmap(bitmap);

        } catch (Exception e) {
        } catch (OutOfMemoryError error) {
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setEnabled(false);

                    DBOwner dbOwner = new DBOwner(mContext);
                    dbOwner.openConnection();
                    // Add ImageDescriptor to DB
                    dbOwner.addImageDescriptorToDB(descriptor);
                    dbOwner.closeConnection();

                    iOnDbUpgrade.onDbUpgrade();
                }
            }
        });

        holder.imageThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnIconClick.onIconClick(descriptor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return descriptors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageThumbnail;
        TextView textDescribe;
        CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            imageThumbnail = (ImageView) itemView.findViewById(R.id.imageThumbnail);
            textDescribe = (TextView) itemView.findViewById(R.id.textDescribe);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkboxSave);
        }
    }

    public interface IOnDbUpgrade {
        void onDbUpgrade();
    }
}
