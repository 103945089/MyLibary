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

import retrofit2.http.GET;
import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.data.Constant;

public class LiveNewsPresenter extends BasePresenter<LiveNewsPresenter.LiveNewsView> {

    public LiveNewsPresenter(LiveNewsView baseview) {
        super(baseview);
    }

    /**
     * 加载直播详情界面数据
     * @param context
     * @param id
     * @param uid
     */
    public void loadLiveNesData(Context context,int id,int uid){
        OkGo.<String>get(Constant.DETAIL_URL)
                .tag(context)
                .params("id",id)
                .params("uid",uid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200){
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("comment");
                            DetailBean.DetailDatas detailDatas = new Gson().fromJson(data.toString()
                                    , new TypeToken<DetailBean.DetailDatas>() {}.getType());
                            List<DetailBean.DetailDatas.CommentBean> commentBeanList = new Gson().fromJson(jsonArray.toString()
                                    , new TypeToken<List<DetailBean.DetailDatas.CommentBean>>(){}.getType());
                            if (null != detailDatas){
                                getView().loadSuccess(detailDatas);
                            }else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                            if (null != commentBeanList && commentBeanList.size() > 0){
                                getView().loadCommentSuccess(commentBeanList);
                            }else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                        }
                    }
                });

    }

    public interface LiveNewsView extends BaseView {
        void loadSuccess(DetailBean.DetailDatas detailDatas);

        void loadCommentSuccess(List<DetailBean.DetailDatas.CommentBean> commentBeans);

        void loadFailed(String msg);

    }
}
