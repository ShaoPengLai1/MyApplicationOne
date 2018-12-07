package com.example.mymvpdemo1.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.mymvpdemo1.bean.LoginBean;
import com.example.mymvpdemo1.utils.NetUtils;
import com.example.mymvpdemo1.view.IView;

public class LoginPresenter {
    private IView mIView;
    private final int PASS_LENGTH=6;

    public LoginPresenter(@NonNull IView iView) {
        mIView = iView;
    }
    public void submit(LoginBean loginBean){
        if(checkName(loginBean.getName()) && checkPw(loginBean.getPassword())){
            //进行网络请求
            boolean loginResult = NetUtils.login(loginBean);
            //拿到结果后
            if (loginResult){
                //通过view的实例，把数据回调给view
                mIView.onSuccess("");
            }else{
                //通过view的实例，把数据回调给view
                mIView.fail("失败");
            }
        }else{
            //通过view的实例，把数据回调给view
            mIView.fail("用户名密码错");
        }
    }


    public void detachView(){
        mIView = null;
    }



    private boolean checkName(String name){
        return !TextUtils.isEmpty(name);
    }

    private boolean checkPw(String passWord){
        return (!TextUtils.isEmpty(passWord) && passWord.length()>=6);
    }
}
