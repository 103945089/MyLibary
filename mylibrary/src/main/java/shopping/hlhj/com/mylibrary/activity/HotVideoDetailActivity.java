package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.ExtendBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.ParamsBean;
import shopping.hlhj.com.mylibrary.bean.TuijianData;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class HotVideoDetailActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView, CollectPresenter.CollectView {

    @Override
    public void loadTuiJian(TuijianData data) {

    }

    private StandardGSYVideoPlayer vdPlayer;
    private EditText etContent;
    private LinearLayout ll_videodetail;
    private RecyclerView recyclerView;
    private TextView tv_title, tv_time, tv_author, tv_comment_normal;
    private ImageView img_btn;
    private SpringView springView;
    private int id;
    private int page = 1;
    private ImageView btSend, btColl;
    private String etString;
    private OrientationUtils orientationUtils;
    private CommentAdapter commentAdapter;
    private int cid = 0;//收藏Id
    private String extendStr;//拓展字段，收藏使用
    private boolean collectflag = true;
    private String tittle;
    private CollectPresenter collectPresenter;
    private List<CommentBean.CommentData> commentDataList = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_hotvideodetail;
    }

    @Override
    protected void beforeinit() {

        id = getIntent().getExtras().getInt("id");
        if (id==0){
            Gson g = new Gson();
            id = g.fromJson(getIntent().getStringExtra("paramStr"), ParamsBean.class).getID();
        }
    }

    @Override
    protected void initView() {
        Intent intent = new Intent(this, FhpVideoDetailAty.class);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
        ll_videodetail = findViewById(R.id.ll_videodetail);
        vdPlayer = findViewById(R.id.hot_gsyvideo);

        btColl = findViewById(R.id.btColl);

        etContent = findViewById(R.id.etContent);
        recyclerView = findViewById(R.id.ry_comment);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_author = findViewById(R.id.tv_author);
        tv_comment_normal = findViewById(R.id.tv_comment_normal);
        img_btn = findViewById(R.id.btSend);
        springView = findViewById(R.id.springview_hot);

        orientationUtils = new OrientationUtils(this, vdPlayer);

        ExtenStr();
    }
    //todo ExtenStr的配置
    public void ExtenStr(){
        ParamsBean paramsBean = new ParamsBean();
        paramsBean.setID(id);
        ExtendBean extendBean = new ExtendBean();
        ExtendBean.AndroidInfoBean androidInfoBean = new ExtendBean.AndroidInfoBean();
        ExtendBean.IosInfoBean iosInfoBean = new ExtendBean.IosInfoBean();
        androidInfoBean.setNativeX(true);
        androidInfoBean.setParamStr(new Gson().toJson(paramsBean));
        androidInfoBean.setSrc("shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity");
        androidInfoBean.setWwwFolder("");

        iosInfoBean.setNativeX(true);
        iosInfoBean.setParamStr(new Gson().toJson(paramsBean));
        iosInfoBean.setSrc("");
        androidInfoBean.setWwwFolder("");

        extendBean.setAndroidInfo(androidInfoBean);
        extendBean.setIosInfo(iosInfoBean);

        extendStr = new Gson().toJson(extendBean);

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        GSYVideoManager.releaseAllVideos();
        super.onPause();

    }

    @Override
    protected void initData() {
        /*LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        ll_videodetail.setVisibility(View.GONE);
        springView.setHeader(new DefaultHeader(recyclerView.getContext()));
        springView.setFooter(new DefaultFooter(recyclerView.getContext()));
        setPresenter(new HotVideoPresenter(this));
        collectPresenter = new CollectPresenter(this);
        collectPresenter.isColl(TMSharedPUtil.getTMUser(this).getMember_code()
                , String.valueOf(TMSharedPUtil.getTMUser(this).getMember_id()), String.valueOf(id), TMSharedPUtil.getTMToken(this));

        getPresenter().loadVideoData(this, id, 0);
        getPresenter().loadHotCommentData(this, id, page);*/
    }

    @Override
    protected void setOnClick() {

        btColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectflag) {
                    Log.e("fhp",(tittle==null)+"");
                    collectPresenter.addColl(
                            TMSharedPUtil.getTMUser(HotVideoDetailActivity.this).getMember_code() + ""
                            , tittle
                            , tittle
                            , Constant.APP_ID
                            , id + ""
                            , extendStr
                            , ""
                            , 1 + ""
                            , "aaa",
                            TMSharedPUtil.getTMToken(HotVideoDetailActivity.this));
                    btColl.setImageResource(R.drawable.ic_collection);
                    collectflag = false;
                } else {
                    collectPresenter.cancelColl(cid, TMSharedPUtil.getTMToken(HotVideoDetailActivity.this));
                    btColl.setImageResource(R.drawable.ic_sc_normal);
                    collectflag = true;
                }
            }
        });

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
                    return;
                }
                if (null == etString || "".equals(etString) || TextUtils.isEmpty(etString)) {
                    Toast.makeText(HotVideoDetailActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != etString && !"".equals(etString) && tmToken != null && !tmToken.equals("")) {
                    getPresenter().sendComment(HotVideoDetailActivity.this
                            , id
                            , TMSharedPUtil.getTMUser(HotVideoDetailActivity.this).getMember_id()
                            , TMSharedPUtil.getTMUser(HotVideoDetailActivity.this).getHead_pic()
                            , TMSharedPUtil.getTMUser(HotVideoDetailActivity.this).getMember_name()
                            , etString);
                    getPresenter().loadHotCommentData(HotVideoDetailActivity.this, id, page);
                }
            }
        });

    }

    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tv_title.setText(detailDatas.title);
        tittle=detailDatas.title;
        tv_time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm"));
        tv_author.setText(detailDatas.release);
        if (null == detailDatas.video_url || "".equals(detailDatas.video_url)) {
            addHistroy(1);
            Intent intent = new Intent(HotVideoDetailActivity.this, TextDetailsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        } else {
            addHistroy(2);
            ll_videodetail.setVisibility(View.VISIBLE);
            initGsy(detailDatas);
        }
    }

    //todo 添加浏览记录接口 每次进页面掉一次， 不需要回调
    public void addHistroy(int type) {
//        collectPresenter.addHis(TMSharedPUtil.getTMUser(this).getMember_code() + ""
//                , title
//                , title
//                , Constant.APP_ID
//                , id + ""
//                , extendStr
//                , ""
//                , 2 + ""
//                , TMSharedPUtil.getTMUser(this).getHead_pic()
//                , TMSharedPUtil.getTMToken(this));
    }

    @Override
    public void addCollect(@NotNull CollBean collBean) {
        // 添加收藏成功，保存收藏条目ID,准备取消收藏当入参使用
        cid = collBean.getData().getStar_id();
        //todo 下面将图标变成已收藏
        btColl.setImageResource(R.drawable.ic_collection);
        collectflag = false;
    }

    @Override
    public void hasCollected(@NotNull CollBean collBean) {
        btColl.setImageResource(R.drawable.ic_collection);
        collectflag = false;
        cid = collBean.getData().getStar_id();
    }

    @Override
    public void notCollected() {
        //Todo  未收藏，让按钮变成未收藏样式
        Log.e("fhp","未收藏----------");
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void addCollectError(@NotNull Exception e) {
//todo 收藏接口访问失败回调
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void addCollectError() {
//todo 收藏接口访问失败回调
        Toast.makeText(this, "收藏失败", Toast.LENGTH_SHORT).show();
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void cancelCollect() {
        //todo 取消收藏成功 将图标变为未收藏
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
        Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT);
    }

    @Override
    public void cancelCollectErro() {
        //todo 取消收藏失败
        Toast.makeText(this, "取消收藏失败", Toast.LENGTH_SHORT);
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

    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas) {

    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {
        tv_comment_normal.setVisibility(View.GONE);
        if (page == 1) {
            commentDataList.clear();
            commentDataList.addAll(commentData);
        } else {
            commentDataList.addAll(commentData);
        }
        commentAdapter = new CommentAdapter(HotVideoDetailActivity.this, commentDataList, false);
        recyclerView.setAdapter(commentAdapter);
    }

    @Override
    public void loadSendCommentSuccess(String msg) {
        etContent.setText("");
        Toast.makeText(HotVideoDetailActivity.this, msg.toString(), Toast.LENGTH_SHORT).show();
    }

}
