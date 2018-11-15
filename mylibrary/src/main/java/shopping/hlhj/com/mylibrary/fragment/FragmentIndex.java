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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.SearchActivity;
import shopping.hlhj.com.mylibrary.adapter.CatLeftAdp;
import shopping.hlhj.com.mylibrary.adapter.IndexAdapter;
import shopping.hlhj.com.mylibrary.bean.CatBean;
import shopping.hlhj.com.mylibrary.data.Constant;

public class FragmentIndex extends Fragment{

    private TabLayout tabLayouts;
    private ViewPager mainviewPager;
    private ImageView img_search;
    private Context context;
    private View rootView,btMore;
    private RelativeLayout rl_fragment_index,loright;
    private CatLeftAdp catLeftAdp;
    private RecyclerView catLeftList;
    private List<CatBean.DataBean> catLeftDatas=new ArrayList<>();
    private DrawerLayout loDraw;
    private View loleft,loDv;
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
        tmUser.setMember_id(100);
        tmUser.setMember_code("6E84443FA6503960355291508B146FDD");
        tmUser.setToken("83B8BCE6B4977AD65C30533773CE425D");
        TMSharedPUtil.saveTMUser(getContext(),tmUser);
        TMSharedPUtil.saveTMToken(getContext(),"83B8BCE6B4977AD65C30533773CE425D");
        TMSharedPUtil.saveTMThemeColor(getContext(),"#000000");*/

        tabLayouts = rootView.findViewById(R.id.main_tab);
        loDv=rootView.findViewById(R.id.loDv);
        mainviewPager = rootView.findViewById(R.id.main_viewpager);
        img_search = rootView.findViewById(R.id.img_search);
        rl_fragment_index = rootView.findViewById(R.id.rl_fragment_index);
        loright=rootView.findViewById(R.id.loleft);
        btMore=rootView.findViewById(R.id.btMore);
        catLeftList=rootView.findViewById(R.id.catList);
        loDraw=rootView.findViewById(R.id.loDraw);
        loleft=rootView.findViewById(R.id.loleft);
        loDv=rootView.findViewById(R.id.loDv);
        if (loDv!=null){
            if (loDv!=null){
                ViewGroup.LayoutParams layoutParams = loDv.getLayoutParams();
                layoutParams.height=getHeight();
                loDv.setLayoutParams(layoutParams);
            }
        }


        if (null != TMSharedPUtil.getTMThemeColor(context)){
            tabLayouts.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
            loDv.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
            rl_fragment_index.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(context)));
        }
        catLeftAdp=new CatLeftAdp(catLeftDatas);
        catLeftList.setAdapter(catLeftAdp);
        catLeftList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


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
                        if (catBean.getData().size()<=4){
                            if (catBean.getData().size()<=3){
                                tabLayouts.setTabMode(TabLayout.MODE_FIXED);
                            }else {
                                tabLayouts.setTabMode(TabLayout.MODE_SCROLLABLE);

                            }
                            btMore.setVisibility(View.GONE);
                            for (int i = 0; i < catBean.getData().size(); i++) {
                                CatFgm catFgm = CatFgm.getInstance(catBean.getData().get(i).getId(), catBean.getData().get(i).getNav_name());
                                if (i==0){
                                    adapter.addFragment(new FragmentIndexChoice(),catBean.getData().get(i).getNav_name());
                                }else {
                                    adapter.addFragment(catFgm,catBean.getData().get(i).getNav_name());
                                }
                                mainviewPager.setAdapter(adapter);

                            }
                        }else {
                            tabLayouts.setTabMode(TabLayout.MODE_SCROLLABLE);
                            btMore.setVisibility(View.VISIBLE);
                            for (int i = 0; i < 4; i++) {
                                CatFgm catFgm = CatFgm.getInstance(catBean.getData().get(i).getId(), catBean.getData().get(i).getNav_name());
                                if (i==0){
                                    adapter.addFragment(new FragmentIndexChoice(),catBean.getData().get(i).getNav_name());
                                }else {
                                    adapter.addFragment(catFgm,catBean.getData().get(i).getNav_name());
                                }
                                mainviewPager.setAdapter(adapter);
                            }

                            for (int i = 0; i < catBean.getData().size(); i++) {
                                if (i>3){
                                    catLeftDatas.add(catBean.getData().get(i));
                                }
                            }
                            catLeftAdp.notifyDataSetChanged();

                        }

                    }
                });

        tabLayouts.setupWithViewPager(mainviewPager);
        tabLayouts.setTabMode(TabLayout.MODE_SCROLLABLE);
        mainviewPager.setOffscreenPageLimit(5);

    }

    private void initData() {
        btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loDraw.isDrawerOpen(loleft)){
                    loDraw.closeDrawer(loleft);
                }else {
                    loDraw.openDrawer(loleft);

                }
            }
        });
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,SearchActivity.class));
            }
        });
    }
    private int getHeight(){
        //状态栏
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height","dimen","android");
        if(resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
