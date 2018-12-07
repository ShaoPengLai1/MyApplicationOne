package com.example.mymvpdemo1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymvpdemo1.bean.LoginBean;
import com.example.mymvpdemo1.presenter.LoginPresenter;
import com.example.mymvpdemo1.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {

    private EditText mAccount_number_edit,mPassword_edit;
    private Button mSubmit,mRegister;
    LoginPresenter mLoginPresenter;
    private String url="http://120.27.23.105/user/login?mobile=\"+mMobile+\"&password=\"+mPassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPresenter();
    }

    private void initPresenter() {
        mLoginPresenter=new LoginPresenter(this);
    }

    private void initView() {
        mAccount_number_edit=findViewById(R.id.Account_number_edit);
        mPassword_edit=findViewById(R.id.password_edit);
        mSubmit=findViewById(R.id.submit);
        mRegister=findViewById(R.id.register);
        mPassword_edit.invalidate();
        mSubmit.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        findViewById(R.id.icon).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mPassword_edit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_eye);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    mPassword_edit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_name);
                }
                return false;
            }

        });
    }


    @Override
    public void onClick(View v) {
        int mId = v.getId();
        switch (mId){
            case R.id.submit:
                LoginBean bean=new LoginBean(mAccount_number_edit.getText().toString(),mPassword_edit.getText().toString());
                mLoginPresenter.submit(bean);
                break;
            case R.id.register:
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            default:
                    break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.detachView();
    }

    @Override
    public void onSuccess(Object data) {
        //接受到了结果，进行数据展示
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String msg) {
        //接受到了结果，进行数据展示
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
