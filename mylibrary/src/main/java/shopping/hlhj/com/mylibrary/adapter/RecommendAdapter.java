package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import shopping.hlhj.com.mylibrary.activity.HotVideoActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.bean.RecommendBean;

public class RecommendAdapter extends BaseAdapter{

    private Context context;
    private List<RecommendBean.RecommendData.RecommenDatas> recommenDatas;

    public RecommendAdapter(Context context, List<RecommendBean.RecommendData.RecommenDatas> recommenDatas) {
        this.context = context;
        this.recommenDatas = recommenDatas;
    }

    public void updataAdapter(Context context,List<RecommendBean.RecommendData.RecommenDatas> recommenDatas){
        this.context = context;
        this.recommenDatas = recommenDatas;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return recommenDatas.size() == 0 ? null : recommenDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return recommenDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.hot_item, null);
            holder.imageView = convertView.findViewById(R.id.img_hot);
            holder.tilte = convertView.findViewById(R.id.tv_titel);
            holder.time = convertView.findViewById(R.id.tv_time);
            holder.newstype = convertView.findViewById(R.id.tv_type);
            holder.linearLayout = convertView.findViewById(R.id.linear_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(recommenDatas.get(position).cover).into(holder.imageView);
        holder.tilte.setText(recommenDatas.get(position).title);
        holder.newstype.setText(recommenDatas.get(position).type + "");
        holder.time.setText(recommenDatas.get(position).create_time + "");        //UNIX时间 需要转换

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HotVideoActivity.class);
                intent.putExtra("video_url",recommenDatas.get(position).video_url);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        LinearLayout linearLayout;
        TextView tilte, newstype, time;
    }
}
