package com.boostcamp.jaechol.movieng.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 네이버 API 연결 및 RESPONSE를 위한 CLASS
 */

public class ApiConnection {
    private static String movieUrl = Const.ApiUrl + Const.ApiType + "?query=";
    private StringBuilder response;
    private BufferedReader br;
    private String line;

    public ApiConnection() {
    }

    public StringBuilder getResult(String title, long start) {
        try {
            String start_query = "&start=" + start;
            title = URLEncoder.encode(title, "utf-8");
            URL url = new URL(movieUrl + title + start_query);
            Log.d("URL : ", url.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(Const.ClientId, Const.ClientId_Value);
            con.setRequestProperty(Const.ClientSecret, Const.ClientSecret_Value);
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line + "\n");
            }
            br.close();
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
