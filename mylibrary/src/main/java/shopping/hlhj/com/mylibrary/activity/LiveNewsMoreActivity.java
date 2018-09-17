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
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DanMuBean;
import shopping.hlhj.com.mylibrary.bean.LiveDetailBean;
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
    public void loadLiveMoreSuccess(List<MoreBean.MoreDatas> moreDatas) {
        liveMoreAdapter = new LiveMoreAdapter(this,moreDatas);
        listView.setAdapter(liveMoreAdapter);
    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {

    }


    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void loadDanmu(DanMuBean danMuBean) {

    }

    @Override
    public void loadLiveDetail(LiveDetailBean.LiveDetail liveDetailBean) {

    }

    @Override
    public void loadSendCommentSuccess(String msg) {

    }


}
