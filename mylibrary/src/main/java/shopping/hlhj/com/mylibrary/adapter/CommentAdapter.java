package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.GlideUtil;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.Tool.MyTimeUtils;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    private List<CommentBean.CommentData> commentBeans;
    private boolean isLiveNews;
    private NeedLoginListener listener;
    public CommentAdapter(Context context, List<CommentBean.CommentData> commentBeans,boolean isLiveNews) {
        this.context = context;
        this.commentBeans = commentBeans;
        this.isLiveNews = isLiveNews;
    }

    public void setListener(NeedLoginListener listener) {
        this.listener = listener;
    }

    public void upData(Context context){
        this.context = context;
        notifyDataSetChanged();
    }

    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.adapter_comment,null);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, final int position) {

        if (isLiveNews == true){
            GlideUtil.INSTANCE.loadHead(context,commentBeans.get(position).head_pic,holder.img_user);
            holder.tv_username.setText(commentBeans.get(position).member_name);
        }else {
            GlideUtil.INSTANCE.loadHead(context,commentBeans.get(position).avatar,holder.img_user);
            holder.tv_username.setText(commentBeans.get(position).username);
        }
        holder.tv_num.setText(commentBeans.get(position).laud_num + "");
        holder.ll_comment_lanud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentBeans.get(position).is_laud==1){//取消点赞
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",commentBeans.get(position).id)
                            .params("token",TMSharedPUtil.getTMToken(context))
                            .headers("token",TMSharedPUtil.getTMToken(context))
                            .params("type",3)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        commentBeans.get(position).is_laud=0;
                                        commentBeans.get(position).setLaud_num(commentBeans.get(position).laud_num - 1);
                                        notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });

                }else {//添加点赞
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",commentBeans.get(position).id)
                            .params("token",TMSharedPUtil.getTMToken(context))
                            .headers("token",TMSharedPUtil.getTMToken(context))
                            .params("type",3)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        commentBeans.get(position).is_laud=1;
                                        commentBeans.get(position).setLaud_num(commentBeans.get(position).laud_num + 1);
                                        notifyDataSetChanged();
                                    }else if (code==500){
                                        if (listener!=null){
                                            listener.needLogin();
                                        }
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
        int is_laud = commentBeans.get(position).is_laud;
        if (is_laud == 1){
            holder.img_zan.setImageResource(R.drawable.ic_home_praise_select);
        }else {
            holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
        }
        holder.tv_comment_content.setText(commentBeans.get(position).content);
        if (commentBeans.get(position).create_time!=null&&!commentBeans.get(position).create_time.isEmpty()){
            holder.tv_time.setText(MyTimeUtils.convertTimeToCustom(Long.parseLong(commentBeans.get(position).create_time)));
        }
    }

    @Override
    public int getItemCount() {
        return commentBeans.size() == 0 ? 0 : commentBeans.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder{
        ImageView img_user,img_zan;
        TextView tv_username,tv_num,tv_comment_content,tv_time;
        LinearLayout ll_comment_lanud;

        public CommentViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);
            img_zan = itemView.findViewById(R.id.img_zan);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_comment_content = itemView.findViewById(R.id.tv_comment_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            ll_comment_lanud = itemView.findViewById(R.id.ll_comment_lanud);
        }
    }

    public interface NeedLoginListener{
        public void needLogin();
    }
}
