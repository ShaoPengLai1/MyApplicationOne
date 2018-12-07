package com.example.mvpdemo2.ui;

public interface Iview<E> {
    void success(E e);
    void error(String str);
}
