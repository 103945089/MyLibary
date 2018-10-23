package shopping.hlhj.com.mylibrary.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.GlideUtil;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.ArticleDetailActivity;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.RecommendBean;

/**
 * Created by Never Fear   on 2018\10\23 0023.
 * Never More....
 */

public class MoreVideoAdp extends BaseQuickAdapter<MoreBean.MoreDatas,BaseViewHolder> {

    public MoreVideoAdp(@Nullable List<MoreBean.MoreDatas> data) {
        super(R.layout.hot_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MoreBean.MoreDatas item) {
        GlideUtil.INSTANCE.loadIMg(mContext,item.cover, (ImageView) helper.getView(R.id.img_hot));
        helper.setText(R.id.tvTittleHotItem,item.title);
        helper.setText(R.id.tv_type,item.getName());

        String s = JavaUtils.StampstoTime(String.valueOf(item.create_time), "yyyy-MM-dd HH:mm:ss"); //UNIX时间 需要转换
        try {
            String format = JavaUtils.format(s);
            helper.setText(R.id.tv_time,format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (item.video_url==null||item.video_url.isEmpty()){
            helper.setVisible(R.id.btPlay,false);
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,ArticleDetailActivity.class);
                    intent.putExtra("id",item.id);
                    mContext.startActivity(intent);
                }
            });
        }else {
            helper.setVisible(R.id.btPlay,true);

            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,HotVideoDetailActivity.class);
                    intent.putExtra("id",item.id);
                    mContext.startActivity(intent);
                }
            });
        }

    }
}
