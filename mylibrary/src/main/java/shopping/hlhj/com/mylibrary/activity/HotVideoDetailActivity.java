package shopping.hlhj.com.mylibrary.activity;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.video.GSYSampleADVideoPlayer;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.DanmakuVDPlayer;

public class HotVideoDetailActivity extends BaseActivity {

    private GSYSampleADVideoPlayer vdPlayer;
    private EditText etContent;
    private ListView listView;
    private TextView tv_title, tv_time, tv_type;
    private String title, videoUrl;
    private String creatTime, type;

    @Override
    protected int getContentResId() {
        return R.layout.activity_hotvideodetail;
    }

    @Override
    protected void beforeinit() {
        title = getIntent().getStringExtra("title");
        videoUrl = getIntent().getStringExtra("video_url");
        creatTime = getIntent().getStringExtra("create_time");
        type = getIntent().getStringExtra("type");
    }

    @Override
    protected void initView() {
//        vdPlayer = findViewById(R.id.hot_gsyvideo);
        etContent = findViewById(R.id.et_Content);
        listView = findViewById(R.id.list_comment);
        tv_title = findViewById(R.id.tv_titel);
        tv_time = findViewById(R.id.tv_time);
        tv_type = findViewById(R.id.tv_type);
    }

    @Override
    protected void initData(){
    }

    @Override
    protected void setOnClick() {


    }
}
