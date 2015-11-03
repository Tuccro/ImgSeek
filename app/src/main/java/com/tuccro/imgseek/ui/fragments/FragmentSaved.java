package com.tuccro.imgseek.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuccro.imgseek.R;
import com.tuccro.imgseek.db.DBOwner;
import com.tuccro.imgseek.model.ImageDescriptor;
import com.tuccro.imgseek.ui.adapters.SavedListAdapter;
import com.tuccro.imgseek.utils.DBUtils;

import java.util.List;

/**
 * Created by tuccro on 11/3/15.
 */
public class FragmentSaved extends Fragment {

    RecyclerView listSaved;
    LinearLayoutManager linearLayoutManager;

    List<ImageDescriptor> imageDescriptors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        listSaved = (RecyclerView) view.findViewById(R.id.listSaved);

        listSaved.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listSaved.setLayoutManager(linearLayoutManager);

        initList();

        return view;
    }
    public void initList() {

        DBOwner dbOwner = new DBOwner(getContext());
        dbOwner.openConnection();

        imageDescriptors = DBUtils.imageDescriptorListFromCursor(dbOwner.getImageDescriptorsCursor());

        dbOwner.closeConnection();

        SavedListAdapter savedListAdapter = new SavedListAdapter(imageDescriptors, getContext());
        listSaved.setAdapter(savedListAdapter);
    }
}
