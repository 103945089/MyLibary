package shopping.hlhj.com.mylibrary.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.youth.banner.BannerConfig;

import java.util.Date;
import java.util.List;

import retrofit2.http.GET;
import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.bean.BaseBean;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DanMuBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.LiveDetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.bean.TopBanner;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.fragment.FragmentIndexChoice;

public class LiveNewsPresenter extends BasePresenter<LiveNewsPresenter.LiveNewsView> {

    public LiveNewsPresenter(LiveNewsView baseview) {
        super(baseview);
    }

    /**
     * 加载评论数据
     *
     * @param context
     * @param id
     * @param page
     */
    public void loadLiveCommentData(Context context, int id, int page) {
        OkGo.<String>get(Constant.COMMENT_LIST)
                .tag(context)
                .params("live_id", id)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        int code = jsonObject.getInteger("code");
                        if (code == 1) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<CommentBean.CommentData> commentBeans = new Gson().fromJson(data.toString(), new TypeToken<List<CommentBean.CommentData>>() {
                            }.getType());
                            Log.e("zy", "--4--" + commentBeans.toString());
                            if (commentBeans != null && commentBeans.size() > 0) {
                                getView().loadCommentSuccess(commentBeans);
                            } else {
                                getView().loadFailed("1");
                            }
                        } else {
                            getView().loadFailed("1");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        response.getException().printStackTrace();
                        Log.e("zy", "错了---------------------" + response.getException());
                        super.onError(response);

                    }
                });
    }

    //发布评论
    public void sendComment(Context context,int live_id,String content,String token){
        OkGo.<String>get(Constant.SEND_COMMENT)
                .tag(context)
                .params("live_id",live_id)
                .params("content",content)
                .params("token",token)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 1){
                            getView().loadSendCommentSuccess(jsonObject.getString("msg"));
                        }
                    }
                });
    }


    /**
     * 获取弹幕
     */
    public void getDanmuData(int live_id) {
        OkGo.<String>get(Constant.getDanmu)
                .params("live_id", live_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 1) {
                            Gson gson = new Gson();
                            DanMuBean danMuBean = gson.fromJson(body, DanMuBean.class);
                            getView().loadDanmu(danMuBean);
                        }
                    }
                });
    }

    /**
     * 发送弹幕
     */
    public void sendDanmu(String token, int lid, String content) {
        OkGo.<String>get(Constant.senDDanmu)
                .params("token", token)
                .params("lid", lid)
                .params("content", content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 1) {
                            Gson gson = new Gson();
                            BaseBean baseBean = gson.fromJson(body, BaseBean.class);
                        }
                    }
                });
    }

    /**
     * 直播加载更多
     *
     * @param context
     */
    public void loadLiveMoreData(Context context, int page) {
        OkGo.<String>get(Constant.LIVE_MORE)
                .tag(context)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 1) {
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            List<MoreBean.MoreDatas> moreDatas = new Gson().fromJson(jsonArray.toString()
                                    , new TypeToken<List<MoreBean.MoreDatas>>() {
                                    }.getType());
                            if (moreDatas != null && moreDatas.size() > 0) {
                                getView().loadLiveMoreSuccess(moreDatas);
                            } else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                        }
                    }
                });
    }

    //直播详情
    public void loadLiveDetail(Context context, int id) {
        OkGo.<String>get(Constant.LIVE_DETAIL)
                .tag(context)
                .params("live_id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        if (jsonObject.getInteger("code") == 1) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            LiveDetailBean.LiveDetail liveDetailBean = new Gson().fromJson(data.toString(), new TypeToken<LiveDetailBean.LiveDetail>() {
                            }.getType());
                            getView().loadLiveDetail(liveDetailBean);
                        }
                    }
                });
    }

    public interface LiveNewsView extends BaseView {
        void loadLiveMoreSuccess(List<MoreBean.MoreDatas> moreDatas);

        void loadCommentSuccess(List<CommentBean.CommentData> commentData);

        void loadFailed(String msg);

        void loadDanmu(DanMuBean danMuBean);

        void loadLiveDetail(LiveDetailBean.LiveDetail liveDetailBean);

        void loadSendCommentSuccess(String msg);

    }
}
