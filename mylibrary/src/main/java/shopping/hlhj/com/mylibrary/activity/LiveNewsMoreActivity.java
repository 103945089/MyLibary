package shopping.hlhj.com.mylibrary.activity;

import android.graphics.Color;
import android.util.Log;
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
import shopping.hlhj.com.mylibrary.adapter.LiveMoreAdapter;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DanMuBean;
import shopping.hlhj.com.mylibrary.bean.LiveDetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.presenter.LiveNewsPresenter;

public class LiveNewsMoreActivity extends BaseActivity<LiveNewsPresenter> implements LiveNewsPresenter.LiveNewsView, LiveMoreAdapter.OnLikeClick {
    private ListView listView;
    private TextView tvTitle;
    private View btExit,loBack;
    private SpringView springView;
    private int page = 1;
    private LiveMoreAdapter liveMoreAdapter;
    private int p=0;
    private List<MoreBean.MoreDatas> moreDatas = new ArrayList<>();
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
        tvTitle=findViewById(R.id.tvTitle);
        btExit=findViewById(R.id.btExit);
        loBack=findViewById(R.id.loBack);
        liveMoreAdapter = new LiveMoreAdapter(this,moreDatas,LiveNewsMoreActivity.this);
        listView.setAdapter(liveMoreAdapter);

        tvTitle.setText("全部直播");
        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
        }
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
        if (page==1){
            this.moreDatas.clear();
        }
        this.moreDatas.addAll(moreDatas);
        liveMoreAdapter.notifyDataSetChanged();
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

    @Override
    public void likeSuccess() {
        MoreBean.MoreDatas moreDatas = this.moreDatas.get(p);
        if (moreDatas.is_laud==1){
            moreDatas.setIs_laud(0);
            moreDatas.laud_num--;
        }else {
            moreDatas.setIs_laud(1);
            moreDatas.laud_num++;

        }
        liveMoreAdapter.notifyDataSetChanged();
    }

    @Override
    public void likeErro() {
        new GoLoginDialog(LiveNewsMoreActivity.this).show();
    }

    @Override
    public void clickLike(int id,int poi) {
        p=poi;
        getPresenter().likeIt(LiveNewsMoreActivity.this,id,2);
    }

    @Override
    public void clickColl(int id, int poi) {

    }
}
