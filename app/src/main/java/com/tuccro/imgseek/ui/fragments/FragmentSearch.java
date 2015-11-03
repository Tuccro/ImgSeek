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
        searchListAdapter = new SearchListAdapter(new ArrayList<ImageDescriptor>());
        listSearch.setAdapter(searchListAdapter);
    }

    // TODO: 11/3/15 to add append data to list
    public void appendList(List<ImageDescriptor> descriptors) {

        SearchListAdapter searchListAdapter = new SearchListAdapter(descriptors);
        listSearch.setAdapter(searchListAdapter);

        lastUpdate = System.currentTimeMillis();
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            int listSize = linearLayoutManager.getChildCount();
            int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

            if (lastPosition == listSize && System.currentTimeMillis() > lastUpdate + 3000) {
                onSearchListInteraction.loadNextResults();
            }
        }
    };

    public interface OnSearchListInteraction {
        void loadNextResults();
    }
}
