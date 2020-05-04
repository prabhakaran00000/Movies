package com.diagnal.movies.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.diagnal.movies.ui.paging.ContentDataSourceFactory;
import com.diagnal.movies.ui.paging.SearchContentDataSource;
import com.diagnal.movies.data.model.Content;

import java.util.concurrent.Executors;

public class ContentViewModel extends ViewModel {

    private LiveData<PagedList<Content>> contentPagedListLiveData;
    private MutableLiveData<PagedList<Content>> contentSearchPagedListLiveData = new MutableLiveData<>();

    public ContentViewModel() {
        ContentDataSourceFactory contentDataSourceFactory = new ContentDataSourceFactory();

        PagedList.Config config = new PagedList.Config.Builder()
                                    .setEnablePlaceholders(false)
                                    .setPageSize(10)
                                    .build();

        contentPagedListLiveData =  (new LivePagedListBuilder(contentDataSourceFactory,config)).build();
    }

    public LiveData<PagedList<Content>> getContentPagedListLiveData() {
        return contentPagedListLiveData;
    }

    public LiveData<PagedList<Content>> getSearchedContentPagedList() {
        return contentSearchPagedListLiveData;
    }

    public void searchContent(PagedList<Content> contents, String queryText){
        SearchContentDataSource searchContentDataSource = new SearchContentDataSource(contents,queryText);
        PagedList.Config pagedConfig = new PagedList.Config.Builder()
                .setPageSize(10)
                .build();
        PagedList<Content> filteredContent = new PagedList.Builder<>(searchContentDataSource, pagedConfig)
                .setNotifyExecutor(Executors.newSingleThreadExecutor())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();
        contentSearchPagedListLiveData.postValue(filteredContent);
    }
}
