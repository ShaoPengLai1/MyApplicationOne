package com.example.mymvpdemo1.view;

public interface IView<T> {

    void onSuccess(T data);
    void fail(String msg);
}
