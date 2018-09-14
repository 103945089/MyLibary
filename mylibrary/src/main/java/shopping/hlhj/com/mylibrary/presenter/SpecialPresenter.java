package shopping.hlhj.com.mylibrary.presenter;

import android.content.Context;

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
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class SpecialPresenter extends BasePresenter<SpecialPresenter.SpecialMoerView> {

    public SpecialPresenter(SpecialMoerView baseview) {
        super(baseview);
    }

    public void loadSpecialData(Context context,int page){
        OkGo.<String>get(Constant.ARTICLE_MORE)
                .tag(context)
                .params("page",page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200){
                            JSONArray data = jsonObject.getJSONArray("data");
                            List<MoreBean.MoreDatas> moreDatas = new Gson().fromJson(data.toString()
                            ,new TypeToken<List<MoreBean.MoreDatas>>(){}.getType());
                            if (null != moreDatas && moreDatas.size() > 0){
                                getView().loadSpecialMoerSuccess(moreDatas);
                            }else {
                                getView().loadFailed(jsonObject.getString("message"));
                            }
                        }
                    }
                });
    }

    public interface SpecialMoerView extends BaseView{
        void loadSpecialMoerSuccess(List<MoreBean.MoreDatas> moreDatas);

        void loadFailed(String msg);
    }
}
