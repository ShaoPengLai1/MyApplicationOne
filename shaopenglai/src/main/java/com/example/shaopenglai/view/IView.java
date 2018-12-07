package com.example.shaopenglai.view;

public interface IView <T>{
    void onSuccess(T data);
    void onFail(String msg);
}
