package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tenma.ventures.bean.TMUser;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.share.bean.TMLinkShare;
import com.tenma.ventures.share.util.TMShareUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.adapter.MoreVideoAdp;
import shopping.hlhj.com.mylibrary.adapter.VideoCommentAdp;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.TuijianData;
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.presenter.AllCommentPresenter;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

/**
 * Created by Never Fear   on 2018\10\25 0025.
 * Never More....
 */

public class AllCommentAty extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView, CollectPresenter.CollectView {
    private StandardGSYVideoPlayer vdPlayer;
    private EditText etContent;
    private LinearLayout ll_videodetail;
    private RecyclerView commentList,recommendList;
    private TextView tv_title, tv_time, tv_author, tv_comment_normal,tvLaudNum;
    private ImageView img_btn,btZan,btExit;
    private SpringView springView;
    private int id;
    private int page = 1;
    private ImageView  btColl;
    private View btSend;
    private String etString;
    private OrientationUtils orientationUtils;
    private VideoCommentAdp commentAdapter;
    private int cid = 0;//收藏Id
    private String extendStr="";//拓展字段，收藏使用
    private boolean collectflag = true;
    private String tittle;
    private CollectPresenter collectPresenter;
    private List<CommentBean.CommentData> commentDataList = new ArrayList<>();
    private GoLoginDialog goLoginDialog;
    private boolean isZan=false;
    private View loBack,btGoShare;
    private View footView,tv11;
    private String thumb;
    private int likeNum=0;
    @Override
    protected int getContentResId() {
        return R.layout.aty_new_video_detail;
    }

    @Override
    protected void beforeinit() {
        id=getIntent().getIntExtra("id",0);
    }

    @Override
    protected void initView() {
        tvLaudNum=findViewById(R.id.tvLaudNum);
        vdPlayer=findViewById(R.id.hot_gsyvideo);
        loBack=findViewById(R.id.loBack);
        btColl=findViewById(R.id.btColl);
        tv_time=findViewById(R.id.tv_time);
        tv_author=findViewById(R.id.tv_author);
        commentList=findViewById(R.id.commentList);
        recommendList=findViewById(R.id.recommendList);
        btSend=findViewById(R.id.btSendComment);
        btZan=findViewById(R.id.btLike);
        btExit=findViewById(R.id.btExit);
        tv11=findViewById(R.id.tv11);
        etContent=findViewById(R.id.etContent);
        springView=findViewById(R.id.spView);
        btGoShare=findViewById(R.id.btGoShare);
        recommendList.setVisibility(View.GONE);
        tv11.setVisibility(View.GONE);
        commentAdapter=new VideoCommentAdp(commentDataList);
        btColl.setVisibility(View.GONE);
        commentList.setAdapter(commentAdapter);
        commentList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        commentList.setNestedScrollingEnabled(false);
        vdPlayer.getBackButton().setVisibility(View.GONE);

        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
        }
    }

    @Override
    public void onPause() {

        vdPlayer.onVideoPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        vdPlayer.onVideoResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        GSYVideoManager.releaseAllVideos();
        super.onDestroy();
    }

    @Override
    public void loadTuiJian(TuijianData data) {

    }

    @Override
    protected void initData() {
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this,id, TMSharedPUtil.getTMUser(this).getMember_id());
        getPresenter().loadHotCommentData(this,id,page);
        collectPresenter=new CollectPresenter(this);
    }

    @Override
    protected void setOnClick() {
        btGoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMLinkShare tmLinkShare = new TMLinkShare();
                tmLinkShare.setThumb(thumb);
                tmLinkShare.setDescription(tittle);
                tmLinkShare.setTitle(tittle);
                tmLinkShare.setUrl(Constant.IMG_URL+"/application/hlhj_webcast/kankan/index.html?id="+id);

                TMShareUtil.getInstance(AllCommentAty.this).shareLink(tmLinkShare);
            }
        });

        btColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectflag) {
                    Log.e("fhp",(tittle==null)+"");
                    collectPresenter.addColl(
                            TMSharedPUtil.getTMUser(AllCommentAty.this).getMember_code() + ""
                            , tittle
                            , tittle
                            , Constant.APP_ID
                            , id + ""
                            , extendStr
                            , ""
                            , 1 + ""
                            , "aaa",
                            TMSharedPUtil.getTMToken(AllCommentAty.this));
                 /*   btColl.setImageResource(R.drawable.ic_collection);
                    collectflag = false;*/
                } else {
                    collectPresenter.cancelColl(cid, TMSharedPUtil.getTMToken(AllCommentAty.this));
                   /* btColl.setImageResource(R.drawable.ic_nottocollect);
                    collectflag = true;*/
                }
            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etString = etContent.getText().toString();
                String tmToken = TMSharedPUtil.getTMToken(AllCommentAty.this);
                if (null == tmToken || "".equals(tmToken) || TextUtils.isEmpty(tmToken)) {
                    Toast.makeText(AllCommentAty.this, "请登录", Toast.LENGTH_SHORT).show();
                    goLoginDialog.show();
                    return;
                }
                if (null == etString || "".equals(etString) || TextUtils.isEmpty(etString)) {
                    Toast.makeText(AllCommentAty.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != etString && !"".equals(etString) && tmToken != null && !tmToken.equals("")) {
                    TMUser tmUser = TMSharedPUtil.getTMUser(AllCommentAty.this);
                    getPresenter().sendComment(AllCommentAty.this
                            , id
                            , tmUser.getMember_id()
                            , tmUser.getHead_pic()
                            , tmUser.getMember_nickname()
                                    ==null|| tmUser.getMember_nickname().isEmpty()?tmUser.getMember_name():tmUser.getMember_nickname()
                            , etString);
                    getPresenter().loadHotCommentData(AllCommentAty.this, id, page);
                }
            }
        });

        btZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isZan){
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",id)
                            .params("token",TMSharedPUtil.getTMToken(mContext))
                            .headers("token",TMSharedPUtil.getTMToken(mContext))
                            .params("type",0)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        isZan=true;
                                        btZan.setImageResource(R.drawable.ic_zan1);
                                        likeNum++;
                                        tvLaudNum.setText("点赞数:"+(likeNum));
                                    }else if (code==500){
                                        goLoginDialog.show();
                                    }
                                }
                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });
                }else {
                    OkGo.<String>post(Constant.ITS_GOOD)
                            .params("id",id)
                            .params("token",TMSharedPUtil.getTMToken(mContext))
                            .headers("token",TMSharedPUtil.getTMToken(mContext))
                            .params("type",0)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject jsonObject = JSON.parseObject(body);
                                    int code = jsonObject.getInteger("code");
                                    if (code == 200){
                                        isZan=false;
                                        btZan.setImageResource(R.drawable.ic_pl_zan);
                                        likeNum--;
                                        tvLaudNum.setText("点赞数:"+(likeNum));
                                    }else if (code==500){
                                        goLoginDialog.show();
                                    }
                                }
                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);
                                }
                            });
                }
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page=1;
                getPresenter().loadHotCommentData(AllCommentAty.this,id,page);

            }

            @Override
            public void onLoadmore() {
                page++;
                getPresenter().loadHotCommentData(AllCommentAty.this,id,page);
            }
        });
    }


    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tittle=detailDatas.title;
        thumb=detailDatas.getCover();
        int read_num = detailDatas.read_num;

        GSYVideoHelper.GSYVideoHelperBuilder builder = new GSYVideoHelper.GSYVideoHelperBuilder();
        Log.e("fhp",detailDatas.video_url);
        builder
//                .setVideoTitle(detailDatas.title)
                .setAutoFullWithSize(true)
                .setCacheWithPlay(true)
                .setUrl(detailDatas.video_url)
                .build(vdPlayer);
        vdPlayer.getBackButton().setVisibility(View.GONE);
        vdPlayer.startPlayLogic();
        vdPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vdPlayer.startWindowFullscreen(AllCommentAty.this,false,false);
            }
        });
        tv_time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm"));
        tv_author.setText(detailDatas.title);

        /*点赞按钮显示*/
        if (detailDatas.is_laud==1){
            isZan=true;
            btZan.setImageResource(R.drawable.ic_zan1);
        }else {
            isZan=false;
            btZan.setImageResource(R.drawable.ic_pl_zan);
        }
//        commentDataList.addAll(detailDatas.comment);
        tvLaudNum.setText("点赞数："+detailDatas.like);
        likeNum=detailDatas.like;
    }

    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> moreDatas) {
    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {
        if (page==1){
            commentDataList.clear();
            commentDataList.addAll(commentData);
        }else {
            commentDataList.addAll(commentData);
        }
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadSendCommentSuccess(String msg) {
        etContent.setText("");
        getPresenter().loadHotCommentData(this,id,1);
        commentDataList.clear();
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
        btColl.setImageResource(R.drawable.ic_nottocollect);
        collectflag = true;
    }

    @Override
    public void addCollectError(@NotNull Exception e) {
//todo 收藏接口访问失败回调
        btColl.setImageResource(R.drawable.ic_nottocollect);
        collectflag = true;
        goLoginDialog.show();
    }

    @Override
    public void addCollectError() {
//todo 收藏接口访问失败回调
        btColl.setImageResource(R.drawable.ic_nottocollect);
        collectflag = true;
        goLoginDialog.show();
    }

    @Override
    public void cancelCollect() {
        //todo 取消收藏成功 将图标变为未收藏
        btColl.setImageResource(R.drawable.ic_nottocollect);
        collectflag = true;
        Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT);
    }

    @Override
    public void cancelCollectErro() {
        //todo 取消收藏失败
        Toast.makeText(this, "取消收藏失败", Toast.LENGTH_SHORT);
    }

}
