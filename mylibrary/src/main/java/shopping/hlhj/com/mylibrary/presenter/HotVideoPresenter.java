package shopping.hlhj.com.mylibrary.presenter;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.List;

import io.reactivex.annotations.Nullable;
import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.TuijianData;
import shopping.hlhj.com.mylibrary.data.Constant;

public class HotVideoPresenter extends BasePresenter<HotVideoPresenter.HotVideoView> {

    public HotVideoPresenter(HotVideoView baseview) {
        super(baseview);
    }

    //加载详情数据
    public void loadVideoData(Context context, int id, int uid) {
        OkGo.<String>get(Constant.DETAIL_URL)
                .tag(context)
                .params("id", id)
                .params("uid", uid)
                .headers("token", TMSharedPUtil.getTMToken(context))
                .params("token", TMSharedPUtil.getTMToken(context))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            DetailBean.DetailDatas detailDatas = new Gson().fromJson(data.toString()
                                    , new TypeToken<DetailBean.DetailDatas>() {
                                    }.getType());
                            if (null != detailDatas && !detailDatas.equals("")) {
                                if (getView()!=null){
                                    getView().loadDataSuccess(detailDatas);
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
    public void loadRecomend(Context context){
        OkGo.<String>post(Constant.TUIJIAN_URL)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        Integer code = jsonObject.getInteger("code");
                        if (code==200){
                            TuijianData tuijianData = new Gson().fromJson(response.body(), TuijianData.class);
                            getView().loadTuiJian(tuijianData);
                        }
                    }
                });
    }
    //加载更多  热门type = 1 推荐 = 8
    public void loadMoreVideoData(Context context, int page) {
        OkGo.<String>post(Constant.HOT_MORE)
                .tag(context)
                .params("type", 1)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<MoreBean.MoreDatas> moreDatas = new Gson().fromJson(data.toString()
                                    , new TypeToken<List<MoreBean.MoreDatas>>() {}.getType());
                            if (null != moreDatas && moreDatas.size() > 0){
                                if (getView()!=null){
                                    getView().loadHotMoreSuccess(moreDatas);
                                }
                            }else {
                                if (getView()!=null){
                                    getView().loadFailed(jsonObject.getString("message"));
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 加载热门和文章的评论数据
     *
     * @param context
     * @param id
     * @param page
     */
    public void loadHotCommentData(Context context, int id, int page) {
        OkGo.<String>get(Constant.OTHER_COMMENT_LIST)
                .tag(context)
                .params("token",TMSharedPUtil.getTMToken(context))
                .headers("token",TMSharedPUtil.getTMToken(context))
                .params("id", id)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<CommentBean.CommentData> commentBeans = new Gson().fromJson(data.toString(), new TypeToken<List<CommentBean.CommentData>>() {
                            }.getType());
                            if (null == commentBeans || "".equals(commentBeans)){
                                if (getView()!=null){

                                    getView().loadFailed("暂无更多评论");
                                }
                                return;
                            }
                            if (commentBeans != null && commentBeans.size() > 0) {
                                if (getView()!=null){
                                    getView().loadCommentSuccess(commentBeans);
                                }
                            }
                        } else {
                            if (getView()!=null){
                                getView().loadFailed("1");
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

    //热门和文章的发布评论
    public void sendComment(Context context,int id,int uid,String avatar,String username,String content){
        OkGo.<String>post(Constant.OTHER_SEND_COMMENT)
                .tag(context)
                .params("nid",id)
                .params("uid",uid)
                .params("avatar",avatar)
                .params("username",username)
                .params("content",content)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (getView()!=null){
                            if (code == 200){
                                getView().loadSendCommentSuccess(jsonObject.getString("message"));
                            }else {

                                getView().loadFailed("评论失败");
                            }
                        }


                    }
                });
    }


    public interface HotVideoView extends BaseView {
        void loadDataSuccess(DetailBean.DetailDatas detailDatas);

        void loadFailed(String msg);

        void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas);

        void loadCommentSuccess(@Nullable List<CommentBean.CommentData> commentData);

        void loadSendCommentSuccess(String msg);

        void loadTuiJian(TuijianData data);
    }
}
