
package com.diagnal.movies.data.model;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import com.diagnal.movies.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("poster-image")
    @Expose
    private String posterImage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @BindingAdapter("contentImage")
    public static void loadImage(ImageView view, String imageName) {
        Context context = view.getContext();
        int identifier = context.getResources().getIdentifier(imageName.replace(".jpg",""),
                "drawable", context.getPackageName());
        if(identifier == 0){
            identifier = R.drawable.placeholder_for_missing_posters;
        }
        view.setImageResource(identifier);
    }

}
