package shopping.hlhj.com.mylibrary;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity<T extends BasePresenter<? extends BaseView>> extends AppCompatActivity implements BaseView{
    protected Context mContext;
    private List<String> mListPermissions = new ArrayList<>();
    private PermissionsResultListener mListener;
    private int mRequestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityCollector.addActivity(this);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        super.onCreate(savedInstanceState);
        initWidows();
        mContext = this;
        ButterKnife.bind(this);
        setContentView(getContentResId());
        beforeinit();
        initView();
        initData();
        setOnClick();
    }

    protected abstract int getContentResId();

    protected abstract void beforeinit();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void setOnClick();

    protected void initWidows(){

    }

    private T presenter;

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    protected void checkPermissions(String[] permissions, int requestCode, PermissionsResultListener listener) {
        //权限不能为空
        if (permissions != null || permissions.length != 0) {
            mListener = listener;
            mRequestCode = requestCode;
            for (int i = 0; i < permissions.length; i++) {
                if (!isHavePermissions(permissions[i])) {
                    mListPermissions.add(permissions[i]);
                }
            }
            //遍历完后申请
            applyPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (grantResults.length > 0) {
                mListener.onSuccessful(grantResults);
            } else {
                mListener.onFailure();
            }
        }
    }

    //判断权限是否申请
    private boolean isHavePermissions(String permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    //申请权限
    private void applyPermissions() {
        if (!mListPermissions.isEmpty()) {
            int size = mListPermissions.size();
            ActivityCompat.requestPermissions(this, mListPermissions.toArray(new String[size]), mRequestCode);
        }
    }
    @Override
    protected void onDestroy() {
        if (presenter != null){
            presenter.recycle();
        }
        super.onDestroy();
        ActivityCollector.removieActivity(this);
    }
}
