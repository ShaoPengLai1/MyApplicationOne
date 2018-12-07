package com.example.demo_1205.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo_1205.R;
import com.example.demo_1205.bean.NewsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<NewsBean.PostsBean> mData;
    private Context mContext;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
        mData=new ArrayList<>();
    }

    public void setData(List<NewsBean.PostsBean> datas) {
        mData.clear();
        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void addData(List<NewsBean.PostsBean> datas) {

        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public NewsBean.PostsBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
            holder=new ViewHolder(convertView);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.bindData(getItem(position));
        return convertView;
    }
    class ViewHolder{
        ImageView icon;
        TextView title;

        public ViewHolder(View convertView) {
            icon=convertView.findViewById(R.id.icon);
            title=convertView.findViewById(R.id.title);
            convertView.setTag(this);
        }

        public void bindData(NewsBean.PostsBean item) {
            title.setText(item.getTitle());
            ImageLoader.getInstance().displayImage(item.getUrl(),icon);
        }
    }
}
