package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;

import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.db.DBHelper;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    //    private List<Search.SearchData.SearchBean> searchBeanList;
    private List<String> searchBeanList;
    private boolean isHistory;

    private DBHelper dbHelper;

    public SearchAdapter(Context context, List<String> searchBeanList, boolean isHistory) {
        this.context = context;
        this.searchBeanList = searchBeanList;
        this.isHistory = isHistory;
    }

    @Override
    public int getCount() {
        return searchBeanList.size() == 0 ? 0 : searchBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchBeanList.size() == 0 ? null : searchBeanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        MyViewHodler holder = null;
        dbHelper = new DBHelper(context);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adapter_serach, null);
            holder = new MyViewHodler();
            holder.textView = convertView.findViewById(R.id.tv_search);
            holder.imgDel = convertView.findViewById(R.id.img_del);
            holder.rlSearch = convertView.findViewById(R.id.rl_search);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHodler) convertView.getTag();
        }
//        holder.textView.setText(searchBeanList.get(position).title);
        holder.textView.setText(searchBeanList.get(position));

        if (isHistory == true) {
            holder.imgDel.setVisibility(View.GONE);
            holder.imgDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String s = searchBeanList.get(position);
                    Log.d("--------------------",s.toString());
                    dbHelper.delByName(searchBeanList.get(position));
                    searchBeanList.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            holder.imgDel.setVisibility(View.GONE);
        }
        return convertView;
    }

    class MyViewHodler {
        TextView textView;
        ImageView imgDel;
        RelativeLayout rlSearch;
    }
}
