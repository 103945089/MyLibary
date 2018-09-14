package shopping.hlhj.com.mylibrary.activity;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.render.view.GSYVideoGLView;
import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;
import shopping.hlhj.com.mylibrary.presenter.LiveNewsPresenter;

public class HotVideoActivity extends BaseActivity<LiveNewsPresenter> implements LiveNewsPresenter.LiveNewsView{
    private GSYSampleADVideoPlayer videoPlayer;
    private TextView tv_title,tv_time,tv_type;
    private ListView list_comment;
    private EditText et_Content;
    private ImageView btSend;
    private int hotvideId;

    @Override
    protected int getContentResId() {
        return R.layout.activity_hotvideodetail;
    }

    @Override
    protected void beforeinit() {
        hotvideId = getIntent().getExtras().getInt("id");
    }

    @Override
    protected void initView() {
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_type = findViewById(R.id.tv_type);
        list_comment = findViewById(R.id.list_comment);
        et_Content = findViewById(R.id.et_Content);
        btSend = findViewById(R.id.btSend);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnClick() {

    }

    @Override
    public void loadSuccess(DetailBean.DetailDatas detailDatas) {
        tv_title.setText(detailDatas.title);
        tv_time.setText(detailDatas.create_time);
        tv_title.setText(detailDatas.title);
    }

    @Override
    public void loadCommentSuccess(List<DetailBean.DetailDatas.CommentBean> commentBeans) {

    }

    @Override
    public void loadFailed(String msg) {

    }
}
