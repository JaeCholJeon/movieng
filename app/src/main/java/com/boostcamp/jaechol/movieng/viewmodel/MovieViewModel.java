package com.boostcamp.jaechol.movieng.viewmodel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.boostcamp.jaechol.movieng.R;
import com.boostcamp.jaechol.movieng.adapter.RecyclerDataAdapter;
import com.boostcamp.jaechol.movieng.model.MovieModel;
import com.boostcamp.jaechol.movieng.utils.IndexData;
import com.boostcamp.jaechol.movieng.utils.ProgressDialog;


/**
 * Created by jaechol on 2018. 12. 15..
 */

public class MovieViewModel implements BaseViewModel {

    private Context context;
    private ApiService service_Movie;
    private boolean isServiceBind = false;
    public ObservableArrayList<MovieModel> movieList;
    public ObservableField<String> movieTitle;
    private RecyclerView recyclerViewList;
    private String stringMovieTitle;
    private LinearLayoutManager layoutManager;
    private RecyclerDataAdapter recyclerDataAdapter;
    private IndexData indexData;
    private ProgressDialog searchingDialog;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ApiService.MovieListBinder tmpBinder = (ApiService.MovieListBinder) iBinder;
            service_Movie = tmpBinder.getService();
            isServiceBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service_Movie = null;
            isServiceBind = false;
        }
    };


    public MovieViewModel(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerViewList = recyclerView;
    }

    private Boolean isNetworkAvail() {
        ConnectivityManager networkManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean mobileConnect = networkManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean wifiAvail = networkManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isAvailable();
        boolean wifiConnect = networkManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean mobileAvail = networkManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isAvailable();
        return (mobileAvail && mobileConnect) || (wifiAvail && wifiConnect);
    }

    @Override
    public void onCreate() {
        if (!isServiceBind) {
            Intent intentApiService = new Intent(context, ApiService.class);
            context.startService(intentApiService);
            context.bindService(intentApiService, conn, Context.BIND_AUTO_CREATE);
        }
        movieList = new ObservableArrayList();
        indexData = new IndexData();
        movieTitle = new ObservableField<String>();

        recyclerDataAdapter = new RecyclerDataAdapter(movieList);
        layoutManager = new LinearLayoutManager(this.context);
        searchingDialog = new ProgressDialog(this.context);

        recyclerViewList.setAdapter(recyclerDataAdapter);
        recyclerViewList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            /**
             * 스크롤 이전 인덱스에 따라 데이터 로딩 및 스크롤 재포지셔닝
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                indexData.itemCount = layoutManager.getItemCount();
                int lastVisibleIndex = ((LinearLayoutManager) recyclerViewList.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if ((lastVisibleIndex + 5 >= indexData.itemCount) && indexData.itemCount < indexData.totalCount) {
                    searchingDialog.ShowProgressDialog();
                    movieList = (ObservableArrayList<MovieModel>) service_Movie.getResult(movieList, stringMovieTitle, indexData);
                    searchingDialog.HideProgressDialog();
                    recyclerViewList.scrollToPosition(lastVisibleIndex - 2);
                    recyclerViewList.setAdapter(recyclerDataAdapter);
                }
            }
        });
        recyclerViewList.setLayoutManager(layoutManager);
    }

    @Override
    public void onPause() {
        if (isServiceBind) {
            context.unbindService(conn);
        }
        isServiceBind = false;
    }

    @Override
    public void onResume() {
        if (!isServiceBind) {
            Intent intentApiService = new Intent(context, com.boostcamp.jaechol.movieng.viewmodel.ApiService.class);
            context.startService(intentApiService);
            context.bindService(intentApiService, conn, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onDestroy() {}

    @Override
    public void onTextAfterChanged(Editable editable) {
        stringMovieTitle = editable.toString();
    }


    @BindingAdapter("bind:item")
    public static void bindItem(RecyclerView recyclerView,
                                ObservableArrayList<MovieModel> movie) {
        RecyclerDataAdapter adapter = (RecyclerDataAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.setItem(movie);
        }
    }

    public void onClickBtnSearch(View v) {
        if (!isNetworkAvail()) {
            Toast.makeText(context, R.string.fail_connect_newtwork, Toast.LENGTH_SHORT).show();

            return;
        }
        indexData.set(0, 0);
        movieList.clear();
        if (stringMovieTitle.equals("")) {
            Toast.makeText(context, R.string.no_input_data, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isServiceBind) {
            Toast.makeText(context, R.string.fail_start_service, Toast.LENGTH_SHORT).show();
            return;
        }
        /** Progress Dialog show */
        searchingDialog.ShowProgressDialog();
        /**
         * 영화 쿼리 검색
         */
        movieList = (ObservableArrayList<MovieModel>) service_Movie.getResult(movieList, stringMovieTitle, indexData);
        /** Progress Dialog dismiss */
        searchingDialog.HideProgressDialog();
        if (movieList.size() < 1) {
            Toast.makeText(context, R.string.no_result, Toast.LENGTH_SHORT).show();
            return;
        }
        recyclerViewList.setAdapter(recyclerDataAdapter);
    }
}
