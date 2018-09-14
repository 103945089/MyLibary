package shopping.hlhj.com.mylibrary.Tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.ui.widget.DanmakuView;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.DanamakuAdapter;
import shopping.hlhj.com.mylibrary.bean.DanMuBean;


/**
 * Created by guoshuyu on 2017/2/16.
 * <p>
 * 配置弹幕使用的播放器，目前使用的是本地模拟数据。
 * <p>
 * 模拟数据的弹幕时常比较短，后面的时长点是没有数据的。
 * <p>
 * 注意：这只是一个例子，演示如何集合弹幕，需要完善如弹出输入弹幕等的，可以自行完善。
 * 注意：b站的弹幕so只有v5 v7 x86、没有64，所以记得配置上ndk过滤。
 */

public class DanmakuVDPlayer extends StandardGSYVideoPlayer {

    private BaseDanmakuParser mParser;//解析器对象
    private IDanmakuView mDanmakuView;//弹幕view
    private DanmakuContext mDanmakuContext;

    public OnEditClickListener onEditClickListener;
    private boolean isLay=false;
    private boolean isEditShow=false;

    private TextView mSendDanmaku, mToogleDanmaku;
    private ImageView btSwitch;
    private ImageView btEdit;
    public ImageView btnFull;
    private long mDanmakuStartSeekPosition = -1;
    int a = 1;
    private EditText editText;
    private RelativeLayout loEdit;
    private TextView btSend;
    private LinearLayout layoutRight ;
    private boolean mDanmaKuShow = true;

    private File mDumakuFile;

    public DanmakuVDPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public DanmakuVDPlayer(Context context) {
        super(context);
    }

    public DanmakuVDPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.danmaku_layout;
    }

    public ImageView getBtEdit() {
        return btEdit;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void init(Context context) {
        super.init(context);
        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        mSendDanmaku = (TextView) findViewById(R.id.send_danmaku);
        mToogleDanmaku = (TextView) findViewById(R.id.toogle_danmaku);
        btSwitch = (ImageView) findViewById(R.id.btSwitch);
        btEdit = (ImageView) findViewById(R.id.btEdit);
        ImageView btFull = (ImageView) findViewById(R.id.fullscreen);
        btnFull = (ImageView) findViewById(R.id.btFull);
        btFull.setVisibility(View.GONE);
        editText=findViewById(R.id.etDanMu);
        loEdit=findViewById(R.id.loEdit);
        btSend=findViewById(R.id.btSend);
        layoutRight=findViewById(R.id.layout_right);
        btSwitch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    mDanmaKuShow = !mDanmaKuShow;
                    resolveDanmakuShow();
                    Log.e("fhp","点击"+a);
                    a++;
                }
                return true;
            }
        });
        btEdit.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    if (true){
                        isEditShow = !isEditShow;
                        resoveEdit(isEditShow);
                    }else {
                        if (onEditClickListener!=null){
                            onEditClickListener.onEditClick();
                        }
                    }
                }

                return true;
            }
        });
        btSend.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    if (onEditClickListener!=null){
                        onEditClickListener.sendDanMu(editText.getText().toString());
                        addDanmaku(editText.getText().toString(),true);
                        editText.setText("");
                        loEdit.setVisibility(View.GONE);
                        isEditShow=false;

                    }
                }
                return true;
            }
        });
        btnFull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEND){
                    Log.e("fhp","是否为空"+(onEditClickListener==null));
                    ((InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(editText.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                    if (onEditClickListener!=null){
                        onEditClickListener.sendDanMu(editText.getText().toString());
                        addDanmaku(editText.getText().toString(),true);
                        editText.setText("");
                        isEditShow=false;
                        loEdit.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });
        //初始化弹幕显示
        initDanmaku();

        mSendDanmaku.setOnClickListener(this);
        mToogleDanmaku.setOnClickListener(this);


    }

    public void setOnEditClickListener(OnEditClickListener onEditClickListener) {
        this.onEditClickListener = onEditClickListener;
    }

    public void setLay(boolean lay) {
        isLay = lay;
    }

    @Override
    protected void hideAllWidget() {
        Log.e("fhpp","hideAllWidget");
        layoutRight.setVisibility(View.GONE);
        super.hideAllWidget();
    }

/*    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int left = layout_right.getLeft();

        Log.e("fhp","点击位置"+event.getRawX()+"---"+event.getRawY());
        Log.e("fhp","左边"+left);
        Log.e("fhp","点了点了"+event.toString()+"----坐标"+(left+btSwitch.getLeft())+"-"+(left+btSwitch.getLeft()+btSwitch.getWidth())+"--"+btSwitch.getTop()+"-"+(btSwitch.getTop()+btSwitch.getHeight()));
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            if (event.getRawX()>=left+btSwitch.getLeft()&&

                    event.getRawX()<=(left+btSwitch.getLeft()+btSwitch.getWidth())
                    &&event.getY()>=btSwitch.getTop()
                    &&event.getY()<=(btSwitch.getTop()+btSwitch.getHeight())){
                Toast.makeText(getContext(),"321321",Toast.LENGTH_LONG).show();
                mDanmaKuShow = !mDanmaKuShow;
                resolveDanmakuShow();
            }else
            if (event.getRawX()>=left+btEdit.getLeft()&&

                    event.getRawX()<=(left+btEdit.getLeft()+btEdit.getWidth())
                    &&event.getY()>=btEdit.getTop()
                    &&event.getY()<=(btEdit.getTop()+btEdit.getHeight())){
                isEditShow = !isEditShow;
                resoveEdit(isEditShow);
            }else {
                super.onTouch(v, event);
            }
        }
        return true;

    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void resoveEdit(boolean b){
        if (b){
            loEdit.setVisibility(View.VISIBLE);
/*            InputMethodManager inputManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(editText,0);*/
        }else {
            loEdit.setVisibility(View.GONE);
/*            InputMethodManager inputManager = (InputMethodManager)editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(),0);*/
        }
    }
    @Override
    public void onPrepared() {
        super.onPrepared();
        onPrepareDanmaku(this);
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.pause();
        }
    }

    @Override
    public void onVideoResume() {
        super.onVideoResume();
        if (mDanmakuView != null && mDanmakuView.isPrepared() && mDanmakuView.isPaused()) {
            mDanmakuView.resume();
        }
    }


    @Override
    public void onCompletion() {
        releaseDanmaku(this);
    }


    @Override
    public void onSeekComplete() {
        super.onSeekComplete();
        int time = mProgressBar.getProgress() * getDuration() / 100;
        //如果已经初始化过的，直接seek到对于位置
        if (mHadPlay && getDanmakuView() != null && getDanmakuView().isPrepared()) {
            resolveDanmakuSeek(this, time);
        } else if (mHadPlay && getDanmakuView() != null && !getDanmakuView().isPrepared()) {
            //如果没有初始化过的，记录位置等待
            setDanmakuStartSeekPosition(time);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.send_danmaku) {
            addDanmaku(true);

        } else if (i == R.id.btSwitch||i==R.id.toogle_danmaku) {
            Toast.makeText(getContext(),"321321",Toast.LENGTH_LONG).show();
            mDanmaKuShow = !mDanmaKuShow;
            resolveDanmakuShow();

        }
    }

    @Override
    protected void cloneParams(GSYBaseVideoPlayer from, GSYBaseVideoPlayer to) {
        ((DanmakuVDPlayer)to).mDumakuFile = ((DanmakuVDPlayer)from).mDumakuFile;
        super.cloneParams(from, to);
    }

    /**
     * 处理播放器在全屏切换时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer != null) {
            DanmakuVDPlayer gsyVideoPlayer = (DanmakuVDPlayer) gsyBaseVideoPlayer;
            //对弹幕设置偏移记录
            gsyVideoPlayer.setDanmakuStartSeekPosition(getCurrentPositionWhenPlaying());
            gsyVideoPlayer.setDanmaKuShow(getDanmaKuShow());
            onPrepareDanmaku(gsyVideoPlayer);
        }
        return gsyBaseVideoPlayer;
    }

    /**
     * 处理播放器在退出全屏时，弹幕显示的逻辑
     * 需要格外注意的是，因为全屏和小屏，是切换了播放器，所以需要同步之间的弹幕状态
     */
    @Override
    protected void resolveNormalVideoShow(View oldF, ViewGroup vp, GSYVideoPlayer gsyVideoPlayer) {
        super.resolveNormalVideoShow(oldF, vp, gsyVideoPlayer);
        if (gsyVideoPlayer != null) {
            DanmakuVDPlayer gsyDanmaVideoPlayer = (DanmakuVDPlayer) gsyVideoPlayer;
            setDanmaKuShow(gsyDanmaVideoPlayer.getDanmaKuShow());
            if (gsyDanmaVideoPlayer.getDanmakuView() != null &&
                    gsyDanmaVideoPlayer.getDanmakuView().isPrepared()) {
                resolveDanmakuSeek(this, gsyDanmaVideoPlayer.getCurrentPositionWhenPlaying());
                resolveDanmakuShow();
                releaseDanmaku(gsyDanmaVideoPlayer);
            }
        }
    }

    public void setDanmaKuStream(File is) {
        mDumakuFile = is;
        if(!getDanmakuView().isPrepared()) {
            onPrepareDanmaku((DanmakuVDPlayer)getCurrentPlayer());
        }
    }
    private void doLayout(){
        if (layoutRight.getVisibility()==View.GONE){
            layoutRight.setVisibility(View.VISIBLE);
        }else {
            layoutRight.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onClickUiToggle() {
        super.onClickUiToggle();

    }

    @Override
    protected void changeUiToPrepareingClear() {
        super.changeUiToPrepareingClear();
        Log.e("fhpp","changeUiToPrepareingClear");
    }

    @Override
    protected void changeUiToClear() {
        Log.e("fhpp","changeUiToClear");
        super.changeUiToClear();

    }

    @Override
    protected void changeUiToPauseClear() {
        Log.e("fhpp","changeUiToPauseClear");
        super.changeUiToPauseClear();

    }

    @Override
    protected void changeUiToCompleteShow() {
        Log.e("fhpp","changeUiToCompleteShow");
        super.changeUiToCompleteShow();
    }

    @Override
    protected void changeUiToCompleteClear() {
        Log.e("fhpp","changeUiToCompleteClear");
        super.changeUiToCompleteClear();
    }

    @Override
    protected void changeUiToPauseShow() {
        Log.e("fhpp","changeUiToPauseShow");
        super.changeUiToPauseShow();

    }

    @Override
    protected void changeUiToPlayingBufferingClear() {
        Log.e("fhpp","changeUiToPlayingBufferingClear");
        super.changeUiToPlayingBufferingClear();
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
        Log.e("fhpp","changeUiToPlayingBufferingShow");
        super.changeUiToPlayingBufferingShow();

    }

    @Override
    protected void changeUiToPreparingShow() {
        Log.e("fhpp","changeUiToPreparingShow");
        super.changeUiToPreparingShow();
        layoutRight.setVisibility(View.VISIBLE);

    }

    @Override
    protected void changeUiToPlayingClear() {
        layoutRight.setVisibility(View.GONE);
        Log.e("fhpp","changeUiToPlayingClear");
        super.changeUiToPlayingClear();
    }
    @Override
    protected void changeUiToPlayingShow() {
        layoutRight.setVisibility(View.VISIBLE);
        Log.e("fhpp","changeUiToPlayingShow");
        super.changeUiToPlayingShow();
    }

    @Override
    protected void resolveUIState(int state) {
        Log.e("fhpp","resolveUIState");

        super.resolveUIState(state);

    }

    private void initDanmaku() {
        // 设置最大显示行数
        HashMap<Integer, Integer> maxLinesPair = new HashMap<Integer, Integer>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL, 5); // 滚动弹幕最大显示5行
        // 设置是否禁止重叠
        HashMap<Integer, Boolean> overlappingEnablePair = new HashMap<Integer, Boolean>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_RL, true);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_TOP, true);

        DanamakuAdapter danamakuAdapter = new DanamakuAdapter(mDanmakuView);
        mDanmakuContext = DanmakuContext.create();
        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3).setDuplicateMergingEnabled(false).setScrollSpeedFactor(1.2f).setScaleTextSize(1.2f)
                .setCacheStuffer(new SpannedCacheStuffer(), danamakuAdapter) // 图文混排使用SpannedCacheStuffer
                .setMaximumLines(maxLinesPair)
                .preventOverlapping(overlappingEnablePair);
        if (mDanmakuView != null) {
            if(mDumakuFile != null) {
                mParser = createParser(getIsStream(mDumakuFile));
            }

            //todo 这是为了demo效果，实际上需要去掉这个，外部传输文件进来
            mParser = createParser(this.getResources().openRawResource(R.raw.comments));

            mDanmakuView.setCallback(new master.flame.danmaku.controller.DrawHandler.Callback() {
                @Override
                public void updateTimer(DanmakuTimer timer) {
                }

                @Override
                public void drawingFinished() {

                }

                @Override
                public void danmakuShown(BaseDanmaku danmaku) {
                }

                @Override
                public void prepared() {
                    if (getDanmakuView() != null) {
                        getDanmakuView().start();
                        if (getDanmakuStartSeekPosition() != -1) {
                            resolveDanmakuSeek(DanmakuVDPlayer.this, getDanmakuStartSeekPosition());
                            setDanmakuStartSeekPosition(-1);
                        }
                        resolveDanmakuShow();
                    }
                }
            });
            mDanmakuView.enableDanmakuDrawingCache(true);
        }
    }


    private InputStream getIsStream(File file) {
        try {
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            StringBuilder sb1=new StringBuilder();
            sb1.append("<i>");
            //分行读取
            while (( line = buffreader.readLine()) != null) {
                sb1.append(line);
            }
            sb1.append("</i>");
            Log.e("3333333",sb1.toString());
            instream.close();
            return new ByteArrayInputStream(sb1.toString().getBytes());
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", "The File doesn't not exist.");
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return null;
    }

    /**
     * 弹幕的显示与关闭
     */
    private void resolveDanmakuShow() {
        post(new Runnable() {
            @Override
            public void run() {
                if (mDanmaKuShow) {
                    if (!getDanmakuView().isShown()){
                        getDanmakuView().show();
                    }
                    btSwitch.setImageResource(R.drawable.ic_barrage_close);
                    mToogleDanmaku.setText("弹幕关");
                } else {
                    if (getDanmakuView().isShown()) {
                        getDanmakuView().hide();
                    }
                    mToogleDanmaku.setText("弹幕开");
                    btSwitch.setImageResource(R.drawable.ic_barrage_open);
                }
            }
        });
    }

    /**
     * 开始播放弹幕
     */
    private void onPrepareDanmaku(DanmakuVDPlayer gsyVideoPlayer) {
        if (gsyVideoPlayer.getDanmakuView() != null && !gsyVideoPlayer.getDanmakuView().isPrepared() && gsyVideoPlayer.getParser() != null) {
            gsyVideoPlayer.getDanmakuView().prepare(gsyVideoPlayer.getParser(),
                    gsyVideoPlayer.getDanmakuContext());
        }
    }

    /**
     * 弹幕偏移
     */
    private void resolveDanmakuSeek(DanmakuVDPlayer gsyVideoPlayer, long time) {
        //todo 因为Gsy更新了，API方法的变更
//        if (getGSYVideoManager().getMediaPlayer() != null && mHadPlay
        if (getGSYVideoManager() != null && mHadPlay

                && gsyVideoPlayer.getDanmakuView() != null && gsyVideoPlayer.getDanmakuView().isPrepared()) {
            gsyVideoPlayer.getDanmakuView().seekTo(time);
        }
    }

    /**
     * 创建解析器对象，解析输入流
     *
     * @param stream
     * @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    /**
     * 释放弹幕控件
     */
    private void releaseDanmaku(DanmakuVDPlayer danmakuVideoPlayer) {
        if (danmakuVideoPlayer != null && danmakuVideoPlayer.getDanmakuView() != null) {
            Debuger.printfError("release Danmaku!");
            danmakuVideoPlayer.getDanmakuView().release();
        }
    }

    public BaseDanmakuParser getParser() {
        if(mParser == null) {
            if (mDumakuFile != null) {
                mParser = createParser(getIsStream(mDumakuFile));
            }
        }
        return mParser;
    }

    public DanmakuContext getDanmakuContext() {
        return mDanmakuContext;
    }

    public IDanmakuView getDanmakuView() {
        return mDanmakuView;
    }

    public long getDanmakuStartSeekPosition() {
        return mDanmakuStartSeekPosition;
    }

    public void setDanmakuStartSeekPosition(long danmakuStartSeekPosition) {
        this.mDanmakuStartSeekPosition = danmakuStartSeekPosition;
    }

    public void setDanmaKuShow(boolean danmaKuShow) {
        mDanmaKuShow = danmaKuShow;
    }

    public boolean getDanmaKuShow() {
        return mDanmaKuShow;
    }

    /**
     * 模拟添加弹幕数据
     */
    public void addDanmaku(boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = "这是一条弹幕 " + getCurrentPositionWhenPlaying();
        danmaku.padding = 5;
        danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 500);
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE;
        danmaku.borderColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);

    }
    public void addDanmaku(String str,boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = str;
        danmaku.padding = 5;
        danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
        danmaku.isLive = islive;
        danmaku.setTime(mDanmakuView.getCurrentTime() + 500);
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = Color.WHITE;
        danmaku.borderColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);

    }
    public void addDanmu(DanMuBean danMuBean){
        List<DanMuBean.DataBean> dataList = danMuBean.getData();
        for (int i = 0; i < dataList.size() - 1; i++) {
            DanMuBean.DataBean bean = dataList.get(i);
            BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
            if (danmaku == null || mDanmakuView == null) {
                return;
            }
            danmaku.text=bean.getContent();
            danmaku.padding = 5;
            danmaku.priority = 8;  // 可能会被各种过滤器过滤并隐藏显示，所以提高等级
            danmaku.isLive = true;
            danmaku.setTime(mDanmakuView.getCurrentTime() + 5000*i);
            danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
            Random random = new Random();
            int i1 = random.nextInt(100);
            if (i1>0&&i1<=20){
                danmaku.textColor = Color.RED;
                danmaku.textShadowColor = Color.WHITE;
            }else if (i1>20&&i1<=40){
                danmaku.textColor = Color.WHITE;
                danmaku.textShadowColor = Color.WHITE;
            }else if (i1>40&&i1<=60){
                danmaku.textColor = Color.BLUE;
                danmaku.textShadowColor = Color.WHITE;
            }else if (i1>60&&i1<=80){
                danmaku.textColor = Color.YELLOW;
                danmaku.textShadowColor = Color.WHITE;
            }else if (i1>80&&i1<=100){
                danmaku.textColor = Color.GREEN;
                danmaku.textShadowColor = Color.WHITE;
            }
            mDanmakuView.addDanmaku(danmaku);
        }

    }
    public interface OnEditClickListener{
        void onEditClick();
        void sendDanMu(String str);
    }

}
