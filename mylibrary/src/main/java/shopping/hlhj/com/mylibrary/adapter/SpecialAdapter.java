package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.bean.HotAdSpecial;

public class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder>{
    private Context context;
    private List<HotAdSpecial.HotAdSpecialData.Special> specialList;

    public SpecialAdapter(Context context, List<HotAdSpecial.HotAdSpecialData.Special> specialList) {
        this.context = context;
        this.specialList = specialList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.special_item,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(specialList.get(position).channel_thumb).into(holder.img_special);
    }

    @Override
    public int getItemCount() {
        return specialList.size() == 0 ? null : specialList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_special;
        LinearLayout ll_special;
        public ViewHolder(View itemView) {
            super(itemView);
            img_special = itemView.findViewById(R.id.img_special);
            ll_special = itemView.findViewById(R.id.ll_special);
            img_special.setOnClickListener(onClickListener);
        }
    }

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int positon = (int) v.getTag();
            if (onItemOnClick != null){
                onItemOnClick.onItemClick(positon,v);
            }
        }
    };

    public OnItemOnClick onItemOnClick;

    public void setOnItemOnClick(OnItemOnClick onItemOnClick) {
        this.onItemOnClick = onItemOnClick;
    }

    public interface OnItemOnClick{
        void onItemClick(int position,View view);
    }
}
