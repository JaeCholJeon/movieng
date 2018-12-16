package com.boostcamp.jaechol.movieng.viewmodel;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.boostcamp.jaechol.movieng.R;
import com.boostcamp.jaechol.movieng.model.MovieModel;
import com.boostcamp.jaechol.movieng.utils.ApiConnection;
import com.boostcamp.jaechol.movieng.utils.IndexData;
import com.boostcamp.jaechol.movieng.utils.JsonArrayParsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 쿼리 실행을 위한 Service 클래스
 * MainActiviy 실행시 Bind.
 * 쿼리 실행 Method : getResult
 * Parameter      : ArrayList<MovieModel> arrayList , String MovieTile , IndexData indexData
 * return         : ArrayList<MovieModel> arrayList
 */

public class ApiService extends Service {

    public IndexData indexData;
    private ApiConnection apiConnection;
    private IBinder mBider = new MovieListBinder();
    private StringBuilder response;
    private ArrayList<MovieModel> arrayList_Movie;

    public ArrayList<MovieModel> getResult(ArrayList<MovieModel> arrayList_Movie, String title, IndexData indexData) {
        this.indexData = indexData;
        this.arrayList_Movie = arrayList_Movie;
        ThreadApiConnection thread = new ThreadApiConnection(title, indexData.itemCount + 1);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return arrayList_Movie;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBider;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiConnection = new ApiConnection();
        response = new StringBuilder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MovieListBinder extends Binder {
        ApiService getService() {
            return ApiService.this;
        }
    }

    class ThreadApiConnection extends Thread {
        String title;
        long start = 0;

        ThreadApiConnection(String title, long start) {
            this.title = title;
            this.start = start;
        }

        @Override
        public void run() {
            super.run();
            response = apiConnection.getResult(title, start);

            if (response.equals("")) {
                Toast.makeText(getApplicationContext(), R.string.no_result, Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(response));
                Log.d("TAG",response.toString());
                int countTotal = jsonObject.getInt("total");

                if (countTotal > 1000)
                    indexData.totalCount = 1000;
                else
                    indexData.totalCount = countTotal;
                if (start > 1000) {
                    return;
                }
                /**
                 네이버 API 실행시 start 파라미터가 1000 이상일 경우 에러발생하므로
                 1000이하가 쿼리되도록 조절
                 **/
                int display = jsonObject.getInt("display");
                int recyclerview_display = display;
                JSONArray ArrayItems = jsonObject.getJSONArray("items");
                int thresdholds = recyclerview_display;
                if (indexData.totalCount - indexData.itemCount < display)
                    thresdholds = indexData.totalCount - indexData.itemCount;
                if (thresdholds < 0)
                    return;
                JsonArrayParsing jsonArrayParsing = new JsonArrayParsing(ArrayItems, thresdholds, arrayList_Movie);
                arrayList_Movie = jsonArrayParsing.getParseList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


