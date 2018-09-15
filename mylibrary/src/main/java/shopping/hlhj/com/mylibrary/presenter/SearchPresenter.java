package shopping.hlhj.com.mylibrary.presenter;

import android.content.Context;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.data.Constant;

public class SearchPresenter extends BasePresenter<SearchPresenter.MyGridView> {

    public SearchPresenter(MyGridView baseview) {
        super(baseview);
    }

    public void loadSearchData(Context context,String key,int page){
        OkGo.<String>get(Constant.SEARCH_URL)
                .tag(context)
                .params("key",key)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200){
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("hot");
                            List<Search.SearchData.SearchBean> searchBeanList = new Gson().fromJson(jsonArray.toString(),
                                    new TypeToken<List<Search.SearchData.SearchBean>>(){}.getType());
                            if (null != searchBeanList && searchBeanList.size() > 0){
                               getView().loadSuccess(searchBeanList);
                            }else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                        }
                    }
                });
    }

    public void loadSearchHot(Context context){
        OkGo.<String>get(Constant.SEARCH_HOT)
                .tag(context)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        if (jsonObject.getInteger("code") == 200){
//                            JSONObject data = jsonObject.getJSONObject("data");
//                            String[] datas = new Gson().fromJson(data.toString(), new TypeToken<String[]>() {
//                            }.getType());
//                            if (data != null){
//                                getView().loadHotData(datas);
//                            }
                        }
                    }
                });
    }


    public interface MyGridView extends BaseView{
        void loadSuccess(List<Search.SearchData.SearchBean> searchBeanList);

        void loadFailed(String msg);

        void loadHotData(String[] data);
    }
}
