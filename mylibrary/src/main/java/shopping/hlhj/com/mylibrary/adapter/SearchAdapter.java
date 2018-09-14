package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.android.exoplayer2.C;

import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.bean.Search;

public class SearchAdapter extends BaseAdapter{
    private Context context;
//    private List<Search.SearchData.SearchBean> searchBeanList;
    private List<String> searchBeanList;

//    public SearchAdapter(Context context, List<Search.SearchData.SearchBean> searchBeanList) {
//        this.context = context;
//        this.searchBeanList = searchBeanList;
//    }


    public SearchAdapter(Context context, List<String> searchBeanList) {
        this.context = context;
        this.searchBeanList = searchBeanList;
    }

    @Override
    public int getCount() {
        return searchBeanList.size() == 0 ? null : searchBeanList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHodler holder = null;
        if (convertView == null){
            convertView = View.inflate(context, R.layout.adapter_serach,null);
            holder = new MyViewHodler();
            holder.textView = convertView.findViewById(R.id.tv_search);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHodler) convertView.getTag();
        }
//        holder.textView.setText(searchBeanList.get(position).title);
        holder.textView.setText(searchBeanList.get(position));
        return convertView;
    }

    class MyViewHodler{
        TextView textView;
    }
}
