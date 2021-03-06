package com.tuccro.imgseek.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;

import com.tuccro.imgseek.R;
import com.tuccro.imgseek.model.ImageDescriptor;
import com.tuccro.imgseek.ui.adapters.IOnIconClick;
import com.tuccro.imgseek.ui.adapters.SearchListAdapter;
import com.tuccro.imgseek.ui.fragments.FragmentPreview;
import com.tuccro.imgseek.ui.fragments.FragmentSaved;
import com.tuccro.imgseek.ui.fragments.FragmentSearch;
import com.tuccro.imgseek.utils.SearchLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<ImageDescriptor>>,
        FragmentSearch.OnSearchListInteraction,
        SearchListAdapter.IOnDbUpgrade, IOnIconClick {

    FragmentSearch fragmentSearch;
    FragmentSaved fragmentSaved;

    Loader<Object> searchLoader;
    int searchLoaderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fragmentSearch = new FragmentSearch();
        fragmentSaved = new FragmentSaved();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);
        searchView.setEnabled(true);
        return true;
    }


    @Override
    public Loader<List<ImageDescriptor>> onCreateLoader(int id, Bundle args) {

        Loader<List<ImageDescriptor>> loader = null;
        if (id == searchLoaderId) {
            loader = new SearchLoader(this, args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ImageDescriptor>> loader, List<ImageDescriptor> data) {
        fragmentSearch.appendList(data);
        fragmentSearch.setProgressBarVisibility(false);
    }

    @Override
    public void onLoaderReset(Loader<List<ImageDescriptor>> loader) {
        fragmentSearch.setProgressBarVisibility(false);
    }

    @Override
    public void loadNextResults() {

        Log.d(this.getLocalClassName(), "Loading next results...");
        searchLoader = getLoaderManager().getLoader(searchLoaderId);

        if (searchLoader != null) {

            fragmentSearch.setProgressBarVisibility(true);
            searchLoader.forceLoad();
        }
    }

    @Override
    public void onDbUpgrade() {
        fragmentSaved.initList();
    }

    @Override
    public void onIconClick(ImageDescriptor descriptor) {
        DialogFragment dialog = new FragmentPreview();

        Bundle bundle = new Bundle();
        bundle.putString(FragmentPreview.PARAM_IMG_URL, descriptor.getImageLocalUrl());
        dialog.setArguments(bundle);

        dialog.show(getSupportFragmentManager(), "tag");
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            if (position == 0) return fragmentSearch;
            else return fragmentSaved;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.search);
                case 1:
                    return getString(R.string.saved);
            }
            return null;
        }
    }

    SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {

            Bundle bundle = new Bundle();
            bundle.putString(SearchLoader.ARG_QUERY, query);

            searchLoaderId = query.hashCode();

            getLoaderManager().initLoader(searchLoaderId, bundle, MainActivity.this);

            searchLoader = getLoaderManager().getLoader(searchLoaderId);

            if (searchLoader != null) {
                searchLoader.forceLoad();
                fragmentSearch.setProgressBarVisibility(true);
                fragmentSearch.initList();
            }
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };
}
