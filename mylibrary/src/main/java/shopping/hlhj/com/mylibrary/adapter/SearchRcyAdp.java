package shopping.hlhj.com.mylibrary.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.db.DBHelper;

/**
 * Created by Never Fear   on 2018\10\25 0025.
 * Never More....
 */

public class SearchRcyAdp extends BaseQuickAdapter<String,BaseViewHolder> {
    private DBHelper dbHelper;
    private int type;
    //1-his 2-hot
    public SearchRcyAdp(@Nullable List<String> data,int type) {
        super(R.layout.adapter_serach,data);
        this.type=type;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        dbHelper=new DBHelper(mContext);
        helper.setText(R.id.tv_search,item);
        if (type==1){
            helper.setVisible(R.id.img_del,true);
            helper.setOnClickListener(R.id.img_del, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = item.toString();
                    Log.d("--------------------",s.toString());
                    dbHelper.delByName("name="+"\'"+item+"\'");
                    getData().remove(helper.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }else {
            helper.setVisible(R.id.img_del,false);

        }
        /*helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = item.toString();
                Log.d("--------------------",s.toString());
                dbHelper.delByName(item);
                notifyDataSetChanged();
            }
        });*/
    }
}
