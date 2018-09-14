package shopping.hlhj.com.mylibrary.presenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.bean.ArticleBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class ArticlePresenter extends BasePresenter<ArticlePresenter.ArticleDetailView> {

    public ArticlePresenter(ArticleDetailView baseview) {
        super(baseview);
    }

    public void loadArticleDetailData(Context context,int id){
        OkGo.<String>get(Constant.ARTICLE_DETAIL)
                .tag(context)
                .params("id",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200){
                            JSONObject jsonArray = jsonObject.getJSONObject("data");
                            JSONArray jsonArray1 = jsonObject.getJSONObject("data").getJSONArray("list");
                            ArticleBean.ArticleDatas articleDatas = new Gson().fromJson(jsonArray.toString()
                                    ,new TypeToken<ArticleBean.ArticleDatas>(){}.getType());
                            List<ArticleBean.ArticleDatas.ArticleDetailBean> articleDetailBeans = new Gson().fromJson(jsonArray1.toString()
                            ,new TypeToken<List<ArticleBean.ArticleDatas.ArticleDetailBean>>(){}.getType());
                            if (null != articleDatas && !"".equals(articleDatas)){
                                getView().loadArticleDataSuccess(articleDatas);
                            }else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                            if (null != articleDetailBeans && articleDetailBeans.size() > 0){
                                getView().loadDetailDataSuccess(articleDetailBeans);
                            }else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                        }
                    }
                });
    }

    public interface ArticleDetailView extends BaseView{
        void loadArticleDataSuccess(ArticleBean.ArticleDatas articleDatas);

        void loadDetailDataSuccess(List<ArticleBean.ArticleDatas.ArticleDetailBean> articleDetailBeans);

        void loadFailed(String msg);
    }
}
