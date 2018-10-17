package shopping.hlhj.com.mylibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tenma.ventures.bean.utils.TMSharedPUtil;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.SearchActivity;
import shopping.hlhj.com.mylibrary.adapter.IndexAdapter;

public class FragmentIndex extends Fragment{

    private TabLayout tabLayouts;
    private ViewPager mainviewPager;
    private ImageView img_search;
    private Context context;
    private View rootView;
    private RelativeLayout rl_fragment_index;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_index,null);
        context = getActivity();
        initView();
        initData();
        return rootView;
    }

    private void initView() {
        tabLayouts = rootView.findViewById(R.id.main_tab);
        mainviewPager = rootView.findViewById(R.id.main_viewpager);
        img_search = rootView.findViewById(R.id.img_search);
        rl_fragment_index = rootView.findViewById(R.id.rl_fragment_index);
        if (null != TMSharedPUtil.getTMThemeColor(context)){
            tabLayouts.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
            rl_fragment_index.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
        }
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
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SearchActivity.class));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
