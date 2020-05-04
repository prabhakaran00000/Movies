package com.diagnal.movies.ui.paging;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.diagnal.movies.data.model.Content;

import java.util.List;
import java.util.stream.Collectors;

public class SearchContentDataSource extends PageKeyedDataSource<Integer,Content> {

    private PagedList<Content> mContents;
    private String mQueryText;

    public SearchContentDataSource(PagedList<Content> contents, String queryText) {
        mContents = contents;
        mQueryText = queryText;
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Content> callback) {
        List<Content> searchResult = mContents.stream()
                .filter(p -> p.getName().toLowerCase().contains(mQueryText.toLowerCase()))
                .collect(Collectors.toList());
        callback.onResult(searchResult,null,2);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Content> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Content> callback) {

    }
}
