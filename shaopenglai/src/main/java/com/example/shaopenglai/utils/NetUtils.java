package com.example.shaopenglai.utils;

import com.example.shaopenglai.model.User;

public class NetUtils {

    public static boolean loginApi(User user){
        if (user.getName().equals("13030023000")&&user.getVerification().equals("1234")){
            return true;
        }
        return false;
    }
}
