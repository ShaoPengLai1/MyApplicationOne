package com.example.mvpdemo2.presenter;


import com.example.mvpdemo2.LoginActivity;
import com.example.mvpdemo2.bean.RegisterBean;
import com.example.mvpdemo2.ui.Iview;
import com.example.mvpdemo2.utils.NetUtil;


public class LoginPersenter {
    /**
     *  持有view的实例
     */
    private Iview mIview;
    public LoginPersenter(Iview iview) {
        mIview = iview;
    }
    //提交
    public void submit(String registerUrl, String names, String passwords) {
        NetUtil.getIntance().getRequest(String.format(registerUrl,names,passwords), RegisterBean.class, new NetUtil.CallBack<RegisterBean>() {
            @Override
            public void onSuccess(RegisterBean o) {
                if(o!=null && o.isSuccess()){
                    //登录成功
                    //通过view的实例，把数据回调给view
                    mIview.success(o.getMsg());
                }else{
                    //登录失败
                    //通过view的实例，把数据回调给view
                    mIview.error(o.getMsg());
                }
            }
        });
    }
    public void datechView(){
        mIview = null;
    }
}
