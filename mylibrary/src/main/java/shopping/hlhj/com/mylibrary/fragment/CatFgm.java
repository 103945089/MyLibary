package shopping.hlhj.com.mylibrary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.CatListRcyAdp;
import shopping.hlhj.com.mylibrary.bean.CatListBean;
import shopping.hlhj.com.mylibrary.data.Constant;

/**
 * Created by Never Fear   on 2018\10\24 0024.
 * Never More....
 */

public class CatFgm extends Fragment {
    public static CatFgm getInstance(int id,String str){
        CatFgm catFgm = new CatFgm();
        Bundle bundle = new Bundle();
        bundle.putInt("id",id);
        bundle.putString("title",str);
        catFgm.setArguments(bundle);
        catFgm.tittle=str;
        return  catFgm;
    }
    private RecyclerView listView;
    private SpringView spView;
    public String tittle="";
    private CatListRcyAdp catListRcyAdp;
    private int page=1;
    private List<CatListBean.DataBean> moreDatas=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fgm_cat, null);
        listView=inflate.findViewById(R.id.listView);
        spView=inflate.findViewById(R.id.spView);

        catListRcyAdp=
        catListRcyAdp=new CatListRcyAdp(moreDatas);
        listView.setAdapter(catListRcyAdp);
        listView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        dispatchData();

        spView.setFooter(new DefaultFooter(getContext()));
        spView.setHeader(new DefaultHeader(getContext()));
        spView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                dispatchData();
            }

            @Override
            public void onLoadmore() {
                page++;
                dispatchData();
            }
        });
        return inflate;

    }

    private void dispatchData() {

        OkGo.<String>post(Constant.Cat_DEtail)
                .params("id",getArguments().getInt("id"))
                .params("page_now",page)
                .headers("token", TMSharedPUtil.getTMToken(getContext()))
                .params("token", TMSharedPUtil.getTMToken(getContext()))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        spView.onFinishFreshAndLoad();
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        if (jsonObject.getInteger("code")==200){
                            if (page==1){
                                moreDatas.clear();
                            }
                            CatListBean catListBean = new Gson().fromJson(body, CatListBean.class);
                            moreDatas.addAll(catListBean.getData());
                            catListRcyAdp.notifyDataSetChanged();
                        }
                    }
                });
    }

}
