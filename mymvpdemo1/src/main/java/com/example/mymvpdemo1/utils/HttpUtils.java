package com.example.mymvpdemo1.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtils {
    private static final int RESPONSE_CODE=200;
    public static String getResult(String urlStr) throws Exception {
        String result="";
        URL url=new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(5000);
        urlConnection.setConnectTimeout(5000);
        int responseCode = urlConnection.getResponseCode();
        if (responseCode==RESPONSE_CODE){
            result=stream2String(urlConnection.getInputStream());
        }
        return result;
    }

    private static String stream2String(InputStream inputStream) throws IOException {
        StringBuilder sb=new StringBuilder();
        BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));
        for (String tmp=br.readLine();tmp!=null;tmp=br.readLine()){
            sb.append(tmp);
        }
        return sb.toString();
    }
}
