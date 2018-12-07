package com.example.mymvpdemo1.model;

import com.example.mymvpdemo1.callback.MyCallBack;

public interface IModel {
    void startRequest(String url, String params, MyCallBack callBack);
}
