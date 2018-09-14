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

import org.apache.cordova.LOG;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.ArticleAdapter;
import shopping.hlhj.com.mylibrary.adapter.HotVideoAdapter;
import shopping.hlhj.com.mylibrary.bean.ArticleBean;
import shopping.hlhj.com.mylibrary.fragment.FragmentIndexChoice;
import shopping.hlhj.com.mylibrary.presenter.ArticlePresenter;

public class ArticleDetailActivity extends BaseActivity<ArticlePresenter> implements ArticlePresenter.ArticleDetailView {

    private int id;
    private ImageView btBack,btSend,btColl,btGoShare;
    private EditText etContent;
    private TextView numCurrent,tvTittleTextView,tv_Time,tv_auther,tv_1,btMore,tv_article_detail_jianjie,tv_article_detail_conetent;
    private WebView webView;
    private RecyclerView comment_list;
    private GridView gridArticle;
    private ImageView imgArticle;
    private ArticleAdapter articleAdapter;

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
    }

    @Override
    protected void initData() {
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
}
