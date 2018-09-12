package shopping.hlhj.com.mylibrary.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import shopping.hlhj.com.mylibrary.activity.HotVideoActivity;
import shopping.hlhj.com.mylibrary.adapter.RecommendAdapter;
import shopping.hlhj.com.mylibrary.adapter.SpecialAdapter;
import shopping.hlhj.com.mylibrary.bean.HotAdSpecial;
import shopping.hlhj.com.mylibrary.adapter.HotVideoAdapter;
import shopping.hlhj.com.mylibrary.bean.RecommendBean;
import shopping.hlhj.com.mylibrary.bean.TopBanner;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.R;

public class FragmentIndexChoice extends Fragment {
    Banner mBanner;
    TextView tvShipingMore,tvTime,tvRemenMore,tvZhuantiMore,tvTuijianTitle,tvTuijiantype;
    VideoView videoXinwen;
    RecyclerView ryZhuanti,ryNews;
    GridView grideViewOther, grideHot;
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    ImageView imgItem1, imgItem2, imgItem3, Imgtuijian;
    ScrollView scrollView;
    private List<String> titles;
    private View view;
    private Context context;
    private HotVideoAdapter hotVideoAdapter;
    private SpecialAdapter specialAdapter;
    private RecommendAdapter recommendAdapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index_choice, null);
        context = getActivity();
        initView();
        initData();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        mBanner = view.findViewById(R.id.convenientBanner);
        tvShipingMore = view.findViewById(R.id.tv_shiping_more);
        videoXinwen = view.findViewById(R.id.video_xinwen);
        tvTime = view.findViewById(R.id.tv_time);
        tvRemenMore = view.findViewById(R.id.tv_remen_more);
        tvZhuantiMore = view.findViewById(R.id.tv_zhuanti_more);
        ryZhuanti = view.findViewById(R.id.ry_zhuanti);
        tvTuijianTitle = view.findViewById(R.id.tv_tuijian_title);
        tvTuijiantype = view.findViewById(R.id.tv_tuijian_type);
        grideViewOther = view.findViewById(R.id.gride_view_other);
        grideHot = view.findViewById(R.id.gride_hot);
        ryNews = view.findViewById(R.id.ry_news);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        imgItem1 = view.findViewById(R.id.img_item1);
        imgItem2 = view.findViewById(R.id.img_item2);
        imgItem3 = view.findViewById(R.id.img_item3);
        Imgtuijian = view.findViewById(R.id.img_tuijian);
        scrollView = view.findViewById(R.id.scroller);
    }

    private void initData() {
        titles = new ArrayList<>();
        scrollView.smoothScrollTo(0,20);        //解决起始位置不是在顶部
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryZhuanti.setLayoutManager(manager);

        loadBannerAdLive();
        loadHotAdSpecial();
        loadRecommend();
        setOnClick();
    }

    private void setOnClick() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                initData();
            }
        });
        refreshLayout.setEnableAutoLoadmore(true);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                loadRecommend();
            }
        });
        tvShipingMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvRemenMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, HotVideoActivity.class));
            }
        });
        tvZhuantiMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //轮播&直播
    public void loadBannerAdLive() {
        OkGo.<String>get(Constant.BANNER_URL)
                .tag(context)
//                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
//                .retryCount(2)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {

                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("carousel");
                            JSONArray jsonArray1 = jsonObject.getJSONObject("data").getJSONArray("live");
                            List<TopBanner.Datas.BannerBean> bannerBeanList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<TopBanner.Datas.BannerBean>>() {
                            }.getType());
                            List<TopBanner.Datas.LiveBean> liveBeanList = new Gson().fromJson(jsonArray1.toString(), new TypeToken<List<TopBanner.Datas.LiveBean>>() {
                            }.getType());
                            if (bannerBeanList != null && liveBeanList != null && bannerBeanList.size() > 0 && liveBeanList.size() > 0) {
                                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                                mBanner.setImageLoader(new FragmentIndexChoice.GlideImageLoader());
                                mBanner.setImages(bannerBeanList);
                                titles.clear();
                                for (int i = 0; i < bannerBeanList.size(); i++) {
                                    String title = bannerBeanList.get(i).getTitle();
                                    Log.d("-----------",title.toString());
                                    titles.add(title);
                                }
                                mBanner.setBannerTitles(titles);
                                mBanner.start();
                                Glide.with(context).load(liveBeanList.get(0).getLive_thumb()).into(imgItem1);
                                Glide.with(context).load(liveBeanList.get(1).getLive_thumb()).into(imgItem2);
                                Glide.with(context).load(liveBeanList.get(2).getLive_thumb()).into(imgItem3);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Log.e("okgo", "出错了---------------http：" + response.toString());
                        super.onError(response);

                    }
                });
    }

    //热门&专题
    public void loadHotAdSpecial() {
        OkGo.<String>get(Constant.HOT_URL)
                .tag(context)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("hot");
                            JSONArray jsonArray1 = jsonObject.getJSONObject("data").getJSONArray("topics");
                            List<HotAdSpecial.HotAdSpecialData.HotBean> hotBeans = new Gson().fromJson(jsonArray.toString(),
                                    new TypeToken<List<HotAdSpecial.HotAdSpecialData.HotBean>>() {
                                    }.getType());
                            List<HotAdSpecial.HotAdSpecialData.Special> specialList = new Gson().fromJson(jsonArray1.toString(),
                                    new TypeToken<List<HotAdSpecial.HotAdSpecialData.Special>>() {
                                    }.getType());

                            if (hotBeans != null && hotBeans.size() > 0 && specialList != null && specialList.size() > 0) {
                                hotVideoAdapter = new HotVideoAdapter(context, hotBeans);
                                grideHot.setAdapter(hotVideoAdapter);

                                specialAdapter = new SpecialAdapter(context, specialList);
                                ryZhuanti.setAdapter(specialAdapter);
                                specialAdapter.setOnItemOnClick(new SpecialAdapter.OnItemOnClick() {
                                    @Override
                                    public void onItemClick(int position, View view) {

                                    }
                                });
                            }
                        }

                    }
                });
    }

    public void loadRecommend() {
        OkGo.<String>post(Constant.TUIJIAN_URL)
                .tag(context)
                .params("page", page)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("hot");
                            List<RecommendBean.RecommendData.RecommenDatas> recommenDatas = new Gson().fromJson(jsonArray.toString(),
                                    new TypeToken<List<RecommendBean.RecommendData.RecommenDatas>>() {
                                    }.getType());
                            if (recommenDatas != null && recommenDatas.size() > 0) {
                                Glide.with(context).load(recommenDatas.get(0).cover).into(Imgtuijian);
                                tvTuijianTitle.setText(recommenDatas.get(0).title);
                                tvTuijiantype.setText(recommenDatas.get(0).type + "");
                                recommendAdapter = new RecommendAdapter(context, recommenDatas);
                                grideViewOther.setAdapter(recommendAdapter);
                                if (page == 1) {

                                }

                            }
                        }
                    }
                });

    }


    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object banner, ImageView imageView) {
            Log.d("---------------",banner.toString());
            Glide.with(context).load(((TopBanner.Datas.BannerBean)banner).getCover()).into(imageView);
        }

        @Override
        public ImageView createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }
    }
}
