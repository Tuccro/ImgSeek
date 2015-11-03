package com.tuccro.imgseek.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuccro.imgseek.R;

/**
 * Created by tuccro on 11/3/15.
 */
public class FragmentSaved extends Fragment {

    RecyclerView listSaved;
    LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_saved, container, false);

        listSaved = (RecyclerView) view.findViewById(R.id.listSaved);

        listSaved.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listSaved.setLayoutManager(linearLayoutManager);

//        listSaved.setOnScrollListener(onScrollListener);

        return view;
    }
}
