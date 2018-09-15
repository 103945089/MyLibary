package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.LiveNewsActivity;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class LiveMoreAdapter extends BaseAdapter{

    private Context context;
    private List<MoreBean.MoreDatas> moreDatas;

    public LiveMoreAdapter(Context context, List<MoreBean.MoreDatas> moreDatas) {
        this.context = context;
        this.moreDatas = moreDatas;
    }

    @Override
    public int getCount() {
        return moreDatas.size() == 0 ? null : moreDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return moreDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LiveMoreViewHolder holder = null;
        if (convertView == null){
            holder = new LiveMoreViewHolder();
            convertView = View.inflate(context, R.layout.adapter_livemore,null);
            holder.imgLiveMore = convertView.findViewById(R.id.img_livemore);
            holder.imgMoreLanud = convertView.findViewById(R.id.img_more_lanud);
            holder.imgMoreCollected = convertView.findViewById(R.id.img_more_collected);
            holder.imgMoreShared = convertView.findViewById(R.id.img_more_shared);
            holder.tvLiveMoreTitle = convertView.findViewById(R.id.tv_livemore_title);
            holder.tvLooknum = convertView.findViewById(R.id.tv_looknum);
            holder.tvMoreLanud = convertView.findViewById(R.id.tv_more_lanud);
            holder.tvMoreComment = convertView.findViewById(R.id.tv_more_comment);
            holder.llLivemoreLanud = convertView.findViewById(R.id.ll_livemore_lanud);
            holder.llLivemoreComment = convertView.findViewById(R.id.ll_livemore_comment);
            holder.linearLayout = convertView.findViewById(R.id.ll_livemore);
            convertView.setTag(holder);
        }else {
            holder = (LiveMoreViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Constant.IMG_URL + moreDatas.get(position).live_thumb).into(holder.imgLiveMore);
        holder.tvLiveMoreTitle.setText(moreDatas.get(position).live_title);
        holder.tvLooknum.setText(moreDatas.get(position).read_num + "");
        holder.tvMoreLanud.setText(moreDatas.get(position).laud_num + "");
        holder.tvMoreComment.setText(moreDatas.get(position).comment_num + "");
        if (moreDatas.get(position).is_laud == 1){
            holder.imgMoreLanud.setImageResource(R.drawable.ic_home_praise_select);
        }
        if (moreDatas.get(position).is_collection == 1){
            holder.imgMoreCollected.setImageResource(R.drawable.ic_collection);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveNewsActivity.class);
                intent.putExtra("id",moreDatas.get(position).id);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class LiveMoreViewHolder {
        ImageView imgLiveMore,imgMoreLanud,imgMoreCollected,imgMoreShared;
        TextView tvLiveMoreTitle,tvLooknum,tvMoreLanud,tvMoreComment;
        LinearLayout linearLayout,llLivemoreLanud,llLivemoreComment;
    }
}
