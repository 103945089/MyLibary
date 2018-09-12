package shopping.hlhj.com.mylibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.IndexAdapter;

public class FragmentIndex extends Fragment{

    private TabLayout tabLayouts;
    private ViewPager mainviewPager;
    private Context context;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_index,null);
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        tabLayouts = rootView.findViewById(R.id.main_tab);
        mainviewPager = rootView.findViewById(R.id.main_viewpager);
        tabLayouts.setupWithViewPager(mainviewPager);
        tabLayouts.setTabMode(TabLayout.MODE_FIXED);
        IndexAdapter adapter = new IndexAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentIndexChoice(),"精选");
        adapter.addFragment(new FragmentIndexSports(),"体育");
        adapter.addFragment(new FragmentIndexScience(),"科技");
        mainviewPager.setAdapter(adapter);
        mainviewPager.setOffscreenPageLimit(2);
    }

    private void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
