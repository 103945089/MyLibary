package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.share.bean.TMLinkShare;
import com.tenma.ventures.share.util.TMShareUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class TextDetailsActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView, CollectPresenter.CollectView, CommentAdapter.NeedLoginListener {

    @Override
    public void loadTuiJian(TuijianData data) {

    }

    private RelativeLayout loNewHead;
    private ImageView btBack, btSend, btColl, btGoShare;
    private EditText etContent;
    private TextView tvTittleTextView, tv_Time, tv_auther, btMore;
    private RecyclerView comment_list;
    private WebView webView;
    private int id, page = 1;
    private int cid = 0;//收藏Id
    private String thumb="";
    private String extendStr;//拓展字段，收藏使用
    private String title;
    private boolean collectflag = true;
    private SpringView springview_textdetail;
    private View dv1;
    private CommentAdapter adapter;
    private CollectPresenter collectPresenter;
    private List<CommentBean.CommentData> commentDataList = new ArrayList<>();
    private GoLoginDialog loginDialog;
    @Override
    protected int getContentResId() {
        return R.layout.aty_special_info_kankan;
    }

    @Override
    protected void beforeinit() {
        id = getIntent().getExtras().getInt("id");
        if (id==0){
            Gson g = new Gson();
            id = g.fromJson(getIntent().getStringExtra("paramStr"), ParamsBean.class).getID();
        }
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.softInputMode= WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void initView() {
        loNewHead = findViewById(R.id.loNewHead);
        btBack = findViewById(R.id.btBack);
        btSend = findViewById(R.id.btSend);
        dv1=findViewById(R.id.dv1);
        btColl = findViewById(R.id.btColl);
        btGoShare = findViewById(R.id.btGoShare);
        etContent = findViewById(R.id.etContent);
        tvTittleTextView = findViewById(R.id.tvTittleTextView);
        tv_Time = findViewById(R.id.tv_Time);
        tv_auther = findViewById(R.id.tv_auther);
        comment_list = findViewById(R.id.comment_list);
        btMore = findViewById(R.id.btMore);
        webView = findViewById(R.id.webView);
        springview_textdetail = findViewById(R.id.springview_textdetail);
        loginDialog=new GoLoginDialog(this);
        adapter = new CommentAdapter(TextDetailsActivity.this, commentDataList, false);
        comment_list.setAdapter(adapter);

        String tmThemeColor = TMSharedPUtil.getTMThemeColor(this);
        if (null!=tmThemeColor&&!tmThemeColor.isEmpty()){
            dv1.setBackgroundColor(Color.parseColor(tmThemeColor));
        }


    }
/*TMLinkShare tmLinkShare = new TMLinkShare();
                tmLinkShare.setThumb(thumb);
                tmLinkShare.setDescription(tittle);
                tmLinkShare.setTitle(tittle);
                tmLinkShare.setUrl(Constant.IMG_URL+"/application/hlhj_webcast/kankan/index.html?id="+id);

                TMShareUtil.getInstance(FhpVideoDetailAty.this).shareLink(tmLinkShare);*/
    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        comment_list.setLayoutManager(manager);
        springview_textdetail.setHeader(new DefaultHeader(this));
        springview_textdetail.setFooter(new DefaultFooter(this));
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this, id, 0);
        getPresenter().loadHotCommentData(this, id, page);
        collectPresenter = new CollectPresenter(this);

        collectPresenter.isColl(TMSharedPUtil.getTMUser(this).getMember_code()
                , String.valueOf(TMSharedPUtil.getTMUser(this).getMember_id()), String.valueOf(id), TMSharedPUtil.getTMToken(this));
        ExtenStr();
    }

    @Override
    protected void setOnClick() {
        btGoShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMLinkShare tmLinkShare = new TMLinkShare();
                tmLinkShare.setThumb(thumb);
                tmLinkShare.setDescription(title);
                tmLinkShare.setTitle(title);
                tmLinkShare.setUrl(Constant.IMG_URL+"/application/hlhj_webcast/kankan/index.html?id="+id);
                TMShareUtil.getInstance(TextDetailsActivity.this).shareLink(tmLinkShare);
            }
        });
        springview_textdetail.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getPresenter().loadHotCommentData(TextDetailsActivity.this,id,page);
                springview_textdetail.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page ++;
                getPresenter().loadHotCommentData(TextDetailsActivity.this,id,page);
                springview_textdetail.onFinishFreshAndLoad();
            }
        });
        btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page++;
                getPresenter().loadHotCommentData(TextDetailsActivity.this, id, page);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmToken = TMSharedPUtil.getTMToken(getApplicationContext());
                String string = etContent.getText().toString();
                if (null == tmToken || "".equals(tmToken) || TextUtils.isEmpty(tmToken)) {
                    loginDialog.show();
                   /* Toast.makeText(TextDetailsActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TextDetailsActivity.this, ConfirmLoginActivity.class));*/
                    return;
                }
                if (null == string || "".equals(string) || TextUtils.isEmpty(string)) {
                    Toast.makeText(TextDetailsActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != string && !"".equals(string) && tmToken != null && !tmToken.equals("")) {
                    getPresenter().sendComment(TextDetailsActivity.this
                            , id
                            , TMSharedPUtil.getTMUser(TextDetailsActivity.this).getMember_id()
                            , TMSharedPUtil.getTMUser(TextDetailsActivity.this).getHead_pic()
                            , TMSharedPUtil.getTMUser(TextDetailsActivity.this).getMember_name()
                            , string);

                }
            }
        });
        btColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectflag) {
                    Log.e("fhp",(title==null)+"");
                    collectPresenter.addColl(
                            TMSharedPUtil.getTMUser(TextDetailsActivity.this).getMember_code() + ""
                            , title
                            , title
                            , Constant.APP_ID
                            , id + ""
                            , extendStr
                            , ""
                            , 1 + ""
                            , thumb,
                            TMSharedPUtil.getTMToken(TextDetailsActivity.this));
                    btColl.setImageResource(R.drawable.ic_collection);
                    collectflag = false;
                } else {
                    collectPresenter.cancelColl(cid, TMSharedPUtil.getTMToken(TextDetailsActivity.this));
                    btColl.setImageResource(R.drawable.ic_sc_normal);
                    collectflag = true;
                }
            }
        });
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
        androidInfoBean.setSrc("shopping.hlhj.com.mylibrary.activity.TextDetailsActivity");
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
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tvTittleTextView.setText(detailDatas.title);
        thumb=detailDatas.getCover();
        tv_Time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm") + "");
        tv_auther.setText(detailDatas.release);
        String prompt = detailDatas.content;
        prompt=prompt.replace("<img", "<img style='max-width:100%;height:auto;'");
        webView.loadDataWithBaseURL(null, prompt, "text/html", "uft-8", null);
        title = detailDatas.title;

        collectPresenter.addHis(TMSharedPUtil.getTMUser(this).getMember_code(),
                TMSharedPUtil.getTMUser(this).getMember_id()+"",title,Constant.APP_ID,id+"",extendStr,""
                ,"1","",TMSharedPUtil.getTMToken(this));
    }

    @Override
    public void loadFailed(String msg) {
        if (msg.equals("评论失败")){
            loginDialog.show();
        }
    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas) {

    }

    @Override
    public void loadCommentSuccess(@Nullable List<CommentBean.CommentData> commentData) {
        if (commentData==null){
            return;
        }
        if (page == 1){
            commentDataList.clear();
            commentDataList.addAll(commentData);
        }else {
            commentDataList.addAll(commentData);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadSendCommentSuccess(String msg) {
        etContent.setText("");
        Toast.makeText(TextDetailsActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
        getPresenter().loadHotCommentData(TextDetailsActivity.this, id, 1);
    }

    @Override
    public void hasCollected(@NotNull CollBean collBean) {
        //Todo  已收藏，让按钮变成已收藏样式
        btColl.setImageResource(R.drawable.ic_collection);
        collectflag = false;
        cid = collBean.getData().getStar_id();
    }

    @Override
    public void notCollected() {
        //Todo  未收藏，让按钮变成未收藏样式
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
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
    public void addCollectError(@NotNull Exception e) {
        //todo 收藏接口访问失败回调
        loginDialog.show();
        btColl.setImageResource(R.drawable.ic_sc_normal);
        collectflag = true;
    }

    @Override
    public void addCollectError() {
        //todo 收藏接口访问失败回调
        loginDialog.show();
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

    @Override
    public void needLogin() {
        loginDialog.show();
    }
}
