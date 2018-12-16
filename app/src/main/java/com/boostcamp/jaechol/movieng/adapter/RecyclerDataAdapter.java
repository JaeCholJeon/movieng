package com.boostcamp.jaechol.movieng.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.boostcamp.jaechol.movieng.databinding.MovieItemLayoutBinding;
import com.boostcamp.jaechol.movieng.model.MovieModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


//RecyclerView 어댑터 및 썸네일 로딩 쓰레드.

public class RecyclerDataAdapter extends RecyclerView.Adapter<RecyclerDataViewHolder> {

    private ArrayList<MovieModel> arrayListMovie;
    private Context context;

    public RecyclerDataAdapter(ArrayList<MovieModel> arrayList_movie_data) {
        this.arrayListMovie = arrayList_movie_data;
    }

    @Override
    public RecyclerDataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        MovieItemLayoutBinding movieLayoutBinding =
                MovieItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new RecyclerDataViewHolder(movieLayoutBinding);
    }

    //뷰홀더 바인딩
    @Override
    public void onBindViewHolder(final RecyclerDataViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link = arrayListMovie.get(holder.getAdapterPosition()).getLink();
                Intent intent_link = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                context.startActivity(intent_link);
            }
        });
        MovieModel item = arrayListMovie.get(position);
        holder.binding.setMovieItem(item);
    }


    @Override
    public int getItemCount() {
        return arrayListMovie.size();
    }

    public void setItem(List<MovieModel> arrayListMovie) {
        if (arrayListMovie != null) {
            this.arrayListMovie = (ArrayList<MovieModel>) arrayListMovie;
            notifyDataSetChanged();
        }
    }

    /**
     * 이미지뷰 바인딩 및 로딩
     *
     * @param view
     * @param imageUrl
     */
    @BindingAdapter("bind:imageUrl")
    public static void setImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .override(500, 500)
                .error(android.support.v4.R.color.ripple_material_light)
                .into(view);
    }

}
