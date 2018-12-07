package com.example.shaopenglai1206.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.shaopenglai1206.R;
import com.example.shaopenglai1206.bean.NewsBean;
import com.example.shaopenglai1206.persenter.IPersenterImpl;
import com.example.shaopenglai1206.ui.adapter.NewsAdapter;
import com.example.shaopenglai1206.utils.NetUtils;
import com.example.shaopenglai1206.view.IView;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;

public class MainActivity extends AppCompatActivity implements IView {

    private int mPage;
    private XListView contents;
    private NewsAdapter adapter;
    private IPersenterImpl mIPersenter;
    private List<String> list=new ArrayList<>();
    private String url="http://www.zhaoapi.cn/product/getProducts?pscid=1&page=%d";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mPage=1;
        contents=findViewById(R.id.contents);
        adapter=new NewsAdapter(MainActivity.this);
        mIPersenter=new IPersenterImpl(this);
        contents.setAdapter(adapter);
        contents.setPullLoadEnable(true);
        contents.setPullLoadEnable(true);
        contents.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        loadData();
    }



    @Override
    public void success(Object o) {
        String s = String.valueOf(o);
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();

    }

    @Override
    public void fail(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }
    private void loadData() {
        NetUtils.getInstance().getResult(String.format(url, mPage), NewsBean.class, new NetUtils.CallBack<NewsBean>() {
            @Override
            public void onSuccess(NewsBean o) {
                if (mPage==1){

                    adapter.setData(o.getData());
                }else {
                    adapter.addData(o.getData());
                }
                mPage++;
                contents.stopLoadMore();
                contents.stopRefresh();
            }
        });
        mIPersenter.getRessult(url,null,NewsBean.class);
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPersenter.onDetach();
    }
}
