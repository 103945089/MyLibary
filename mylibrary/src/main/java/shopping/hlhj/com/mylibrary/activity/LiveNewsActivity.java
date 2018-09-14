package shopping.hlhj.com.mylibrary.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.DanmakuVDPlayer;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.TopBanner;
import shopping.hlhj.com.mylibrary.presenter.LiveNewsPresenter;

public class LiveNewsActivity extends BaseActivity<LiveNewsPresenter> implements LiveNewsPresenter.LiveNewsView {

    private DanmakuVDPlayer vdPlayer;
    private TextView tv_live_titel,tv_live_content,tv_live_contentmore,tv_look;
    private LinearLayout ll_zan,ll_look,ll_collect,ll_shared;
    private ImageView img_zan,img_look,img_collect;
    private EditText etContent;
    private RecyclerView recyclerview;
    private int liveId;
    private SpringView springView;
    private boolean dianzanflag = true;
    private boolean collectflag = true;
    private CommentAdapter commentAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.aty_livedetail_new;
    }

    @Override
    protected void beforeinit() {
        liveId = getIntent().getExtras().getInt("id");
    }

    @Override
    protected void initView() {
        vdPlayer = findViewById(R.id.videoPlayer);
        tv_live_titel = findViewById(R.id.tv_live_titel);
        tv_live_content = findViewById(R.id.tv_live_content);
        tv_live_contentmore = findViewById(R.id.tv_live_contentmore);
        tv_look = findViewById(R.id.tv_look);
        ll_zan = findViewById(R.id.ll_zan);
        ll_look = findViewById(R.id.ll_look);
        ll_collect = findViewById(R.id.ll_collect);
        ll_shared = findViewById(R.id.ll_shared);
        img_zan = findViewById(R.id.img_zan);
        img_look = findViewById(R.id.img_look);
        img_collect = findViewById(R.id.img_collect);
        etContent = findViewById(R.id.etContent);
        recyclerview = findViewById(R.id.recyclerview);
//        springView = findViewById(R.id.springview);
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);

        setPresenter(new LiveNewsPresenter(LiveNewsActivity.this));
        getPresenter().loadLiveNesData(this, liveId,0);
    }

    @Override
    protected void setOnClick() {
        ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dianzanflag){
                    img_zan.setImageResource(R.drawable.ic_home_praise_select);
                    dianzanflag = false;
                } else {
                    img_zan.setImageResource(R.drawable.ic_home_praise_normal);
                    dianzanflag = true;
                }

            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectflag){
                    img_collect.setImageResource(R.drawable.ic_collection);
                    collectflag = false;
                } else {
                    img_collect.setImageResource(R.drawable.ic_sc_normal);
                    collectflag = true;
                }
            }
        });
        ll_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void loadSuccess(DetailBean.DetailDatas detailDatas) {
        tv_look.setText(detailDatas.like + "");
        tv_live_titel.setText(detailDatas.title);
        tv_live_content.setText(detailDatas.content);
    }

    @Override
    public void loadLiveMoreSuccess(List<TopBanner.Datas.LiveBean> liveBeans) {

    }


    @Override
    public void loadCommentSuccess(List<DetailBean.DetailDatas.CommentBean> commentBeans) {
        commentAdapter = new CommentAdapter(this,commentBeans);
        recyclerview.setAdapter(commentAdapter);
    }

    @Override
    public void loadFailed(String msg) {

    }
}
