package shopping.hlhj.com.mylibrary.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;

import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.bean.DetailBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.presenter.HotVideoPresenter;

public class HotVideoDetailActivity extends BaseActivity<HotVideoPresenter> implements HotVideoPresenter.HotVideoView {

    private GSYSampleADVideoPlayer vdPlayer;
    private EditText etContent;
    private ListView listView;
    private TextView tv_title, tv_time, tv_author,tv_comment_normal;
    private ImageView img_btn;
    private int id;
    private String etString;

    @Override
    protected int getContentResId() {
        return R.layout.activity_hotvideodetail;
    }

    @Override
    protected void beforeinit() {
        id = getIntent().getExtras().getInt("id");
    }

    @Override
    protected void initView() {
//        vdPlayer = findViewById(R.id.hot_gsyvideo);
        etContent = findViewById(R.id.et_Content);
        listView = findViewById(R.id.list_comment);
        tv_title = findViewById(R.id.tv_title);
        tv_time = findViewById(R.id.tv_time);
        tv_author = findViewById(R.id.tv_author);
        tv_comment_normal = findViewById(R.id.tv_comment_normal);
        img_btn = findViewById(R.id.btSend);
    }

    @Override
    protected void initData(){
        setPresenter(new HotVideoPresenter(this));
        getPresenter().loadVideoData(this,id,0);
    }

    @Override
    protected void setOnClick() {
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etString = etContent.getText().toString();
                if (null != etString && !"".equals(etString)){

                }
            }
        });

    }

    @Override
    public void loadDataSuccess(DetailBean.DetailDatas detailDatas) {
        tv_title.setText(detailDatas.title);
        tv_time.setText(JavaUtils.StampstoTime(String.valueOf(detailDatas.create_time),"yyyy-MM-dd HH:mm"));
        tv_author.setText(detailDatas.release);
        List<DetailBean.DetailDatas.CommentBean> commentBeans = detailDatas.getCommentBeans();
        if (null == commentBeans || commentBeans.size() == 0){
            listView.setVisibility(View.GONE);
            tv_comment_normal.setVisibility(View.VISIBLE);
        }else {
            listView.setVisibility(View.VISIBLE);
            tv_comment_normal.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadFailed(String msg) {

    }

    @Override
    public void loadHotMoreSuccess(List<MoreBean.MoreDatas> MoreDatas) {

    }

}
