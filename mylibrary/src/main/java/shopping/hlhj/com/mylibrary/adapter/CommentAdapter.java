package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.DetailBean;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    private List<DetailBean.CommentBean> commentBeans;
    private boolean flag = true;
    public CommentAdapter(Context context, List<DetailBean.CommentBean> commentBeans) {
        Log.d("--------------",commentBeans.size() + "");
        this.context = context;
        this.commentBeans = commentBeans;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.adapter_comment,null);
        CommentViewHolder holder = new CommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CommentViewHolder holder, int position) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

//        Glide.with(context).load(commentBeans.get(position).avatar).apply(mRequestOptions).into(holder.img_user);
        holder.img_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    holder.img_zan.setImageResource(R.drawable.ic_home_praise_select);
                    flag = false;
                }else {
                    holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
                    flag = true;
                }
            }
        });
        holder.tv_username.setText(commentBeans.get(position).member_name);
        holder.tv_comment_content.setText(commentBeans.get(position).content);
        holder.tv_num.setText(commentBeans.get(position).laud_num + "");
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

        public CommentViewHolder(View itemView) {
            super(itemView);
            img_user = itemView.findViewById(R.id.img_user);
            img_zan = itemView.findViewById(R.id.img_zan);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_comment_content = itemView.findViewById(R.id.tv_comment_content);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
