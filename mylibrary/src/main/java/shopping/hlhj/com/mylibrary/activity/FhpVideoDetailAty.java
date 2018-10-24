package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
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
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoHelper;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.FullyGridLayoutManager;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.adapter.MoreVideoAdp;
import shopping.hlhj.com.mylibrary.adapter.RecommendAdapter;
import shopping.hlhj.com.mylibrary.adapter.VideoCommentAdp;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.RecommendBean;
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

/**
 * Created by Never Fear   on 2018\10\23 0023.
 * Never More....
 */

public class FhpVideoDetailAty extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView, CollectPresenter.CollectView {

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
    private MoreVideoAdp recommendAdapter;
    private List<MoreBean.MoreDatas> recommenDatas = new ArrayList<>();
    private GoLoginDialog goLoginDialog;
    private boolean isZan=false;
    private int likeNum=0;
    @Override
    protected int getContentResId() {
        return R.layout.aty_new_video_detail;
    }

    @Override
    protected void beforeinit() {
        id = getIntent().getExtras().getInt("id");

    }

    @Override
    protected void initView() {
        goLoginDialog = new GoLoginDialog(FhpVideoDetailAty.this);
        tvLaudNum=findViewById(R.id.tvLaudNum);
        vdPlayer=findViewById(R.id.hot_gsyvideo);
        btColl=findViewById(R.id.btColl);
        tv_time=findViewById(R.id.tv_time);
        tv_author=findViewById(R.id.tv_author);
        commentList=findViewById(R.id.commentList);
        recommendList=findViewById(R.id.recommendList);
        btSend=findViewById(R.id.btSendComment);
        btZan=findViewById(R.id.btLike);
        btExit=findViewById(R.id.btExit);
        etContent=findViewById(R.id.etContent);
        commentAdapter = new VideoCommentAdp(commentDataList);
        commentAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.comment_empty,null));
        commentList.setAdapter(commentAdapter);
        commentList.setNestedScrollingEnabled(false);
        commentList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        recommendAdapter=new MoreVideoAdp(recommenDatas);
        recommendList.setAdapter(recommendAdapter);
        recommendList.setLayoutManager(new FullyGridLayoutManager(this,2));
        recommendList.setNestedScrollingEnabled(false);
    }

    @Override
    protected void onResume() {
        vdPlayer.onVideoResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        vdPlayer.onVideoPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        GSYVideoManager.releaseAllVideos();
        super.onDestroy();
    }

    @Override
    protected void initData() {
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this,id, TMSharedPUtil.getTMUser(this).getMember_id());
        getPresenter().loadHotCommentData(this,id,page);
        getPresenter().loadMoreVideoData(this,1);
        collectPresenter=new CollectPresenter(this);
    }

    @Override
    protected void setOnClick() {
        btColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectflag) {
                    Log.e("fhp",(tittle==null)+"");
                    collectPresenter.addColl(
                            TMSharedPUtil.getTMUser(FhpVideoDetailAty.this).getMember_code() + ""
                            , tittle
                            , tittle
                            , Constant.APP_ID
                            , id + ""
                            , extendStr
                            , ""
                            , 1 + ""
                            , "aaa",
                            TMSharedPUtil.getTMToken(FhpVideoDetailAty.this));
                 /*   btColl.setImageResource(R.drawable.ic_collection);
                    collectflag = false;*/
                } else {
                    collectPresenter.cancelColl(cid, TMSharedPUtil.getTMToken(FhpVideoDetailAty.this));
                   /* btColl.setImageResource(R.drawable.ic_sc_normal);
                    collectflag = true;*/
                }
            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etString = etContent.getText().toString();
                String tmToken = TMSharedPUtil.getTMToken(FhpVideoDetailAty.this);
                if (null == tmToken || "".equals(tmToken) || TextUtils.isEmpty(tmToken)) {
                    Toast.makeText(FhpVideoDetailAty.this, "请登录", Toast.LENGTH_SHORT).show();
                    goLoginDialog.show();
                    return;
                }
                if (null == etString || "".equals(etString) || TextUtils.isEmpty(etString)) {
                    Toast.makeText(FhpVideoDetailAty.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != etString && !"".equals(etString) && tmToken != null && !tmToken.equals("")) {
                    getPresenter().sendComment(FhpVideoDetailAty.this
                            , id
                            , TMSharedPUtil.getTMUser(FhpVideoDetailAty.this).getMember_id()
                            , TMSharedPUtil.getTMUser(FhpVideoDetailAty.this).getHead_pic()
                            , TMSharedPUtil.getTMUser(FhpVideoDetailAty.this).getMember_name()
                            , etString);
                    getPresenter().loadHotCommentData(FhpVideoDetailAty.this, id, page);
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
    }

    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tittle=detailDatas.title;
        GSYVideoHelper.GSYVideoHelperBuilder builder = new GSYVideoHelper.GSYVideoHelperBuilder();
        Log.e("fhp",detailDatas.video_url);
        builder
//                .setVideoTitle(detailDatas.title)
                .setAutoFullWithSize(true)
                .setCacheWithPlay(true)
                .setUrl(detailDatas.video_url)
                .build(vdPlayer);
        vdPlayer.getBackButton().setVisibility(View.GONE);
        vdPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vdPlayer.startWindowFullscreen(FhpVideoDetailAty.this,false,false);
            }
        });
        tv_time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm"));
        tv_author.setText(detailDatas.release);

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
        recommenDatas.addAll(moreDatas);
        recommendAdapter.notifyDataSetChanged();
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
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
        goLoginDialog.show();
    }

    @Override
    public void addCollectError() {
//todo 收藏接口访问失败回调
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
        goLoginDialog.show();
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
}
