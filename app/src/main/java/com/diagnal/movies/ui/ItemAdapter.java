package com.diagnal.movies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.diagnal.movies.R;
import com.diagnal.movies.data.model.Content;
import com.diagnal.movies.databinding.RecyclerVewItemBinding;

public class ItemAdapter extends PagedListAdapter<Content,ItemAdapter.ItemViewHolder> {
    private Context mContext;
    public ItemAdapter(Context context) {
        super(diffCallback);
        mContext = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerVewItemBinding recyclerVewItemBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.recycler_vew_item, parent, false);
        return new ItemViewHolder(recyclerVewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Content content = getItem(position);
        if(content != null)
        holder.bind(content);
    }
    private static DiffUtil.ItemCallback<Content> diffCallback = new DiffUtil.ItemCallback<Content>() {
        @Override
        public boolean areItemsTheSame(@NonNull Content oldItem, @NonNull Content newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Content oldItem, @NonNull Content newItem) {
            return false;
        }
    };

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerVewItemBinding mRecyclerVewItemBinding;
        public ItemViewHolder(@NonNull RecyclerVewItemBinding recyclerVewItemBinding) {
            super(recyclerVewItemBinding.getRoot());
            mRecyclerVewItemBinding = recyclerVewItemBinding;
        }

        public void bind(Content content){
            mRecyclerVewItemBinding.setContent(content);
        }
    }
}
