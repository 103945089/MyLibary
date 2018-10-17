package shopping.hlhj.com.mylibrary.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tenma.ventures.bean.TMUser;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.bean.UserBean;
import shopping.hlhj.com.mylibrary.fragment.FragmentBoom;
import shopping.hlhj.com.mylibrary.fragment.FragmentIndex;
import shopping.hlhj.com.mylibrary.fragment.FragmentLive;

public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButtonIndex,radioButtonLive,radioButtonBoom;
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private int mIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeinit() {
        TMUser tmUser = new TMUser();
        TMSharedPUtil.saveTMUser(this,tmUser);
    }

    @Override
    protected void initView() {
        radioGroup = findViewById(R.id.radGrp_select);
        radioButtonIndex = findViewById(R.id.radBtn_index);
        radioButtonBoom = findViewById(R.id.radBtn_boom);
        radioButtonLive = findViewById(R.id.radBtn_live);
    }

    private void changeUi(int i) {
        if (mIndex == i){
            return;
        }
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        //隐藏上一个界面
        transaction.hide(fragments.get(mIndex));
        //判断是否添加界面
        if (!fragments.get(i).isAdded()){
            transaction.add(R.id.flayout_content,fragments.get(i)).show(fragments.get(i));
        }else {
            transaction.show(fragments.get(i));
        }
        transaction.commit();
        mIndex = i;
    }


    @Override
    protected void initData() {
        fragments = getFrament();
        fm = getSupportFragmentManager();
        transaction = fm.beginTransaction();
        fragment = fragments.get(0);
        radioButtonIndex.setChecked(true);
        //默认布局
        transaction.replace(R.id.flayout_content,fragment);
        transaction.commit();
    }

    @Override
    protected void setOnClick() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radBtn_index){
                    changeUi(0);
                }
                if (checkedId == R.id.radBtn_live){
                    changeUi(1);
                }
                if (checkedId == R.id.radBtn_boom){
                    changeUi(2);
                }
            }
        });
    }

    public List<Fragment> getFrament() {
        fragments.add(new FragmentIndex());
        fragments.add(new FragmentLive());
        fragments.add(new FragmentBoom());
        return fragments;
    }

//    private long currentTime;
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            exit();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void exit() {
//        if ((System.currentTimeMillis() - currentTime) > 2000){
//            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
//            currentTime = System.currentTimeMillis();
//        }else {
//            finish();
//            ActivityCollector.finishAll();
//            System.exit(0);
//        }
//    }
}
