package shopping.hlhj.com.mylibrary.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.DanmakuVDPlayer;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DanMuBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.LiveDetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.LiveNewsPresenter;

public class LiveNewsActivity extends BaseActivity<LiveNewsPresenter> implements LiveNewsPresenter.LiveNewsView, DanmakuVDPlayer.OnEditClickListener {

    private DanmakuVDPlayer vdPlayer;
    private TextView tv_live_titel, tv_live_content, tv_live_contentmore, tv_look, tv_live_num;
    private LinearLayout ll_zan, ll_look, ll_collect, ll_shared;
    private ImageView img_zan, img_look, img_collect, btSend;
    private EditText etContent;
    private RecyclerView recyclerview;
    private int liveId;
    private SpringView springView;
    private boolean dianzanflag = true;
    private boolean collectflag = true;
    private CommentAdapter commentAdapter;
    private OrientationUtils orientationUtils;
    private int page = 1;

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
        tv_live_num = findViewById(R.id.tv_live_num);
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
        springView = findViewById(R.id.live_spring);
        btSend = findViewById(R.id.img_send);

        orientationUtils = new OrientationUtils(this, vdPlayer);
        vdPlayer.onEditClickListener = this;
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        setPresenter(new LiveNewsPresenter(LiveNewsActivity.this));
        getPresenter().getDanmuData(liveId);
        getPresenter().loadLiveDetail(this, liveId);
        getPresenter().loadLiveCommentData(this, liveId, page);
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
    }

    @Override
    protected void setOnClick() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                springView.onFinishFreshAndLoad();

            }

            @Override
            public void onLoadmore() {
                page++;
                springView.onFinishFreshAndLoad();
            }
        });
        ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dianzanflag) {
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
                if (collectflag) {
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
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onPause() {
        GSYVideoManager.releaseAllVideos();
        super.onPause();

    }


    @Override
    public void loadDanmu(DanMuBean danMuBean) {
        vdPlayer.addDanmu(danMuBean);
    }

    @Override
    public void loadLiveDetail(LiveDetailBean.LiveDetail liveDetailBean) {
        tv_live_titel.setText(liveDetailBean.live_desc);
        tv_live_content.setText(liveDetailBean.live_title);
        tv_live_num.setText(liveDetailBean.laud_num + "");
        tv_look.setText(liveDetailBean.is_laud + "");
        initGsy(liveDetailBean);
    }

    /**
     * 使能Gsy播放器
     *
     * @param liveDetail
     */
    private void initGsy(LiveDetailBean.LiveDetail liveDetail) {
        GSYVideoOptionBuilder builder = new GSYVideoOptionBuilder();

        builder
//                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setUrl(liveDetail.live_source)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setCacheWithPlay(false)
                .setVideoTitle("")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        orientationUtils.setEnable(true);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                orientationUtils.setEnable(!lock);
            }
        }).build(vdPlayer);
        vdPlayer.btnFull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orientationUtils.resolveByClick();
                if (orientationUtils.getIsLand() > 0) {
                    orientationUtils.backToProtVideo();
                }
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                vdPlayer.startWindowFullscreen(LiveNewsActivity.this, false, true);
            }
        });
        vdPlayer.getStartButton().performClick();
    }

    @Override
    public void loadLiveMoreSuccess(List<MoreBean.MoreDatas> moreDatas) {

    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {
        Log.d("--------------", commentData.size() + "");
        commentAdapter = new CommentAdapter(this,commentData);
        recyclerview.setAdapter(commentAdapter);
    }


    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void onEditClick() {

    }

    @Override
    public void sendDanMu(String str) {
        vdPlayer.addDanmaku(str, true);
        getPresenter().sendDanmu(TMSharedPUtil.getTMToken(this), liveId, str);
    }

}
