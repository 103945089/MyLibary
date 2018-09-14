package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.db.DBHelper;

public class SearchResultAdapter extends BaseAdapter {

    private Context context;
    private List<Search.SearchData.SearchBean> searchBeanList;

    public SearchResultAdapter(Context context,List<Search.SearchData.SearchBean> searchBeanList) {
        this.context = context;
        this.searchBeanList = searchBeanList;
    }


    public void setData(List<Search.SearchData.SearchBean> searchBeanList) {
        this.searchBeanList.clear();
        if (searchBeanList != null && searchBeanList.size() > 0){
            this.searchBeanList.addAll(searchBeanList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return searchBeanList.size() == 0 ? null : searchBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return searchBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchViewHolder holder = null;
        if (convertView == null) {
            holder = new SearchViewHolder();
            convertView = View.inflate(context, R.layout.adapter_searchresult, null);
            holder.searchImg = convertView.findViewById(R.id.img_search_result);
            holder.searchtitle = convertView.findViewById(R.id.tv_search_title);
            holder.searchType = convertView.findViewById(R.id.tv_search_type);
            holder.searchTime = convertView.findViewById(R.id.tv_search_time);
            convertView.setTag(holder);
        } else {
            holder = (SearchViewHolder) convertView.getTag();
        }
        Glide.with(context).load(searchBeanList.get(position).cover).into(holder.searchImg);
        holder.searchtitle.setText(searchBeanList.get(position).title);
        holder.searchType.setText(searchBeanList.get(position).relese);
        String s = JavaUtils.StampstoTime(String.valueOf(searchBeanList.get(position).create_time), "yyyy-MM-dd HH:mm:ss");
        try {
            String format = JavaUtils.format(s);
            holder.searchTime.setText(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return convertView;
    }


    class SearchViewHolder {
        TextView searchtitle, searchType, searchTime;
        ImageView searchImg;
    }
}
