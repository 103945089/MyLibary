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

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tenma.ventures.bean.TMUser;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.SearchActivity;
import shopping.hlhj.com.mylibrary.adapter.IndexAdapter;
import shopping.hlhj.com.mylibrary.bean.CatBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class FragmentIndex extends Fragment{

    private TabLayout tabLayouts;
    private ViewPager mainviewPager;
    private ImageView img_search;
    private Context context;
    private View rootView,loDv;
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
        //模拟数据
/*        TMUser tmUser = new TMUser();
        tmUser.setMember_id(69);
        tmUser.setMember_code("A930AD3C0ADB2430E234F096DBCFA357");
        tmUser.setToken("19105607C568A5597ACC3B856921F5DF");
        TMSharedPUtil.saveTMUser(getContext(),tmUser);
        TMSharedPUtil.saveTMToken(getContext(),"19105607C568A5597ACC3B856921F5DF");
        TMSharedPUtil.saveTMThemeColor(getContext(),"#f20909");*/

        tabLayouts = rootView.findViewById(R.id.main_tab);
        loDv=rootView.findViewById(R.id.loDv);
        mainviewPager = rootView.findViewById(R.id.main_viewpager);
        img_search = rootView.findViewById(R.id.img_search);
        rl_fragment_index = rootView.findViewById(R.id.rl_fragment_index);

        if (null != TMSharedPUtil.getTMThemeColor(context)){
            tabLayouts.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
            loDv.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
            rl_fragment_index.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
        }
        tabLayouts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                GSYVideoManager.releaseAllVideos();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        final IndexAdapter adapter = new IndexAdapter(getChildFragmentManager());

        OkGo.<String>get(Constant.CATLIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        CatBean catBean = new Gson().fromJson(body, CatBean.class);
                        for (int i = 0; i < catBean.getData().size(); i++) {
                            CatFgm catFgm = CatFgm.getInstance(catBean.getData().get(i).getId(), catBean.getData().get(i).getNav_name());
                            if (i==0){
                                adapter.addFragment(new FragmentIndexChoice(),catBean.getData().get(i).getNav_name());
                            }else {
                                adapter.addFragment(catFgm,catBean.getData().get(i).getNav_name());
                            }
                            mainviewPager.setAdapter(adapter);

                        }
                    }
                });

        tabLayouts.setupWithViewPager(mainviewPager);
        tabLayouts.setTabMode(TabLayout.MODE_FIXED);
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
