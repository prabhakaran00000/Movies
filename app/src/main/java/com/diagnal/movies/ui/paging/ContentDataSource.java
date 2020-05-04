package com.diagnal.movies.ui.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.diagnal.movies.data.model.Content;
import com.diagnal.movies.helper.AppUtils;

public class ContentDataSource extends PageKeyedDataSource<Integer, Content> {
    private static final String TAG = "Data";
    private final int FIRST_PAGE = 1;
    private final int TOTAL_PAGE = 3;
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Content> callback) {
        Log.d(TAG, "loadInitial: "+params.requestedLoadSize);
        callback.onResult(AppUtils.getMovieList(FIRST_PAGE),null,FIRST_PAGE+1);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Content> callback) {
        Log.d(TAG, "loadBefore: "+params.key);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Content> callback) {
        Log.d(TAG, "loadAfter: "+params.key);
        if(params.key <= TOTAL_PAGE){
            callback.onResult(AppUtils.getMovieList(params.key),params.key+1);
        }else {
            Log.d(TAG, "loadAfter inside: "+params.key);
        }

    }
}
