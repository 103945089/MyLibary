package shopping.hlhj.com.mylibrary.activity;

import android.util.Log;
import android.widget.ListView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import java.util.List;
import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.LiveMoreAdapter;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.LiveNewsPresenter;

public class LiveNewsMoreActivity extends BaseActivity<LiveNewsPresenter> implements LiveNewsPresenter.LiveNewsView {
    private ListView listView;
    private SpringView springView;
    private int page = 1;
    private LiveMoreAdapter liveMoreAdapter;
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
        setPresenter(new LiveNewsPresenter(this));
        getPresenter().loadLiveMoreData(this,page);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @Override
    protected void setOnClick() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadLiveMoreData(LiveNewsMoreActivity.this,page);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page ++;
                getPresenter().loadLiveMoreData(LiveNewsMoreActivity.this,page);
                springView.onFinishFreshAndLoad();
            }
        });
    }

    @Override
    public void loadSuccess(DetailBean.DetailDatas detailDatas) {

    }

    @Override
    public void loadLiveMoreSuccess(List<MoreBean.MoreDatas> moreDatas) {
        Log.d("2222222222222222",moreDatas.size() + "");
        liveMoreAdapter = new LiveMoreAdapter(this,moreDatas);
        listView.setAdapter(liveMoreAdapter);
    }

    @Override
    public void loadCommentSuccess(List<DetailBean.DetailDatas.CommentBean> commentBeans) {

    }

    @Override
    public void loadFailed(String msg) {

    }
}
