package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.ArticleDetailActivity;
import shopping.hlhj.com.mylibrary.activity.FhpVideoDetailAty;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
import shopping.hlhj.com.mylibrary.activity.TextDetailsActivity;
import shopping.hlhj.com.mylibrary.bean.ArticleBean;

public class ArticleAdapter extends BaseAdapter {

    private Context context;
    private List<ArticleBean.ArticleDatas.ArticleDetailBean> articleDetailBeans;

    public ArticleAdapter(Context context, List<ArticleBean.ArticleDatas.ArticleDetailBean> articleDetailBeans) {
        this.context = context;
        this.articleDetailBeans = articleDetailBeans;
    }

    @Override
    public int getCount() {
        return articleDetailBeans.size() == 0 ? null : articleDetailBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return articleDetailBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ArticleViewHolder holder = null;
        if (convertView == null) {
            holder = new ArticleViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.hot_item, null);
            holder.imageView = convertView.findViewById(R.id.img_hot);
            holder.tilte = convertView.findViewById(R.id.tvTittleHotItem);
            holder.time = convertView.findViewById(R.id.tv_time);
            holder.newstype = convertView.findViewById(R.id.tv_type);
            holder.btPlay=convertView.findViewById(R.id.btPlay);
            holder.relativeLayout = convertView.findViewById(R.id.linear_item);
            convertView.setTag(holder);
        } else {
            holder = (ArticleViewHolder) convertView.getTag();
        }
        Glide.with(context).load(articleDetailBeans.get(position).cover).into(holder.imageView);
        holder.tilte.setText(articleDetailBeans.get(position).title);
        holder.newstype.setText(articleDetailBeans.get(position).release);
        try {
            String s = JavaUtils.StampstoTime(String.valueOf(articleDetailBeans.get(position).getCreate_time()), "yyyy-MM-dd HH:mm:ss");
            String format = JavaUtils.format(s);
            holder.time.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (null==articleDetailBeans.get(position).getVideo_url()||articleDetailBeans.get(position).getVideo_url().isEmpty()){
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TextDetailsActivity.class);
                    intent.putExtra("id", articleDetailBeans.get(position).id);
                    context.startActivity(intent);
                }
            });
            holder.btPlay.setVisibility(View.GONE);
        }else {
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, FhpVideoDetailAty.class);
                    intent.putExtra("id", articleDetailBeans.get(position).id);
                    context.startActivity(intent);
                }
            });
            holder.btPlay.setVisibility(View.VISIBLE);

        }


        return convertView;
    }

    class ArticleViewHolder {
        ImageView imageView;
        RelativeLayout relativeLayout;
        TextView tilte, newstype, time;
        View btPlay;
    }
}
