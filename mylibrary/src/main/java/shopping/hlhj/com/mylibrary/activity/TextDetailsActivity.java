package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.adapter.CommentAdapter;
import shopping.hlhj.com.mylibrary.adapter.TextCommentAdapter;
import shopping.hlhj.com.mylibrary.bean.CommentBean;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class TextDetailsActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView {

    private RelativeLayout loNewHead;
    private ImageView btBack,btSend,btColl,btGoShare;
    private EditText etContent;
    private TextView tvTittleTextView,tv_Time,tv_auther,btMore;
    private RecyclerView comment_list;
    private WebView webView;
    private int id,page = 1;
    private TextCommentAdapter commentAdapter;

    @Override
    protected int getContentResId() {
        return R.layout.aty_special_info_kankan;
    }

    @Override
    protected void beforeinit() {
        id = getIntent().getExtras().getInt("id");
    }

    @Override
    protected void initView() {
        loNewHead = findViewById(R.id.loNewHead);
        btBack = findViewById(R.id.btBack);
        btSend = findViewById(R.id.btSend);
        btColl = findViewById(R.id.btColl);
        btGoShare = findViewById(R.id.btGoShare);
        etContent = findViewById(R.id.etContent);
        tvTittleTextView = findViewById(R.id.tvTittleTextView);
        tv_Time = findViewById(R.id.tv_Time);
        tv_auther = findViewById(R.id.tv_auther);
        comment_list = findViewById(R.id.comment_list);
        btMore = findViewById(R.id.btMore);
        webView = findViewById(R.id.webView);
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        comment_list.setLayoutManager(manager);

        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this,id,0);
    }

    @Override
    protected void setOnClick() {
        btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page ++ ;
                getPresenter().loadHotCommentData(TextDetailsActivity.this,id,page);
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
                    Toast.makeText(TextDetailsActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TextDetailsActivity.this, ConfirmLoginActivity.class));
                    return;
                }
                if (null == string || "".equals(string) || TextUtils.isEmpty(string)) {
                    Toast.makeText(TextDetailsActivity.this, "请输入评论内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != string && !"".equals(string) && tmToken != null && !tmToken.equals("")) {
                    getPresenter().sendComment(TextDetailsActivity.this, id, string, tmToken);
                }
            }
        });
    }

    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tvTittleTextView.setText(detailDatas.title);
        tv_Time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time), "yyyy-MM-dd HH:mm") + "");
        tv_auther.setText(detailDatas.release);
        webView.loadDataWithBaseURL(null,detailDatas.content,"text/html","uft-8",null);
        commentAdapter = new TextCommentAdapter(TextDetailsActivity.this,detailDatas.comment);
        comment_list.setAdapter(commentAdapter);
    }

    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas) {

    }

    @Override
    public void loadCommentSuccess(List<CommentBean.CommentData> commentData) {

    }

    @Override
    public void loadSendCommentSuccess(String msg) {
        Toast.makeText(TextDetailsActivity.this,msg.toString(),Toast.LENGTH_SHORT).show();
    }
}
