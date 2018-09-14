package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
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
import shopping.hlhj.com.mylibrary.bean.TopBanner;
import shopping.hlhj.com.mylibrary.data.Constant;

public class LiveMoreAdapter extends BaseAdapter{

    private Context context;
    private List<TopBanner.Datas.LiveBean> liveBeans;

    public LiveMoreAdapter(Context context, List<TopBanner.Datas.LiveBean> liveBeans) {
        this.context = context;
        this.liveBeans = liveBeans;
    }

    @Override
    public int getCount() {
        return liveBeans.size() == 0 ? null : liveBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return liveBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LiveMoreViewHolder holder = null;
        if (convertView == null){
            holder = new LiveMoreViewHolder();
            convertView = View.inflate(context, R.layout.adapter_more,null);
            holder.imgHotMore = convertView.findViewById(R.id.img_hotmore);
            holder.tvHotMoreTitle = convertView.findViewById(R.id.tv_hotmore_title);
            holder.tvHotMoreTime = convertView.findViewById(R.id.tv_hotmore_time);
            holder.linearLayout = convertView.findViewById(R.id.ll_hotmore); convertView.setTag(holder);
        }else {
            holder = (LiveMoreViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Constant.IMG_URL + liveBeans.get(position).getLive_thumb()).into(holder.imgHotMore);
        holder.tvHotMoreTitle.setText(liveBeans.get(position).live_title);
        String s = JavaUtils.StampstoTime(String.valueOf(liveBeans.get(position).create_at), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            holder.tvHotMoreTime.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    class LiveMoreViewHolder {
        ImageView imgHotMore;
        TextView tvHotMoreTitle,tvHotMoreTime;
        LinearLayout linearLayout;
    }
}
