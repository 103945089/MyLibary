package shopping.hlhj.com.mylibrary.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.GlideUtil;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
import shopping.hlhj.com.mylibrary.bean.HotAdSpecial;

/**
 * Created by Never Fear   on 2018\10\24 0024.
 * Never More....
 */

public class HotVideoRcyAdp extends BaseQuickAdapter<HotAdSpecial.HotAdSpecialData.HotBean,BaseViewHolder> {

    public HotVideoRcyAdp(@Nullable List<HotAdSpecial.HotAdSpecialData.HotBean> data) {
        super(R.layout.hot_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HotAdSpecial.HotAdSpecialData.HotBean item) {
        GlideUtil.INSTANCE.loadIMg(mContext,item.cover, (ImageView) helper.getView(R.id.img_hot));
        helper.setText(R.id.tvTittleHotItem,item.title);
        helper.setText(R.id.tv_type,item.release);

        try {
            String s = JavaUtils.StampstoTime(String.valueOf(item.getCreate_time()), "yyyy-MM-dd HH:mm:ss");
            String format = JavaUtils.format(s);
            helper.setText(R.id.tv_time,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (helper.getAdapterPosition()%2){
            case 0:
                RelativeLayout view = (RelativeLayout) helper.getView(R.id.linear_item);
                GridLayoutManager.LayoutParams lp1 = (GridLayoutManager.LayoutParams) view.getLayoutParams();
                lp1.setMarginStart(0);
                view.setLayoutParams(lp1);
                break;
            case 1:
                RelativeLayout view2 = (RelativeLayout) helper.getView(R.id.linear_item);
                GridLayoutManager.LayoutParams lp2 = (GridLayoutManager.LayoutParams) view2.getLayoutParams();
                lp2.setMarginStart(10);
                view2.setLayoutParams(lp2);
                break;
            default:
        }
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HotVideoDetailActivity.class);
                intent.putExtra("id",item.id);
                mContext.startActivity(intent);
            }
        });
    }
}
