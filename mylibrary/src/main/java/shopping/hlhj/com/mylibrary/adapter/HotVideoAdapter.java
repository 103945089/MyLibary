package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.HotVideoActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
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
            holder.tilte = convertView.findViewById(R.id.tvTittleHotItem);
            holder.time = convertView.findViewById(R.id.tv_time);
            holder.newstype = convertView.findViewById(R.id.tv_type);
            holder.relativeLayout = convertView.findViewById(R.id.linear_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHodler) convertView.getTag();
        }
        Glide.with(context).load(hotBeans.get(position).cover).into(holder.imageView);
        holder.tilte.setText(hotBeans.get(position).title);
        holder.newstype.setText(hotBeans.get(position).getRelease());
        try {
            String s = JavaUtils.StampstoTime(String.valueOf(hotBeans.get(position).getCreate_time()), "yyyy-MM-dd HH:mm:ss");
            String format = JavaUtils.format(s);
            holder.time.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (position%2){
            case 0:
                RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
                lp1.setMarginStart(0);
                holder.relativeLayout.setLayoutParams(lp1);
                break;
            case 1:
                RelativeLayout.LayoutParams lp2 = (RelativeLayout.LayoutParams) holder.relativeLayout.getLayoutParams();
                lp2.setMarginStart(10);
                holder.relativeLayout.setLayoutParams(lp2);
                break;
            default:
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HotVideoDetailActivity.class);
                intent.putExtra("id", hotBeans.get(position).id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHodler {
        ImageView imageView;
        RelativeLayout relativeLayout;
        TextView tilte, newstype, time;
    }
}
