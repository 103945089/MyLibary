package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
import shopping.hlhj.com.mylibrary.bean.MoreBean;

public class MoreAdapter extends BaseAdapter{

    private Context context;
    private List<MoreBean.MoreDatas> hotMoreDatas;
    private boolean isSpecial;

    public MoreAdapter(Context context, List<MoreBean.MoreDatas> hotMoreDatas,boolean isSpecial) {
        this.context = context;
        this.hotMoreDatas = hotMoreDatas;
        this.isSpecial = isSpecial;
    }

    @Override
    public int getCount() {
        return hotMoreDatas.size() == 0 ? null : hotMoreDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return hotMoreDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MoreViewHolder holder = null;
        if (convertView == null){
            holder = new MoreViewHolder();
            convertView = View.inflate(context, R.layout.adapter_more,null);
            holder.imgHotMore = convertView.findViewById(R.id.img_hotmore);
            holder.tvHotMoreTitle = convertView.findViewById(R.id.tv_hotmore_title);
            holder.tvHotMoreTime = convertView.findViewById(R.id.tv_hotmore_time);
            holder.linearLayout = convertView.findViewById(R.id.ll_hotmore);
            convertView.setTag(holder);
        }else {
            holder = (MoreViewHolder) convertView.getTag();
        }
        if (isSpecial == true){
            Glide.with(context).load(hotMoreDatas.get(position).channel_thumb).into(holder.imgHotMore);
            holder.tvHotMoreTitle.setText(hotMoreDatas.get(position).name);
        }else {
            Glide.with(context).load(hotMoreDatas.get(position).cover).into(holder.imgHotMore);
            holder.tvHotMoreTitle.setText(hotMoreDatas.get(position).title);
        }
        String s = JavaUtils.StampstoTime(String.valueOf(hotMoreDatas.get(position).create_time), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            holder.tvHotMoreTime.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HotVideoDetailActivity.class);
                intent.putExtra("id",hotMoreDatas.get(position).id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class MoreViewHolder {
        ImageView imgHotMore;
        TextView tvHotMoreTitle,tvHotMoreTime;
        LinearLayout linearLayout;
    }
}
