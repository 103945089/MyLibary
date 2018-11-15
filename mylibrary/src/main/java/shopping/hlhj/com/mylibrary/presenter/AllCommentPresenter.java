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

import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.data.Constant;

/**
 * Created by Never Fear   on 2018\10\25 0025.
 * Never More....
 */

public class AllCommentPresenter extends BasePresenter<AllCommentPresenter.AllCommentView> {


    public AllCommentPresenter(AllCommentView baseview) {
        super(baseview);
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
                .params("token", TMSharedPUtil.getTMToken(context))
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
                                getView().loadFailed("暂无更多评论");
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
    public interface AllCommentView extends BaseView {
        void loadFailed(String str);

        void loadCommentSuccess(List<CommentBean.CommentData> commentBeans);
    }
}
