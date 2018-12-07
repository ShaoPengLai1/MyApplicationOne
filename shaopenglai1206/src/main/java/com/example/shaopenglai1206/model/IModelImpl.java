package com.example.shaopenglai1206.model;

import com.example.shaopenglai1206.callback.MyCallBack;
import com.example.shaopenglai1206.utils.NetUtils;

public class IModelImpl implements IModel {


    private MyCallBack callBack;

    @Override
    public void getResult(String url, String params, Class clazz, final MyCallBack callBack) {
        this.callBack=callBack;
        NetUtils.getInstance().getResult(url, clazz, new NetUtils.CallBack() {
            @Override
            public void onSuccess(Object o) {
                callBack.setData(o);
            }
        });
    }
}
