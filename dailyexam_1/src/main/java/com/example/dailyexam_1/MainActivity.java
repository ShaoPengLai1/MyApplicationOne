package com.example.dailyexam_1;

import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.dailyexam_1.adapter.ShoppingAdapter;
import com.example.dailyexam_1.bean.ShoppingBean;
import com.example.dailyexam_1.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager contents;
    private TextView original;
    private TextView discounts;
    private TextView title;
    private String url="http://www.zhaoapi.cn/product/getProductDetail?pid=%d";
    private ShoppingAdapter adapter;
    private int mPage;
    private List<String> list=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contents=findViewById(R.id.contents);
        original=findViewById(R.id.original);
        discounts = findViewById(R.id.discounts);
        title = findViewById(R.id.title);
        adapter=new ShoppingAdapter(this);
        contents.setAdapter(adapter);
        original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        initData();
    }

    private void initData() {
        mPage=1;
        NetUtils.getInstance().getResult(String.format(url, mPage), ShoppingBean.class, new NetUtils.CallBack<ShoppingBean>() {
            @Override
            public void onSuccess(ShoppingBean o) {
                sub(o.getData().getImages());
                adapter.setData(list);
                title.setText(o.getData().getTitle());
                original.setText("原价："+o.getData().getPrice());
                discounts.setText("优惠价："+o.getData().getBargainPrice());
                int center=adapter.getCount()/2;
                center=center-center%(list.size());
                contents.setCurrentItem(center);
                startLooper();
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            contents.setCurrentItem(contents.getCurrentItem()+1);
            handler.sendEmptyMessageDelayed(0,2000);
        }
    };
    private void startLooper() {
        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessageDelayed(0,2000);
    }

    private void sub(String str) {

        int index=str.indexOf("|");
        if (index>=0){
            String strLeft=str.substring(0,index);
            list.add(strLeft);
            sub(str.substring(index+1,str.length()));
        }else {
            list.add(str);
        }
    }
}
