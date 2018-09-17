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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

    private Context context;
    private List<CommentBean.CommentData> commentBeans;
    private boolean flag = true;
    public CommentAdapter(Context context, List<CommentBean.CommentData> commentBeans) {
        this.context = context;
        this.commentBeans = commentBeans;
        notifyDataSetChanged();
    }

    @NonNull
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
                if (flag){
                    holder.img_zan.setImageResource(R.drawable.ic_home_praise_select);
                    flag = false;
                    holder.tv_num.setText(commentBeans.get(position).laud_num + 1 + "");
                }else {
                    holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
                    holder.tv_num.setText(commentBeans.get(position).laud_num + "");
                    flag = true;
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

//public class CommentAdapter extends BaseAdapter {
//
//    private Context context;
//    private List<CommentBean.CommentData> commentData;
//    private boolean flag = true;
//
//    public CommentAdapter(Context context, List<CommentBean.CommentData> commentData) {
//        Log.d("--------------",commentData.size() + "222");
//        this.context = context;
//        this.commentData = commentData;
//    }
//
//    @Override
//    public int getCount() {
//        return commentData.size() == 0 ? null : commentData.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return commentData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        CommentViewHolder holder = null;
//        if (convertView == null) {
//            holder = new CommentViewHolder();
//            convertView = View.inflate(context, R.layout.adapter_comment, null);
//            holder.img_user = convertView.findViewById(R.id.img_user);
//            holder.img_zan = convertView.findViewById(R.id.img_zan);
//            holder.tv_username = convertView.findViewById(R.id.tv_username);
//            holder.tv_num = convertView.findViewById(R.id.tv_num);
//            holder.tv_comment_content = convertView.findViewById(R.id.tv_comment_content);
//            holder.tv_time = convertView.findViewById(R.id.tv_time);
//            holder.ll_comment_lanud = convertView.findViewById(R.id.ll_comment_lanud);
//            convertView.setTag(holder);
//        } else {
//            holder = (CommentViewHolder) convertView.getTag();
//        }
//        RequestOptions mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
//                .skipMemoryCache(true);
//
////        Glide.with(context).load(commentData.get(position).head_pic).apply(mRequestOptions).into(holder.img_user);
////        holder.ll_comment_lanud.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (flag) {
////                    flag = false;
////                } else {
////                    flag = true;
////                }
////            }
////        });
////        if (flag) {
////            holder.img_zan.setImageResource(R.drawable.ic_home_praise_select);
////            flag = false;
////        } else {
////            holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
////            flag = true;
////        }
//        holder.tv_username.setText(commentData.get(position).member_name);
//        holder.tv_comment_content.setText(commentData.get(position).content);
//        holder.tv_num.setText(commentData.get(position).laud_num + "");
//        String s = JavaUtils.StampstoTime(String.valueOf(commentData.get(position).create_at), "yyyy-MM-dd HH:mm:ss");
//        try {
//            String format = JavaUtils.format(s);
//            holder.tv_time.setText(format);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    class CommentViewHolder {
//        ImageView img_user, img_zan;
//        TextView tv_username, tv_num, tv_comment_content, tv_time;
//        LinearLayout ll_comment_lanud;
//    }
//}
