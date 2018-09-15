package shopping.hlhj.com.mylibrary.activity;

import android.widget.ListView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.MoreAdapter;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.SpecialPresenter;

public class SpecialMoreActivity extends BaseActivity<SpecialPresenter> implements SpecialPresenter.SpecialMoerView {
    private ListView listView;
    private SpringView springView;
    private int page = 1;
    private MoreAdapter moreAdapter;
    @Override
    protected int getContentResId() {
        return R.layout.activity_hotvideo;
    }

    @Override
    protected void beforeinit() {

    }

    @Override
    protected void initView() {
        listView = findViewById(R.id.list_hotvideo);
        springView = findViewById(R.id.springview_hotvideo);
    }

    @Override
    protected void initData() {
        setPresenter(new SpecialPresenter(this));
        getPresenter().loadSpecialData(this,page);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @Override
    protected void setOnClick() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadSpecialData(SpecialMoreActivity.this,page);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page ++;
                getPresenter().loadSpecialData(SpecialMoreActivity.this,page);
                springView.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    public void loadSpecialMoerSuccess(List<MoreBean.MoreDatas> moreDatas) {
        moreAdapter = new MoreAdapter(this,moreDatas,true);
        listView.setAdapter(moreAdapter);
    }

    @Override
    public void loadFailed(String msg) {

    }
}