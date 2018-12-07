package com.example.shaopenglai.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.shaopenglai.model.User;
import com.example.shaopenglai.utils.NetUtils;
import com.example.shaopenglai.view.IView;

public class LoginPresenter {
    private IView mIView;

    public LoginPresenter(@NonNull IView iView) {
        mIView = iView;
    }
    public void submit(User user){
        if (checkName(user.getName())&&checkVerification(user.getVerification())){
            boolean boo = NetUtils.loginApi(user);
            if (boo){
                mIView.onSuccess("");
            }else {
                mIView.onFail("失败");
            }
        }else {
            mIView.onFail("手机号验证码错误");
        }
    }

    public void detachView(){
        mIView=null;
    }
    private boolean checkVerification(String verification) {
        return (!TextUtils.isEmpty(verification)&&verification.length()>=4);
    }

    private boolean checkName(String name) {
        return !TextUtils.isEmpty(name);
    }
}
