package com.example.peng.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {


    private EditText mAccount_number_edit;
    private EditText mPassword_edit;
    private Button mSubmit;
    private Button mRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccount_number_edit=findViewById(R.id.Account_number_edit);
        mPassword_edit=findViewById(R.id.password_edit);
        mSubmit=findViewById(R.id.submit);
        mRegister=findViewById(R.id.register);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mMobile = mAccount_number_edit.getText().toString().trim();
                String mPassword = mPassword_edit.getText().toString().trim();
                final String LOGINURL="http://120.27.23.105/user/login?mobile="+mMobile+"&password="+mPassword;
                new HttpHelper().get(LOGINURL).result(new HttpHelper.HttpListener() {
                    @Override
                    public void success(String data) {
                        Gson gson=new Gson();
                        LoginBean loginBean=gson.fromJson(data,LoginBean.class);
                        if (loginBean.getCode().equals("0")){
                            Intent intent=new Intent(MainActivity.this,ShowActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,loginBean.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail() {

                    }
                });
            }
        });


    }

}
