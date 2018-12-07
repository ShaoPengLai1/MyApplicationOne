package com.example.peng.myapplication;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpHelper {

    private final int SUCCESS_HTTP=1000;
    private final int FAIL_HTTP=10001;
    public HttpHelper(){}

    public HttpHelper get(String url){
        doHttp(url,"GRT",null);
        return this;
    }
    public HttpHelper post(String url,String string){
        doHttp(url,"POST",string);
        return this;
    }

    private void doHttp(final String url,final String method,final String string) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL u=new URL(url);
                    HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
                    urlConnection.setRequestMethod(method);
                    urlConnection.setReadTimeout(5000);
                    urlConnection.setConnectTimeout(5000);
                    if ("POST".equals(method)){
                        PrintWriter writer=new PrintWriter(urlConnection.getOutputStream());
                        writer.write(string);
                        writer.flush();
                        writer.close();
                    }
                    urlConnection.connect();
                    int code = urlConnection.getResponseCode();
                    Message message=Message.obtain();
                    if (code==HttpURLConnection.HTTP_OK){
                        message.what=SUCCESS_HTTP;
                        InputStream is=urlConnection.getInputStream();
                        String data=stream2String(is);
                        message.obj=data;
                    }else {
                        message.what=FAIL_HTTP;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SUCCESS_HTTP:
                    String data= (String) msg.obj;
                    listener.success(data);
                    break;
                case FAIL_HTTP:
                    listener.fail();
                    break;
            }
        }
    };


    public interface HttpListener{
        void success(String data);
        void fail();
    }

    private HttpListener listener;
    public void result(HttpListener listener){
        this.listener=listener;
    }
    private String stream2String(InputStream input) {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        int len=1;
        byte[] buffer=new byte[512];
        try {
            while ((len=input.read(buffer))!=-1){
                stream.write(buffer,0,len);
            }
            return new String(stream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
