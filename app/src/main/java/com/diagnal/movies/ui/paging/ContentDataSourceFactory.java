package com.diagnal.movies.ui.paging;

import androidx.paging.DataSource;

public class ContentDataSourceFactory extends DataSource.Factory {

    @Override
    public DataSource create() {
        ContentDataSource contentDataSource = new ContentDataSource();
        return contentDataSource;
    }

}
