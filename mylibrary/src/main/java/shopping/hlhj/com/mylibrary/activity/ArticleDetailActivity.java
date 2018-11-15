package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import org.apache.cordova.LOG;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.ArticleAdapter;
import shopping.hlhj.com.mylibrary.bean.ArticleBean;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.ExtendBean;
import shopping.hlhj.com.mylibrary.bean.ParamsBean;
import shopping.hlhj.com.mylibrary.presenter.ArticlePresenter;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;

public class ArticleDetailActivity extends BaseActivity<ArticlePresenter> implements ArticlePresenter.ArticleDetailView, CollectPresenter.CollectView {

    private int id;
    private TextView tv_article_detail_jianjie,tv_article_detail_conetent;
    private GridView gridArticle;
    private ImageView imgArticle;
    private ArticleAdapter articleAdapter;
    private CollectPresenter collectPresenter;
    private String extendStr;
    private View btExit,loBack;
    @Override
    protected int getContentResId() {
        return R.layout.activity_article_detail;
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
        tv_article_detail_jianjie = findViewById(R.id.tv_article_detail_jianjie);
        tv_article_detail_conetent = findViewById(R.id.tv_article_detail_conetent);
        gridArticle = findViewById(R.id.grid_article);
        loBack=findViewById(R.id.loBack);
        imgArticle = findViewById(R.id.img_article);
        btExit=findViewById(R.id.btExit);


        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
        }

        //todo ExtenStr的配置
        ParamsBean paramsBean = new ParamsBean();
        paramsBean.setID(id);
        ExtendBean extendBean = new ExtendBean();
        ExtendBean.AndroidInfoBean androidInfoBean = new ExtendBean.AndroidInfoBean();
        ExtendBean.IosInfoBean iosInfoBean = new ExtendBean.IosInfoBean();
        androidInfoBean.setNativeX(true);
        androidInfoBean.setParamStr(new Gson().toJson(paramsBean));
        androidInfoBean.setSrc("shopping.hlhj.com.mylibrary.activity.ArticleDetailActivity");
        androidInfoBean.setWwwFolder("");

        iosInfoBean.setNativeX(true);
        iosInfoBean.setParamStr(new Gson().toJson(paramsBean));
        iosInfoBean.setSrc("");
        androidInfoBean.setWwwFolder("");

        extendBean.setAndroidInfo(androidInfoBean);
        extendBean.setIosInfo(iosInfoBean);

        extendStr=new Gson().toJson(extendBean);
    }

    @Override
    protected void initData() {
        collectPresenter=new CollectPresenter(this);
        setPresenter(new ArticlePresenter(this));
        getPresenter().loadArticleDetailData(this,id);



    }

    @Override
    protected void setOnClick() {
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadArticleDataSuccess(ArticleBean.ArticleDatas articleDatas) {
        Glide.with(this).load(articleDatas.channel_thumb).into(imgArticle);
        tv_article_detail_conetent.setText("\u3000\u3000\u3000\u3000  " + articleDatas.summary);
    }

    @Override
    public void loadDetailDataSuccess(List<ArticleBean.ArticleDatas.ArticleDetailBean> articleDetailBeans) {
        articleAdapter = new ArticleAdapter(this,articleDetailBeans);
        gridArticle.setAdapter(articleAdapter);
    }

    @Override
    public void loadFailed(String msg) {

    }

    //todo 下面都是收藏的回调

    /**
     * 已收藏
     * @param collBean
     */
    @Override
    public void hasCollected(@NotNull CollBean collBean) {

    }

    /**
     * 未收藏
     */
    @Override
    public void notCollected() {

    }

    /***
     * 添加收藏成功
     * @param collBean
     */
    @Override
    public void addCollect(@NotNull CollBean collBean) {

    }

    /**
     * 添加收藏失败
     * @param e
     */
    @Override
    public void addCollectError(@NotNull Exception e) {

    }
    /**
     * 添加收藏失败
     * @param
     */
    @Override
    public void addCollectError() {

    }

    /**
     * 取消收藏成功
     */
    @Override
    public void cancelCollect() {

    }

    /**
     * 取消收藏失败
     */
    @Override
    public void cancelCollectErro() {

    }
}
