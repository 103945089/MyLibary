package shopping.hlhj.com.mylibrary.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.CatListAty;
import shopping.hlhj.com.mylibrary.bean.CatBean;
import shopping.hlhj.com.mylibrary.bean.CatListBean;

/**
 * Created by Never Fear   on 2018\10\26 0026.
 * Never More....
 */

public class CatLeftAdp extends BaseQuickAdapter<CatBean.DataBean,BaseViewHolder> {

    public CatLeftAdp(@Nullable List<CatBean.DataBean> data) {
        super(R.layout.cat_left_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CatBean.DataBean item) {
        helper.setText(R.id.itemTittle,"◆"+item.getNav_name());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CatListAty.class);
                intent.putExtra("id",item.getId());
                intent.putExtra("tv",item.getNav_name());
                mContext.startActivity(intent);
            }
        });
    }
}
