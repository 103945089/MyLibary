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
import shopping.hlhj.com.mylibrary.bean.HotAdSpecial;

public class HotVideoAdapter extends BaseAdapter {
    private Context context;
    private List<HotAdSpecial.HotAdSpecialData.HotBean> hotBeans;

    public HotVideoAdapter(Context context, List<HotAdSpecial.HotAdSpecialData.HotBean> hotBeans) {
        this.context = context;
        this.hotBeans = hotBeans;
    }

    @Override
    public int getCount() {
        if (null != hotBeans && hotBeans.size() > 4) {
            return 4;
        } else {
            return hotBeans.size() == 0 ? null : hotBeans.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return hotBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHodler holder = null;
        if (convertView == null) {
            holder = new ViewHodler();
            convertView = LayoutInflater.from(context).inflate(R.layout.hot_item, null);
            holder.imageView = convertView.findViewById(R.id.img_hot);
            holder.tilte = convertView.findViewById(R.id.tv_titel);
            holder.time = convertView.findViewById(R.id.tv_time);
            holder.newstype = convertView.findViewById(R.id.tv_type);
            holder.linearLayout = convertView.findViewById(R.id.linear_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }
        Glide.with(context).load(hotBeans.get(position).cover).into(holder.imageView);
        holder.tilte.setText(hotBeans.get(position).title);
        holder.newstype.setText(hotBeans.get(position).type + "");
        holder.time.setText(hotBeans.get(position).create_time + "");        //UNIX时间 需要转换

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HotVideoActivity.class);
                intent.putExtra("video_url",hotBeans.get(position).video_url);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHodler {
        ImageView imageView;
        LinearLayout linearLayout;
        TextView tilte, newstype, time;
    }
}
