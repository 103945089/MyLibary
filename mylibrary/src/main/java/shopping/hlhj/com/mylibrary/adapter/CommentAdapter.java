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
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    private List<CommentBean.CommentData> commentBeans;
    private boolean flag = true;
    public CommentAdapter(Context context, List<CommentBean.CommentData> commentBeans) {
        this.context = context;
        this.commentBeans = commentBeans;
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
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

        Glide.with(context).load(commentBeans.get(position).head_pic).apply(mRequestOptions).into(holder.img_user);
        holder.tv_num.setText(commentBeans.get(position).laud_num + "");
        holder.ll_comment_lanud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){//取消点赞
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",commentBeans.get(position).id)
                            .params("token",TMSharedPUtil.getTMToken(context))
                            .headers("token",TMSharedPUtil.getTMToken(context))
                            .params("status",3)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        holder.img_zan.setImageResource(R.drawable.ic_home_praise_select);
                                        flag = false;
                                        holder.tv_num.setText(commentBeans.get(position).laud_num + 1 + "");
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
                            .params("status",3)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
                                        holder.tv_num.setText(commentBeans.get(position).laud_num + "");
                                        flag = true;
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

            flag = true;
        }else {
            holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
            flag = false;
        }
        holder.tv_username.setText(commentBeans.get(position).member_name);
        holder.tv_comment_content.setText(commentBeans.get(position).content);

        String s = JavaUtils.StampstoTime(String.valueOf(commentBeans.get(position).create_at), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            holder.tv_time.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return commentBeans.size() == 0 ? null : commentBeans.size();
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
}
