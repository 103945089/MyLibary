package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.DetailBean;

public class TextCommentAdapter extends RecyclerView.Adapter<TextCommentAdapter.TextCommentViewHolder> {

    private Context context;
    private List<DetailBean.DetailDatas.TextDetailComment> textDetailComments;

    public TextCommentAdapter(Context context, List<DetailBean.DetailDatas.TextDetailComment> textDetailComments) {
        this.context = context;
        this.textDetailComments = textDetailComments;
    }

    private boolean flag = true;

    @Override
    public TextCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.adapter_comment, null);
        TextCommentViewHolder holder = new TextCommentViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TextCommentViewHolder holder, final int position) {
        RequestOptions mRequestOptions = RequestOptions.circleCropTransform().diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

        Glide.with(context).load(textDetailComments.get(position).avatar).apply(mRequestOptions).into(holder.img_user);
        holder.tv_num.setText(textDetailComments.get(position).laud_num + "");
        holder.ll_comment_lanud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    holder.img_zan.setImageResource(R.drawable.ic_home_praise_select);
                    flag = false;
                    holder.tv_num.setText(textDetailComments.get(position).laud_num + 1 + "");
                } else {
                    holder.img_zan.setImageResource(R.drawable.ic_home_praise_normal);
                    holder.tv_num.setText(textDetailComments.get(position).laud_num + "");
                    flag = true;
                }
            }
        });
        holder.tv_username.setText(textDetailComments.get(position).username);
        holder.tv_comment_content.setText(textDetailComments.get(position).content);

        String s = JavaUtils.StampstoTime(String.valueOf(textDetailComments.get(position).create_time), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            holder.tv_time.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return textDetailComments.size() == 0 ? 0 : textDetailComments.size();
    }

    class TextCommentViewHolder extends RecyclerView.ViewHolder {
        ImageView img_user, img_zan;
        TextView tv_username, tv_num, tv_comment_content, tv_time;
        LinearLayout ll_comment_lanud;

        public TextCommentViewHolder(View itemView) {
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
