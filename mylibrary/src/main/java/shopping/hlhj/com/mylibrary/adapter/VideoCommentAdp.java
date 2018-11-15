package shopping.hlhj.com.mylibrary.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.GlideUtil;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.Tool.MyTimeUtils;
import shopping.hlhj.com.mylibrary.Tool.TimeUtil;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.data.Constant;

/**
 * Created by Never Fear   on 2018\10\23 0023.
 * Never More....
 */

public class VideoCommentAdp  extends BaseQuickAdapter<CommentBean.CommentData,BaseViewHolder>{

    public VideoCommentAdp(@Nullable List<CommentBean.CommentData> data) {
        super(R.layout.adapter_comment,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CommentBean.CommentData item) {
        helper.setText(R.id.tv_num,item.laud_num+"");
        helper.setText(R.id.tv_username,item.username);
        GlideUtil.INSTANCE.loadHead(mContext,item.avatar, (ImageView) helper.getView(R.id.img_user));
        helper.getView(R.id.img_zan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.is_laud==1){//取消蒂娜赞
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",item.id)
                            .params("token", TMSharedPUtil.getTMToken(mContext))
                            .headers("token",TMSharedPUtil.getTMToken(mContext))
                            .params("type",3)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        item.setIs_laud(0);
                                        item.setLaud_num(item.laud_num-1);
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });
                }else {
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",item.id)
                            .params("token",TMSharedPUtil.getTMToken(mContext))
                            .headers("token",TMSharedPUtil.getTMToken(mContext))
                            .params("type",3)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        item.setIs_laud(1);
                                        item.setLaud_num(item.laud_num+1);
                                        notifyDataSetChanged();
                                    }else if (code==500){
                                        GoLoginDialog goLoginDialog = new GoLoginDialog(mContext);
                                        goLoginDialog.show();
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });
                }
            }
        });

        if (item.is_laud == 1){
            helper.setImageResource(R.id.img_zan,R.drawable.ic_home_praise_select);
        }else {
            helper.setImageResource(R.id.img_zan,R.drawable.ic_home_praise_normal);
        }

        helper.setText(R.id.tv_comment_content,item.content);

        if (item.create_time!=null&&!item.create_time.isEmpty()){
            helper.setText(R.id.tv_time, MyTimeUtils.convertTimeToCustom(Long.parseLong(item.create_time)));

        }
        /*String s = JavaUtils.StampstoTime(String.valueOf(item.create_at), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            helper.setText(R.id.tv_time,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
    }
}
