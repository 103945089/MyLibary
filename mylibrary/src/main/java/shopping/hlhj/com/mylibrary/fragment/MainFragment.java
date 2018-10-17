package shopping.hlhj.com.mylibrary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.R;

public class MainFragment extends BaseFragment {

    private View view;
    private RadioGroup radioGroup;
    private RadioButton radioButtonIndex,radioButtonLive,radioButtonBoom;
    private List<Fragment> fragments = new ArrayList<>();
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private int mIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main,null);
        initView();
        initData();
        return view;
    }


    private void initView() {
        radioGroup = view.findViewById(R.id.radGrp_select);
        radioButtonIndex = view.findViewById(R.id.radBtn_index);
        radioButtonBoom = view.findViewById(R.id.radBtn_boom);
        radioButtonLive = view.findViewById(R.id.radBtn_live);

//        TMUser tmUser = new TMUser();
//        tmUser.setToken("BBF236B7F379AEEEC451DC58ED60AB8B");
//        tmUser.setToken("734D698E91313C951560891C3CF1ECD1");
//        tmUser.setMember_code("1D0916EF9A29336083BFB0017C90EAEA");
//        tmUser.setMember_id(63);
//        TMSharedPUtil.saveTMUser(getContext(),tmUser);
    }

    private void initData() {
        fragments = getFrament();
        fm = getFragmentManager();
        transaction = fm.beginTransaction();
        fragment = fragments.get(0);
        radioButtonIndex.setChecked(true);
        //默认布局
        transaction.replace(R.id.flayout_content,fragment);
        transaction.commit();
        setOnClick();
    }


    private void changeUi(int i) {
        if (mIndex == i){
            return;
        }
        fm = getFragmentManager();
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

}
