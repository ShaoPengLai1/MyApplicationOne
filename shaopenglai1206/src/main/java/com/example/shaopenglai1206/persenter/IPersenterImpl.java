package com.example.shaopenglai1206.persenter;

import com.example.shaopenglai1206.bean.NewsBean;
import com.example.shaopenglai1206.callback.MyCallBack;
import com.example.shaopenglai1206.model.IModelImpl;
import com.example.shaopenglai1206.view.IView;

public class IPersenterImpl implements IPersenter{

    private IModelImpl iModel;
    private IView iView;
    public IPersenterImpl(IView iView){
        this.iView=iView;
        iModel=new IModelImpl();
    }
    @Override
    public void getRessult(String url, String params, Class clazz) {
        iModel.getResult(url, null, clazz, new MyCallBack() {
            @Override
            public void setData(Object data) {
                if (data instanceof NewsBean){
                    NewsBean newsBean= (NewsBean) data;
                    if (newsBean==null||newsBean.isSuccess()){
                        iView.fail(newsBean.getMsg());
                    }else {
                        iView.success(newsBean);
                    }
                }
            }
        });
    }
    public void onDetach(){
        if (iModel!=null){
            iModel=null;
        }
        if (iView!=null){
            iView=null;
        }
    }
}
