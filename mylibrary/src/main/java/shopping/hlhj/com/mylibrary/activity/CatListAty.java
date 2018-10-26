package shopping.hlhj.com.mylibrary.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.CatListRcyAdp;
import shopping.hlhj.com.mylibrary.bean.CatListBean;
import shopping.hlhj.com.mylibrary.data.Constant;

/**
 * Created by Never Fear   on 2018\10\26 0026.
 * Never More....
 */

public class CatListAty extends AppCompatActivity {
    private RecyclerView listView;
    private SpringView spView;
    public String tittle="";
    private CatListRcyAdp catListRcyAdp;
    private int page=1;
    private TextView mTittle;
    private View btExit,loBack;
    private List<CatListBean.DataBean> moreDatas=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_cat);

        initView();




    }

    private void initView(){

        listView=findViewById(R.id.listView);
        spView=findViewById(R.id.spView);
        btExit=findViewById(R.id.btExit);
        mTittle=findViewById(R.id.tvTitle);
        loBack=findViewById(R.id.loBack);

        mTittle.setText(getIntent().getStringExtra("tv"));
        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
        }

        catListRcyAdp=new CatListRcyAdp(moreDatas);

        listView.setAdapter(catListRcyAdp);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));



        spView.setFooter(new DefaultFooter(this));
        spView.setHeader(new DefaultHeader(this,R.drawable.my_arrow,R.drawable.my_arrow));
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

    }
    private void dispatchData() {

        OkGo.<String>post(Constant.Cat_DEtail)
                .params("id",getIntent().getIntExtra("id",0))
                .params("page_now",page)
                .headers("token", TMSharedPUtil.getTMToken(this))
                .params("token", TMSharedPUtil.getTMToken(this))
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
