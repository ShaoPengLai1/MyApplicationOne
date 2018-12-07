package com.example.shaopenglai1206.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shaopenglai1206.R;
import com.example.shaopenglai1206.bean.NewsBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<NewsBean.DataBean> mData;
    private Context context;

    public NewsAdapter(Context context) {
        this.context = context;
        mData=new ArrayList<>();
    }

    public void setData(List<NewsBean.DataBean> datas) {
        mData.clear();
        if (datas!=null){
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void addData(List<NewsBean.DataBean> datas) {
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
    public NewsBean.DataBean getItem(int position) {
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
            convertView=LayoutInflater.from(context).inflate(R.layout.item,parent,false);
            holder=new ViewHolder(convertView);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.bind(getItem(position));
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

        public void bind(NewsBean.DataBean item) {
            title.setText(item.getTitle());
            ImageLoader.getInstance().displayImage(item.getImages(),icon);
        }
    }
}
