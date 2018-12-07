package com.example.mvpdemo2;

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

import com.example.mvpdemo2.presenter.LoginPersenter;
import com.example.mvpdemo2.ui.Iview;
import com.example.mvpdemo2.ui.ProductActivity;
import com.example.mvpdemo2.utils.NonNull;


public class MainActivity extends AppCompatActivity implements Iview,View.OnClickListener{
    private String registerUrl = "http://120.27.23.105/user/login?mobile=%s&password=%s";
    private EditText name;
    private EditText password;
    private Button btn_register;
    private Button btn_begin;
    private LoginPersenter persenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //创建一个Presenter实例
        initPresenter();
    }
    /**
     * 绑定Presenter
     */
    private void initPresenter() {
        //把view传给presenter进行绑定
        persenter = new LoginPersenter(this);
    }
    //初始化view
    private void initView() {
        //获取资源id
        name = findViewById(R.id.Account_number_edit);
        password = findViewById(R.id.password_edit);
        btn_register = findViewById(R.id.submit);
        btn_begin = findViewById(R.id.register);
        //登录按钮
        btn_register.setOnClickListener(this);
        //注册按钮
        btn_begin.setOnClickListener(this);
        findViewById(R.id.icon).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_eye);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    findViewById(R.id.icon).setBackgroundResource(R.drawable.ic_action_name);
                }
                return false;
            }

        });
    }
    /**
     * 登录成功
     * //接受到了结果，进行数据展示
     * */
    @Override
    public void success(Object o) {
        String s = String.valueOf(o);
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,ProductActivity.class);
        startActivity(intent);
    }
  /**
   * 登录失败
   * //接受到了结果，进行数据展示
   * */
    @Override
    public void error(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            //登录按钮
            case R.id.submit:
                //获取输入框的值
                String names = name.getText().toString();
                String passwords = password.getText().toString();
                //判断是否为空如果不为空调用LoginPersenter类的submit()方法
                if(NonNull.getInstance().isNonNull(names,passwords)){
                    //通过presenter的实例，调用presenter中的方法
                    persenter.submit(registerUrl,names,passwords);
                }else{
                    Toast.makeText(MainActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
                //注册按钮
            case R.id.register:
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                default:
                    break;

        }
    }
    /**
     * 在Activity結束的時候解綁Presenter
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        persenter.datechView();
    }
}
