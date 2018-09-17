package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class HotVideoDetailActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView {

    private StandardGSYVideoPlayer vdPlayer;
    private EditText etContent;
    private LinearLayout ll_videodetail;
    private RecyclerView recyclerView;
    private TextView tv_title, tv_time, tv_author, tv_comment_normal;
    private ImageView img_btn;
    private SpringView springView;
    private int id;
    private int page = 1;
    private String etString;
    private OrientationUtils orientationUtils;
    private CommentAdapter commentAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.activity_hotvideodetail;
    }

    @Override
    protected void beforeinit() {
        id = getIntent().getExtras().getInt("id");
    }

    @Override
    protected void initView() {
        ll_videodetail = findViewById(R.id.ll_videodetail);
        vdPlayer = findViewById(R.id.hot_gsyvideo);
        etContent = findViewById(R.id.et_Content);
        recyclerView = findViewById(R.id.ry_comment);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_author = findViewById(R.id.tv_author);
        tv_comment_normal = findViewById(R.id.tv_comment_normal);
        img_btn = findViewById(R.id.btSend);
        springView = findViewById(R.id.springview_hot);
        orientationUtils = new OrientationUtils(this, vdPlayer);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        GSYVideoManager.releaseAllVideos();
        super.onPause();

    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ll_videodetail.setVisibility(View.GONE);
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this, id, 0);
        getPresenter().loadHotCommentData(this, id, page);
    }

    @Override
    protected void setOnClick() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadHotCommentData(HotVideoDetailActivity.this, id, page);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page++;
                getPresenter().loadHotCommentData(HotVideoDetailActivity.this, id, page);
                springView.onFinishFreshAndLoad();
            }
        });
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etString = etContent.getText().toString();
                String tmToken = TMSharedPUtil.getTMToken(HotVideoDetailActivity.this);
                if (null == tmToken || "".equals(tmToken) || TextUtils.isEmpty(tmToken)) {
                    Toast.makeText(HotVideoDetailActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HotVideoDetailActivity.this, ConfirmLoginActivity.class));
                    return;
                }
                if (null == etString || "".equals(etString) || TextUtils.isEmpty(etString)) {
                    Toast.makeText(HotVideoDetailActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != etString && !"".equals(etString) && tmToken != null && !tmToken.equals("")) {
                    getPresenter().sendComment(HotVideoDetailActivity.this, id, etString, tmToken);
                }
            }
        });

    }

    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tv_title.setText(detailDatas.title);
        tv_time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm"));
        tv_author.setText(detailDatas.release);
        if (null == detailDatas.video_url || "".equals(detailDatas.video_url)){
            Intent intent = new Intent(HotVideoDetailActivity.this,TextDetailsActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
            finish();
        }else {
            ll_videodetail.setVisibility(View.VISIBLE);
            initGsy(detailDatas);
        }
    }

    private void initGsy(DetailBean.DetailDatas detailDatas) {
        GSYVideoOptionBuilder builder = new GSYVideoOptionBuilder();

        builder
//                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setUrl(detailDatas.video_url)
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
        vdPlayer.getStartButton().performClick();

        vdPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orientationUtils.resolveByClick();
                if (orientationUtils.getIsLand() > 0) {
                    orientationUtils.backToProtVideo();
                }
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                vdPlayer.startWindowFullscreen(HotVideoDetailActivity.this, false, true);
            }
        });

        vdPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadFailed(String msg) {
        if (msg.equals("1")) {
            tv_comment_normal.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas) {

    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {
        tv_comment_normal.setVisibility(View.GONE);
        commentAdapter = new CommentAdapter(HotVideoDetailActivity.this, commentData);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public void loadSendCommentSuccess(String msg) {
        Toast.makeText(HotVideoDetailActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
    }

}
