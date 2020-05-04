package com.diagnal.movies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.diagnal.movies.R;
import com.diagnal.movies.data.model.Content;
import com.diagnal.movies.databinding.ActivityMovieListBinding;
import com.diagnal.movies.viewmodels.ContentViewModel;

public class MovieListActivity extends AppCompatActivity {

    private ActivityMovieListBinding mActivityMovieListBinding;
    private ContentViewModel mContentViewModel;
    private ItemAdapter mItemAdapter;
    private PagedList<Content> mContents;
    private static final int PORTRAIT_COUNT = 3;
    private static final int LANDSCAPE_COUNT = 5;
    private static final String LAST_SEARCH_QUERY = "last_search_query";
    private String mLastSearchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMovieListBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_list);
        initViewModel();
        initRecyclerView();
        if (savedInstanceState != null) {
            mLastSearchQuery = savedInstanceState.getString(LAST_SEARCH_QUERY,null);
        }
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = mActivityMovieListBinding.recyclerView;
        mItemAdapter = new ItemAdapter(this);
        int spanCount;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = LANDSCAPE_COUNT;
        } else {
            spanCount = PORTRAIT_COUNT;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this,spanCount));
        recyclerView.setAdapter(mItemAdapter);
    }

    private void initViewModel() {
        mContentViewModel = new ViewModelProvider(this).get(ContentViewModel.class);
        mContentViewModel.getContentPagedListLiveData().observe(this, new Observer<PagedList<Content>>() {
            @Override
            public void onChanged(PagedList<Content> contents) {
                mContents = contents;
                mItemAdapter.submitList(contents);
            }
        });

        mContentViewModel.getSearchedContentPagedList().observe(MovieListActivity.this, new Observer<PagedList<Content>>() {
            @Override
            public void onChanged(PagedList<Content> contents) {
                mItemAdapter.submitList(contents);
            }
        });
    }

    public void searchContent(String queryText){
        if (queryText.length() > 3) {
            mLastSearchQuery = queryText;
            mContentViewModel.searchContent(mContents,queryText);
        }else{
            mLastSearchQuery = null;
            mItemAdapter.submitList(mContents);
            mActivityMovieListBinding.recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if(mLastSearchQuery != null){
            searchItem.expandActionView();
            searchView.setQuery(mLastSearchQuery,true);
            searchView.clearFocus();
        }
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchContent(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LAST_SEARCH_QUERY, mLastSearchQuery);
    }
}
