package com.example.mymvpdemo1.model;

import android.os.Handler;
import android.os.Message;


import com.example.mymvpdemo1.bean.RegBean;
import com.example.mymvpdemo1.callback.MyCallBack;
import com.example.mymvpdemo1.utils.HttpUtils;
import com.google.gson.Gson;

public class IModelImpl implements IModel {

    private MyCallBack callBack;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    RegBean regBean= (RegBean) msg.obj;
                    if (callBack!=null){
                        callBack.setData(regBean);
                    }
                    break;
                case 1:
                    break;
                    default:
                        break;
            }
        }
    };
    @Override
    public void startRequest(final String url, String params,final MyCallBack callBack) {
        this.callBack=callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = HttpUtils.getResult(url);
                    Gson gson=new Gson();
                    RegBean regBean = gson.fromJson(result, RegBean.class);
                    handler.sendMessage(handler.obtainMessage(0,regBean));
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.sendMessage(handler.obtainMessage(0,"错误"));
                }
            }
        }).start();
    }
}
