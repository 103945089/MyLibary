package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
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

import org.apache.cordova.LOG;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.ArticleAdapter;
import shopping.hlhj.com.mylibrary.adapter.HotVideoAdapter;
import shopping.hlhj.com.mylibrary.bean.ArticleBean;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.ExtendBean;
import shopping.hlhj.com.mylibrary.bean.ParamsBean;
import shopping.hlhj.com.mylibrary.fragment.FragmentIndexChoice;
import shopping.hlhj.com.mylibrary.presenter.ArticlePresenter;
import shopping.hlhj.com.mylibrary.presenter.CollectPresenter;

public class ArticleDetailActivity extends BaseActivity<ArticlePresenter> implements ArticlePresenter.ArticleDetailView, CollectPresenter.CollectView {

    private int id;
    private ImageView btBack,btSend,btColl,btGoShare;
    private EditText etContent;
    private TextView numCurrent,tvTittleTextView,tv_Time,tv_auther,tv_1,btMore,tv_article_detail_jianjie,tv_article_detail_conetent;
    private WebView webView;
    private RecyclerView comment_list;
    private GridView gridArticle;
    private ImageView imgArticle;
    private ArticleAdapter articleAdapter;
    private CollectPresenter collectPresenter;
    private String extendStr;
    @Override
    protected int getContentResId() {
        return R.layout.activity_article_detail;
    }

    @Override
    protected void beforeinit() {
        id = getIntent().getExtras().getInt("id");
    }

    @Override
    protected void initView() {
//        btBack = findViewById(R.id.btBack);
//        btSend = findViewById(R.id.btSend);
//        btColl = findViewById(R.id.btColl);
//        btGoShare = findViewById(R.id.btGoShare);
//        etContent = findViewById(R.id.etContent);
//        numCurrent = findViewById(R.id.numCurrent);
//        tvTittleTextView = findViewById(R.id.tvTittleTextView);
//        tv_Time = findViewById(R.id.tv_Time);
//        tv_auther = findViewById(R.id.tv_auther);
//        tv_1 = findViewById(R.id.tv_1);
//        btMore = findViewById(R.id.btMore);
//        webView = findViewById(R.id.webView);
//        comment_list = findViewById(R.id.comment_list);
        tv_article_detail_jianjie = findViewById(R.id.tv_article_detail_jianjie);
        tv_article_detail_conetent = findViewById(R.id.tv_article_detail_conetent);
        gridArticle = findViewById(R.id.grid_article);
        imgArticle = findViewById(R.id.img_article);

        //todo ExtenStr的配置
        ParamsBean paramsBean = new ParamsBean();
        paramsBean.setID(id);
        ExtendBean extendBean = new ExtendBean();
        ExtendBean.AndroidInfoBean androidInfoBean = new ExtendBean.AndroidInfoBean();
        ExtendBean.IosInfoBean iosInfoBean = new ExtendBean.IosInfoBean();
        androidInfoBean.setNativeX(true);
        androidInfoBean.setParamStr(new Gson().toJson(paramsBean));
        androidInfoBean.setSrc("shopping.hlhj.com.mylibrary.activity.LiveNewsActivity");
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
