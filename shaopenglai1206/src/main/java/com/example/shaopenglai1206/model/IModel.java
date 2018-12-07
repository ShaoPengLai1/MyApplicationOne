package com.example.shaopenglai1206.model;

import com.example.shaopenglai1206.callback.MyCallBack;

public interface IModel {
    void getResult(String url, String params, Class clazz, MyCallBack callBack);
}
