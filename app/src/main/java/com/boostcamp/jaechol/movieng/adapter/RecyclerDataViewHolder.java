package com.boostcamp.jaechol.movieng.adapter;

import android.support.v7.widget.RecyclerView;

import com.boostcamp.jaechol.movieng.databinding.MovieItemLayoutBinding;

public class RecyclerDataViewHolder extends RecyclerView.ViewHolder {
    public MovieItemLayoutBinding binding;
    public RecyclerDataViewHolder(MovieItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
