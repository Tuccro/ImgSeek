package com.tuccro.imgseek.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tuccro.imgseek.R;
import com.tuccro.imgseek.model.ImageDescriptor;
import com.tuccro.imgseek.ui.adapters.SearchListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuccro on 10/27/15.
 */
public class FragmentSearch extends Fragment {

    OnSearchListInteraction onSearchListInteraction;

    private RecyclerView listSearch;
    private ProgressBar progressBar;

    LinearLayoutManager linearLayoutManager;

    SearchListAdapter searchListAdapter;

    long lastUpdate;

    List<ImageDescriptor> imageDescriptors;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            onSearchListInteraction = (OnSearchListInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSearchListInteraction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        listSearch = (RecyclerView) view.findViewById(R.id.listSearch);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        listSearch.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listSearch.setLayoutManager(linearLayoutManager);

        listSearch.setOnScrollListener(onScrollListener);

        initList();

        return view;
    }

    public void setProgressBarVisibility(boolean visible) {
        progressBar.setVisibility((visible) ? View.VISIBLE : View.GONE);
    }

    public void initList() {
        imageDescriptors = new ArrayList<ImageDescriptor>();
        searchListAdapter = new SearchListAdapter(imageDescriptors, getActivity());
        listSearch.setAdapter(searchListAdapter);
    }

    public void appendList(List<ImageDescriptor> descriptors) {

        imageDescriptors.addAll(descriptors);
        searchListAdapter.notifyDataSetChanged();

        lastUpdate = System.currentTimeMillis();
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            int listSize = linearLayoutManager.getChildCount();
            int lastPosition = linearLayoutManager.findLastVisibleItemPosition();

            if (lastPosition >= listSize && System.currentTimeMillis() > lastUpdate + 1500) {
                onSearchListInteraction.loadNextResults();
            }
        }
    };

    public interface OnSearchListInteraction {
        void loadNextResults();
    }
}
