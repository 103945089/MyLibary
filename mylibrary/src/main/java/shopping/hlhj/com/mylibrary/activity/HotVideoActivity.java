package shopping.hlhj.com.mylibrary.activity;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.ArrayList;
import java.util.List;
import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.MoreAdapter;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class HotVideoActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView{

    private ListView listView;
    private View btExit,loBack;
    private TextView tvTitle;
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
        btExit=findViewById(R.id.btExit);
        tvTitle=findViewById(R.id.tvTitle);
        loBack=findViewById(R.id.loBack);
        tvTitle.setText("视频列表");

        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
        }

    }

    @Override
    protected void initData() {
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadMoreVideoData(this,page);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @Override
    protected void setOnClick() {
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadMoreVideoData(HotVideoActivity.this,page);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page ++;
                getPresenter().loadMoreVideoData(HotVideoActivity.this,page);
                springView.onFinishFreshAndLoad();
            }
        });
    }


    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {

    }

    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> moreDatas) {
        moreAdapter = new MoreAdapter(this,moreDatas,false);
        listView.setAdapter(moreAdapter);
    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {

    }

    @Override
    public void loadSendCommentSuccess(String msg) {

    }
}
