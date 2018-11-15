package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.GlideUtil;
import shopping.hlhj.com.mylibrary.activity.LiveNewsActivity;
import shopping.hlhj.com.mylibrary.bean.TopBanner;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.fragment.FragmentIndexChoice;

public class LiveNewsAdapter extends BaseAdapter {

    private Context context;
    private List<TopBanner.Datas.LiveBean> liveBeanList;

    public LiveNewsAdapter(Context context, List<TopBanner.Datas.LiveBean> liveBeanList) {
        this.context = context;
        this.liveBeanList = liveBeanList;
    }

    @Override
    public int getCount() {
        if (liveBeanList.size() >= 3) {
            return 3;
        } else {
            return liveBeanList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return liveBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LiveNewsViewHolder holder = null;
        if (convertView == null) {
            holder = new LiveNewsViewHolder();
            convertView = View.inflate(context, R.layout.adapter_livenews_img, null);
            holder.imgView = convertView.findViewById(R.id.img_item);
            holder.loSele=convertView.findViewById(R.id.loSele);
            convertView.setTag(holder);
        } else {
            holder = (LiveNewsViewHolder) convertView.getTag();
        }
        GlideUtil.INSTANCE.loadIMg(context,liveBeanList.get(position).getLive_thumb(),holder.imgView);
        if (FragmentIndexChoice.seleHash.get(position)){
            holder.loSele.setBackground(context.getResources().getDrawable(R.drawable.select_red));
        }else {
            holder.loSele.setBackground(null);
        }
        return convertView;
    }

    class LiveNewsViewHolder {
        ImageView imgView;
        RelativeLayout loSele;
    }
}
