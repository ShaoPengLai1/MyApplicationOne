package com.example.mymvpdemo1.utils;

import android.os.SystemClock;

import com.example.mymvpdemo1.bean.LoginBean;

public class NetUtils {
    public static boolean login(LoginBean bean){
        SystemClock.sleep(2000);
        if (bean.getName().equals("阿里巴巴")&&bean.getPassword().equals("121213")){
            return true;
        }
        return false;
    }

}
