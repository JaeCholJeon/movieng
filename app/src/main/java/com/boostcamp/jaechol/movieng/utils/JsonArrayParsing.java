package com.boostcamp.jaechol.movieng.utils;

import com.boostcamp.jaechol.movieng.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * RESPONSE JSON 데이터를 MovieModel 로 변환을 위한 클래스.
 */

public class JsonArrayParsing {
    private JSONArray ArrayItems;
    private int thresdholds = 0;
    private ArrayList<MovieModel> arrayListMovie;

    public JsonArrayParsing(JSONArray ArrayItems, int thresdholds, ArrayList<MovieModel> arrayListMovie) {
        this.ArrayItems = ArrayItems;
        this.thresdholds = thresdholds;
        this.arrayListMovie = arrayListMovie;
    }
    public ArrayList<MovieModel> getParseList() {
        for (int iter = 0; iter < thresdholds; iter++) {
            JSONObject jsonObject_items = null;
            try {
                jsonObject_items = ArrayItems.getJSONObject(iter);
                String title = jsonObject_items.getString("title");
                String link = jsonObject_items.getString("link");
                String image_url = jsonObject_items.getString("image");
                if (image_url.equals(""))
                    image_url = Const.NoImageURL;
                String director = jsonObject_items.getString("director");
                String actor = jsonObject_items.getString("actor");
                String pubDate = jsonObject_items.getString("pubDate");
                float userRating = (float) jsonObject_items.getDouble("userRating");
                title = title.replace("<b>", "");
                title = title.replace("</b>", "");
                MovieModel data = new MovieModel(title, link, image_url, director, actor, pubDate, userRating);
                arrayListMovie.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrayListMovie;
    }
}
