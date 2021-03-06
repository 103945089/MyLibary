package shopping.hlhj.com.mylibrary.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.youth.banner.BannerConfig;

import java.util.Date;
import java.util.List;

import retrofit2.http.GET;
import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.R;
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
                .params("token",TMSharedPUtil.getTMToken(context))
                .headers("token",TMSharedPUtil.getTMToken(context))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        int code = jsonObject.getInteger("code");
                        if (code == 1) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<CommentBean.CommentData> commentBeans = new Gson().fromJson(data.toString(), new TypeToken<List<CommentBean.CommentData>>() {
                            }.getType());
                            if (commentBeans != null && commentBeans.size() > 0) {
                                if (getView()!=null){
                                    getView().loadCommentSuccess(commentBeans);
                                }
                            } else {
                                if (getView()!=null){
                                    getView().loadFailed("1");
                                }
                            }
                        } else {
                            if (getView()!=null){
                                getView().loadFailed("2");
                            }
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
    public void sendComment(final Context context, int live_id, String content, String token){
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
                            if (getView()!=null){
                                getView().loadSendCommentSuccess("200");
                            }
                        }else {
                            if (getView()!=null){
                                getView().loadFailed("评论失败");
                            }
                        }
                        if (getView()!=null){
                            getView().loadFailed(jsonObject.getString("msg"));
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(context,"评论失败",Toast.LENGTH_SHORT).show();
                        super.onError(response);
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
                            if (getView()!=null){
                                getView().loadDanmu(danMuBean);
                            }
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
                .params("token",TMSharedPUtil.getTMToken(context))
                .headers("token",TMSharedPUtil.getTMToken(context))
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
                                if (getView()!=null){
                                    getView().loadLiveMoreSuccess(moreDatas);
                                }
                            } else {
                                if (getView()!=null){
                                    getView().loadFailed(jsonObject.getString("message"));
                                }
                            }
                        }
                    }
                });
    }

    //直播详情
    public void loadLiveDetail(Context context, int id) {
        OkGo.<String>get(Constant.LIVE_DETAIL)
                .tag(context)
                .params("token",TMSharedPUtil.getTMToken(context))
                .headers("token",TMSharedPUtil.getTMToken(context))
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
                            if (getView()!=null){
                                getView().loadLiveDetail(liveDetailBean);
                            }
                        }
                    }
                });
    }
    //点赞
    public void likeIt(Context context,int id ,int type){
        OkGo.<String>post(Constant.ITS_GOOD)
                .params("id",id)
                .params("token", TMSharedPUtil.getTMToken(context))
                .headers("token",TMSharedPUtil.getTMToken(context))
                .params("type",2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200){
                            if (getView()!=null){
                                getView().likeSuccess();
                            }
                        }else if (code==500){
                            if (getView()!=null){
                                getView().likeErro();
                            }
                        }
                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
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

        void likeSuccess();

        void likeErro();
    }
}
