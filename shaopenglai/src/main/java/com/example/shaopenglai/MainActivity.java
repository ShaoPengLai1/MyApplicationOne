package com.example.shaopenglai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shaopenglai.model.User;
import com.example.shaopenglai.presenter.LoginPresenter;
import com.example.shaopenglai.view.IView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,IView {

    private EditText phone_number,verification;
    private LoginPresenter mLoginPresenter;
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
        phone_number=findViewById(R.id.phone_number);
        verification=findViewById(R.id.Verification);
        verification.invalidate();
        findViewById(R.id.submit_area).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.submit_area:
                User user=new User(phone_number.getText().toString(),verification.getText().toString());
                mLoginPresenter.submit(user);
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
        Toast.makeText(this,"成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFail(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }
}
