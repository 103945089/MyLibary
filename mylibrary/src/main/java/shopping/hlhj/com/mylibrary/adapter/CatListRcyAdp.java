package shopping.hlhj.com.mylibrary.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.GlideUtil;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
import shopping.hlhj.com.mylibrary.bean.CatListBean;

/**
 * Created by Never Fear   on 2018\10\24 0024.
 * Never More....
 */

public class CatListRcyAdp extends BaseQuickAdapter<CatListBean.DataBean,BaseViewHolder> {
    public CatListRcyAdp(@Nullable List<CatListBean.DataBean> data) {
        super(R.layout.adapter_more,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CatListBean.DataBean item) {
        String s = JavaUtils.StampstoTime(item.getCreate_time(), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            helper.setText(R.id.tv_hotmore_time,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GlideUtil.INSTANCE.loadIMg(mContext,item.getCover(), (ImageView) helper.getView(R.id.img_hotmore));
        helper.setText(R.id.tv_hotmore_title,item.getTitle());
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,HotVideoDetailActivity.class);
                intent.putExtra("id",item.getId());
                mContext.startActivity(intent);
            }
        });
    }
}
