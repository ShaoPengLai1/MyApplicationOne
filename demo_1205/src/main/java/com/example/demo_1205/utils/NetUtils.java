package com.example.demo_1205.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class NetUtils {
    private static NetUtils intance;
    private Gson gson;
    public NetUtils() {
        gson = new Gson();
    }

    public static NetUtils getIntance() {
        if(intance == null){
            intance = new NetUtils();
        }
        return intance;
    }
    //执行网络请求返回String
    public String getRequest(String urlStr){
        String result = "";
        try {
            //定义url地址
            URL url = null;
            try {
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            //打开连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //设置请求格式
            try {
                urlConnection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            //设置超时
            urlConnection.setReadTimeout(5000);
            urlConnection.setConnectTimeout(5000);
            //获取请求码
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200){
                result = stream2String(urlConnection.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //将字节流转换为总分刘
    private String stream2String(InputStream inputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        for (String tmp = br.readLine();tmp!=null;tmp = br.readLine()){
            builder.append(tmp);
        }
        return builder.toString();
    }
    //执行网络请求返回Bean
    public <E> E getRequest(String urlStr,Class clazz){
        return (E) gson.fromJson(getRequest(urlStr),clazz);
    }
    //定义接口
    public interface CallBack<E>{
        void onSuccess(E e);
    }
    //异步请求
    @SuppressLint("StaticFieldLeak")
    public void getRequest(String urlStr, final Class clazz, final CallBack callBack){
        new AsyncTask<String,Void,Object>(){
            @Override
            protected Object doInBackground(String... strings) {
                return getRequest(strings[0],clazz);
            }

            @Override
            protected void onPostExecute(Object o) {
                callBack.onSuccess(o);
            }
        }.execute(urlStr);
    }
}
