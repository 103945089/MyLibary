package shopping.hlhj.com.mylibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.azimolabs.keyboardwatcher.KeyboardWatcher;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.share.bean.TMLinkShare;
import com.tenma.ventures.share.util.TMShareUtil;

import org.apache.cordova.LOG;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.DanmakuVDPlayer;
import shopping.hlhj.com.mylibrary.Tool.KeyboardUtil;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DanMuBean;
import shopping.hlhj.com.mylibrary.bean.ExtendBean;
import shopping.hlhj.com.mylibrary.bean.LiveDetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.ParamsBean;
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.cv.TextEditTextView;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;
import shopping.hlhj.com.mylibrary.presenter.LiveNewsPresenter;

import static android.widget.Toast.LENGTH_SHORT;

public class LiveNewsActivity extends BaseActivity<LiveNewsPresenter> implements LiveNewsPresenter.LiveNewsView, DanmakuVDPlayer.OnEditClickListener, CollectPresenter.CollectView, KeyboardWatcher.OnKeyboardToggleListener {

    private DanmakuVDPlayer vdPlayer;
    private TextView tv_live_titel, tv_live_content, tv_live_contentmore, tv_look, tv_live_num, tv_livecomment_normal;
    private LinearLayout ll_zan, ll_look, ll_collect, ll_shared;
    private ImageView img_zan, img_look, img_collect, btSend;
    private EditText etContent;
    private RecyclerView recyclerview;
    private int liveId = 0;
    private SpringView springView;
    private boolean dianzanflag = false;
    private boolean collectflag = true;
    private CommentAdapter commentAdapter;
    private OrientationUtils orientationUtils;
    private int page = 1;

    private int lanud_num;

    public static boolean isDanmu=false;

    private int cid = 0;//收藏Id
    private String title;
    private CollectPresenter collectPresenter;
    private String extendStr;//拓展字段，收藏使用
    private List<CommentBean.CommentData> commentDataList = new ArrayList<>();
    private String thumb,url;
    private KeyboardWatcher keyboardWatcher;
    @Override
    protected int getContentResId() {
        return R.layout.aty_livedetail_new;
    }

    @Override
    protected void beforeinit() {
        liveId = getIntent().getExtras().getInt("id");
        if (liveId == 0) {
            //todo 不是从自己页面跳过来的， 要在这里接收收藏列表传来的参数
            Gson g = new Gson();
            liveId = g.fromJson(getIntent().getStringExtra("paramStr"), ParamsBean.class).getID();
        } else {

        }
    }

    @Override
    protected void initView() {
        vdPlayer = findViewById(R.id.videoPlayer);
        tv_live_titel = findViewById(R.id.tv_live_titel);
        tv_live_content = findViewById(R.id.tv_live_content);
        tv_live_contentmore = findViewById(R.id.tv_live_contentmore);
        tv_livecomment_normal = findViewById(R.id.tv_livecomment_normal);
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
        vdPlayer.setOnEditClickListener(LiveNewsActivity.this);

        //todo ExtenStr的配置
        ParamsBean paramsBean = new ParamsBean();
        paramsBean.setID(liveId);
        ExtendBean extendBean = new ExtendBean();
        ExtendBean.AndroidInfoBean androidInfoBean = new ExtendBean.AndroidInfoBean();
        ExtendBean.IosInfoBean iosInfoBean = new ExtendBean.IosInfoBean();
        androidInfoBean.setNativeX(true);
        androidInfoBean.setParamStr(new Gson().toJson(paramsBean));
        androidInfoBean.setSrc("shopping.hlhj.com.mylibrary.activity.LiveNewsActivity");
        androidInfoBean.setWwwFolder("");

        iosInfoBean.setNativeX(true);
        iosInfoBean.setParamStr(new Gson().toJson(paramsBean));
        iosInfoBean.setSrc("HLHJLookLiveDetilController");
        androidInfoBean.setWwwFolder("");

        extendBean.setAndroidInfo(androidInfoBean);
        extendBean.setIosInfo(iosInfoBean);

        extendStr = new Gson().toJson(extendBean);

    }

    @Override
    public void onKeyboardShown(int keyboardSize) {

    }

    @Override
    public void onKeyboardClosed() {
        isDanmu=false;
        Log.e("fhp","改动了。。。。。");
        etContent.setHint("输入评论");
    }

    @Override
    protected void initData() {
        keyboardWatcher=new KeyboardWatcher(this);
        keyboardWatcher.setListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(manager);
        collectPresenter = new CollectPresenter(this);

        setPresenter(new LiveNewsPresenter(LiveNewsActivity.this));
        getPresenter().getDanmuData(liveId);
        getPresenter().loadLiveDetail(this, liveId);
        getPresenter().loadLiveCommentData(this, liveId, page);
        springView.setHeader(new DefaultHeader(recyclerview.getContext()));
        springView.setFooter(new DefaultFooter(recyclerview.getContext()));

        collectPresenter.isColl(TMSharedPUtil.getTMUser(this).getMember_code()
                , Constant.APP_ID, String.valueOf(liveId), TMSharedPUtil.getTMToken(this));



    }

    @Override
    protected void setOnClick() {

        ll_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMLinkShare tmLinkShare = new TMLinkShare();
                tmLinkShare.setThumb(Constant.IMG_URL+thumb);
                tmLinkShare.setTitle(title);
                tmLinkShare.setDescription(title);
                tmLinkShare.setUrl(url);
                TMShareUtil.getInstance(LiveNewsActivity.this).shareLink(tmLinkShare);
            }
        });
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadLiveCommentData(LiveNewsActivity.this, liveId, page);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page++;
                getPresenter().loadLiveCommentData(LiveNewsActivity.this, liveId, page);
                springView.onFinishFreshAndLoad();
            }
        });
        ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getPresenter().likeIt(LiveNewsActivity.this,liveId,2);

            }
        });
        ll_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectflag) {
                    collectPresenter.addColl(TMSharedPUtil.getTMUser(LiveNewsActivity.this).getMember_code() + ""
                            , title
                            , title
                            , Constant.APP_ID
                            , liveId + ""
                            , extendStr
                            , ""
                            , 2 + ""
                            , "aaa",
                            TMSharedPUtil.getTMToken(LiveNewsActivity.this));
                    img_collect.setImageResource(R.drawable.ic_collection);
                    collectflag = false;
                } else {
                    collectPresenter.cancelColl(cid, TMSharedPUtil.getTMToken(LiveNewsActivity.this));
                    img_collect.setImageResource(R.drawable.ic_sc_normal);
                    collectflag = true;
                }
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDanmu){
                    String tmToken = TMSharedPUtil.getTMToken(getApplicationContext());
                    String string = etContent.getText().toString();
                    if (null == tmToken || "".equals(tmToken) || TextUtils.isEmpty(tmToken)) {
                        Toast.makeText(LiveNewsActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LiveNewsActivity.this, ConfirmLoginActivity.class));
                        return;
                    }
                    if (null == string || "".equals(string) || TextUtils.isEmpty(string)) {
                        Toast.makeText(LiveNewsActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (null != string && !"".equals(string) && tmToken != null && !tmToken.equals("")) {
                        getPresenter().sendComment(LiveNewsActivity.this, liveId, string, tmToken);
                        getPresenter().loadLiveCommentData(LiveNewsActivity.this, liveId, page);
                    }
                }else {
                    String tmToken = TMSharedPUtil.getTMToken(getApplicationContext());
                    String string = etContent.getText().toString();
                    if (null == tmToken || "".equals(tmToken) || TextUtils.isEmpty(tmToken)) {
                        Toast.makeText(LiveNewsActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LiveNewsActivity.this, ConfirmLoginActivity.class));
                        return;
                    }
                    if (null == string || "".equals(string) || TextUtils.isEmpty(string)) {
                        Toast.makeText(LiveNewsActivity.this, "请输入弹幕", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (null != string && !"".equals(string) && tmToken != null && !tmToken.equals("")) {
                        vdPlayer.addDanmaku(etContent.getText().toString(),true);
                        getPresenter().sendDanmu(TMSharedPUtil.getTMToken(LiveNewsActivity.this),liveId,etContent.getText().toString());
                        etContent.setText("");
                    }
                    etContent.setHint("输入评论");
                    isDanmu=false;
                }

            }
        });
    }

    @Override
    protected void onPause() {
        GSYVideoManager.releaseAllVideos();
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        LOG.e("fhp","已经关了？？？");
        super.onDestroy();

    }

    @Override
    public void loadDanmu(DanMuBean danMuBean) {
        vdPlayer.addDanmu(danMuBean);
    }

    @Override
    public void loadLiveDetail(LiveDetailBean.LiveDetail liveDetailBean) {
        url=liveDetailBean.getLive_source();
        thumb=Constant.IMG_URL+liveDetailBean.getLive_thumb();
       /* if (liveDetailBean.getIs_collection()==0){
            img_collect.setImageResource(R.drawable.ic_sc_normal);
        }else {
            img_collect.setImageResource(R.drawable.ic_collection);
        }*/
       if (liveDetailBean.getIs_laud()==1){
           img_zan.setImageResource(R.drawable.ic_home_praise_select);
            dianzanflag=true;
       }else {
           img_zan.setImageResource(R.drawable.ic_home_praise_normal);
           dianzanflag=false;
       }
        tv_look.setText(liveDetailBean.read_num+"");

        tv_live_titel.setText(liveDetailBean.live_title);
        tv_live_content.setText(liveDetailBean.live_desc);
        lanud_num = liveDetailBean.laud_num;
        tv_live_num.setText(liveDetailBean.laud_num + "");
        title = liveDetailBean.live_title;
        //todo 添加浏览记录接口 每次进页面掉一次， 不需要回调
        collectPresenter.addHis(TMSharedPUtil.getTMUser(this).getMember_code() + ""
                , title
                , title
                , Constant.APP_ID
                , liveId + ""
                , extendStr
                , ""
                , 2 + ""
                , TMSharedPUtil.getTMUser(this).getHead_pic()
                , TMSharedPUtil.getTMToken(this));
        Log.d("--------------",title.toString());
        initGsy(liveDetailBean);
    }

    @Override
    public void loadSendCommentSuccess(String msg) {
        if (msg.equals("200")) {
            getPresenter().loadLiveCommentData(LiveNewsActivity.this,liveId,1);
            etContent.setText("");

            Toast.makeText(LiveNewsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
        }
        commentAdapter.upData(this);
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
                        DanmakuVDPlayer.isLay=false;
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
                DanmakuVDPlayer.isLay=true;
                vdPlayer.setOnEditClickListener(LiveNewsActivity.this);
                if (orientationUtils.getIsLand() > 0) {
                    orientationUtils.backToProtVideo();
                }
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                vdPlayer.startWindowFullscreen(LiveNewsActivity.this, false, true);
            }
        });
        vdPlayer.getStartButton().performClick();

        vdPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadLiveMoreSuccess(List<MoreBean.MoreDatas> moreDatas) {

    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {
        tv_livecomment_normal.setVisibility(View.GONE);
        if (page == 1) {
            commentDataList.clear();
            commentDataList.addAll(commentData);
        } else {
            commentDataList.addAll(commentData);
        }
        commentAdapter = new CommentAdapter(this, commentDataList, true);
        recyclerview.setAdapter(commentAdapter);
    }


    @Override
    public void loadFailed(String msg) {
        if (msg.equals("评论失败")){
            new GoLoginDialog(this).show();
        }
        Toast.makeText(LiveNewsActivity.this, msg.toString(), LENGTH_SHORT);
//        if (msg.equals("1")) {
//            tv_livecomment_normal.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onEditClick() {
        if (isDanmu){
            isDanmu=false;
            etContent.setHint("输入评论");
        }else {
            isDanmu=true;
            etContent.setHint("输入弹幕");
        }
        Log.e("fhp","调起框");
        KeyboardUtil.showSoftInput(this,etContent);
        /*InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            etContent.requestFocus();
            imm.showSoftInput(etContent, 0);
        }*/
    }

    @Override
    public void sendDanMu(String str) {
        Log.e("fhp","发送一次弹幕");
//        vdPlayer.addDanmaku(str, true);
        getPresenter().sendDanmu(TMSharedPUtil.getTMToken(this), liveId, str);
    }

    /**
     * 是否收藏回调
     *
     * @param collBean 收藏javabean 其中包含收藏条目ID,保存收藏ID,用来访问取消收藏接口的入参使用
     */
    @Override
    public void hasCollected(CollBean collBean) {
        //Todo  已收藏，让按钮变成已收藏样式
        img_collect.setImageResource(R.drawable.ic_collection);
        collectflag = false;
        cid = collBean.getData().getStar_id();
    }

    @Override
    public void notCollected() {
        //Todo  未收藏，让按钮变成未收藏样式
        img_collect.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void addCollect(CollBean collBean) {
        // 添加收藏成功，保存收藏条目ID,准备取消收藏当入参使用
        cid = collBean.getData().getStar_id();
        //todo 下面将图标变成已收藏
        img_collect.setImageResource(R.drawable.ic_collection);
        collectflag = false;

        OkGo.<String>post(Constant.CollMine)
                .params("id",liveId)
                .params("type",1)
                .params("token",TMSharedPUtil.getTMToken(this))
                .headers("token",TMSharedPUtil.getTMToken(this))
                .params("sid",collBean.getData().getStar_id())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
    }

    @Override
    public void addCollectError(Exception e) {
        //todo 收藏接口访问失败回调
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        img_collect.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void addCollectError() {
        //todo 收藏接口访问失败回调
        Toast.makeText(this, "收藏失败", Toast.LENGTH_SHORT).show();
        img_collect.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void cancelCollect() {
        //todo 取消收藏成功 将图标变为未收藏
        img_collect.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
        Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT);

        OkGo.<String>post(Constant.CollMine)
                .params("id",liveId)
                .params("type",1)
                .params("token",TMSharedPUtil.getTMToken(this))
                .headers("token",TMSharedPUtil.getTMToken(this))
                .params("sid",cid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
    }

    @Override
    public void cancelCollectErro() {
        //todo 取消收藏失败
        Toast.makeText(this, "取消收藏失败", Toast.LENGTH_SHORT);
    }

    @Override
    public void likeSuccess() {
        if (!dianzanflag) {
            img_zan.setImageResource(R.drawable.ic_home_praise_select);
            lanud_num++;
            tv_live_num.setText(lanud_num  + "");
            dianzanflag = true;
        } else {
            img_zan.setImageResource(R.drawable.ic_home_praise_normal);
            lanud_num--;
            tv_live_num.setText(lanud_num +"");
            dianzanflag = false;
        }
    }

    @Override
    public void likeErro() {
        new GoLoginDialog(LiveNewsActivity.this).show();
    }
}
