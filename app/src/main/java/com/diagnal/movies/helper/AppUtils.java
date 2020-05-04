package com.diagnal.movies.helper;

import com.diagnal.movies.data.model.Content;
import com.diagnal.movies.data.model.MovieItem;
import com.google.gson.Gson;

import java.util.List;

public class AppUtils {

    public static List<Content> getMovieList(int pageNo){
        MovieItem movieItem = null;
        switch(pageNo){
            case 1:
               movieItem = new Gson().fromJson(Constants.PAGE_ONE,MovieItem.class);
               break;
            case 2:
                movieItem = new Gson().fromJson(Constants.PAGE_TWO,MovieItem.class);
                break;
            case 3:
                movieItem = new Gson().fromJson(Constants.PAGE_THREE,MovieItem.class);
                break;
        }
        return movieItem.getPage().getContentItems().getContent();
    }

}
