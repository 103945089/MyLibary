package shopping.hlhj.com.mylibrary.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class HotVideoDetailActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView {

    private StandardGSYVideoPlayer vdPlayer;
    private EditText etContent;
    private ListView listView;
    private TextView tv_title, tv_time, tv_author, tv_comment_normal;
    private ImageView img_btn;
    private int id;
    private String etString;
    private OrientationUtils orientationUtils;

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
        vdPlayer = findViewById(R.id.hot_gsyvideo);
        etContent = findViewById(R.id.et_Content);
        listView = findViewById(R.id.list_comment);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_author = findViewById(R.id.tv_author);
        tv_comment_normal = findViewById(R.id.tv_comment_normal);
        img_btn = findViewById(R.id.btSend);

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
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this, id, 0);
    }

    @Override
    protected void setOnClick() {
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etString = etContent.getText().toString();
                if (null != etString && !"".equals(etString)) {

                }
            }
        });

    }

    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tv_title.setText(detailDatas.title);
        tv_time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm"));
        tv_author.setText(detailDatas.release);
        initGsy(detailDatas);
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
                if (orientationUtils.getIsLand()>0){
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

    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas) {

    }

}
