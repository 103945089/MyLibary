package shopping.hlhj.com.mylibrary.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.apache.cordova.LOG;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import shopping.hlhj.com.mylibrary.Tool.FullyGridLayoutManager;
import shopping.hlhj.com.mylibrary.Tool.JavaUtils;
import shopping.hlhj.com.mylibrary.activity.ArticleDetailActivity;
import shopping.hlhj.com.mylibrary.activity.HotVideoActivity;
import shopping.hlhj.com.mylibrary.activity.HotVideoDetailActivity;
import shopping.hlhj.com.mylibrary.activity.LiveNewsActivity;
import shopping.hlhj.com.mylibrary.activity.LiveNewsMoreActivity;
import shopping.hlhj.com.mylibrary.activity.SpecialMoreActivity;
import shopping.hlhj.com.mylibrary.adapter.HotVideoRcyAdp;
import shopping.hlhj.com.mylibrary.adapter.LiveNewsAdapter;
import shopping.hlhj.com.mylibrary.adapter.RecommendAdapter;
import shopping.hlhj.com.mylibrary.adapter.RecommendRcyAdp;
import shopping.hlhj.com.mylibrary.adapter.SpecialAdapter;
import shopping.hlhj.com.mylibrary.bean.HotAdSpecial;
import shopping.hlhj.com.mylibrary.adapter.HotVideoAdapter;
import shopping.hlhj.com.mylibrary.bean.RecommendBean;
import shopping.hlhj.com.mylibrary.bean.TopBanner;
import shopping.hlhj.com.mylibrary.data.Constant;
import shopping.hlhj.com.mylibrary.R;

public class FragmentIndexChoice extends Fragment {
    Banner mBanner;
    TextView tvLiveNewsTitle,tvShipingMore,tvTime,tvRemenMore,tvZhuantiMore,tvTuijianTitle,tvTuijianAuthor;
    ImageView imgXinwen;
    RecyclerView ryZhuanti,ryNews;
    GridView  gridLive;
    RecyclerView grideHot,grideViewOther;
    SpringView springView;
    Unbinder unbinder;
    ImageView Imgtuijian;
    ScrollView scrollView;
    private TextView bannerTittle;
    private List<String> titles;
    public static HashMap<Integer,Boolean> seleHash=new HashMap<>();
    private View view;
    private Context context;
    private HotVideoAdapter hotVideoAdapter;
    private List<HotAdSpecial.HotAdSpecialData.HotBean> hotDatas=new ArrayList<>();
    private HotVideoRcyAdp hotVideoRcyAdp;
    private SpecialAdapter specialAdapter;
    private RecommendAdapter recommendAdapter;
    private LiveNewsAdapter liveNewsAdapter;
    private int page = 1;
    private int liveId,tuijianId,bannerId;
    private RecommendRcyAdp recommendRcyAdp;

    private List<RecommendBean.RecommendData.RecommenDatas> rDatas=new ArrayList<>();
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
        bannerTittle=view.findViewById(R.id.bannerTittle);
        tvLiveNewsTitle = view.findViewById(R.id.tv_xinwen);
        tvShipingMore = view.findViewById(R.id.tv_shiping_more);
        imgXinwen = view.findViewById(R.id.img_xinwen);
        tvTime = view.findViewById(R.id.tv_time);
        tvRemenMore = view.findViewById(R.id.tv_remen_more);
        tvZhuantiMore = view.findViewById(R.id.tv_zhuanti_more);
        ryZhuanti = view.findViewById(R.id.ry_zhuanti);
        tvTuijianTitle = view.findViewById(R.id.tv_tuijian_title);
        tvTuijianAuthor = view.findViewById(R.id.tv_tuijian_author);
        grideViewOther = view.findViewById(R.id.gride_view_other);
        grideHot = view.findViewById(R.id.gride_hot);
        gridLive = view.findViewById(R.id.grid_live);
        ryNews = view.findViewById(R.id.ry_news);
        springView = view.findViewById(R.id.springview);
        Imgtuijian = view.findViewById(R.id.img_tuijian);
        scrollView = view.findViewById(R.id.scroller);
        springView.setHeader(new DefaultHeader(context));
        springView.setFooter(new DefaultFooter(context));


    }

    private void initData() {
        titles = new ArrayList<>();
        scrollView.smoothScrollTo(0,0);        //解决起始位置不是在顶部
        GridLayoutManager manager = new GridLayoutManager(context, 1);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ryZhuanti.setLayoutManager(manager);


        hotVideoRcyAdp=new HotVideoRcyAdp(hotDatas);
        grideHot.setLayoutManager(new FullyGridLayoutManager(getContext(),2));
        grideHot.setAdapter(hotVideoRcyAdp);
        grideHot.setNestedScrollingEnabled(false);
        getData();

        recommendRcyAdp=new RecommendRcyAdp(rDatas);
        grideViewOther.setAdapter(recommendRcyAdp);
        grideViewOther.setLayoutManager(new FullyGridLayoutManager(getContext(),2));
        grideViewOther.setNestedScrollingEnabled(false);

    }

    public void getData(){
        loadBannerAdLive();
        loadHotAdSpecial();
        loadRecommend();
        setOnClick();
    }

    private void setOnClick() {
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getData();
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                page ++ ;
                loadRecommend();
                springView.onFinishFreshAndLoad();
            }
        });
        tvShipingMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LiveNewsMoreActivity.class));
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
                startActivity(new Intent(context,SpecialMoreActivity.class));
            }
        });
        imgXinwen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveNewsActivity.class);
                intent.putExtra("id",liveId);
                startActivity(intent);
            }
        });
        gridLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        Imgtuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HotVideoDetailActivity.class);
                intent.putExtra("id",tuijianId);
                startActivity(intent);
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
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        JSONObject jsonObject = JSON.parseObject(body);
                        int code = jsonObject.getInteger("code");
                        if (code == 200) {
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("carousel");
                            JSONArray jsonArray1 = jsonObject.getJSONObject("data").getJSONArray("live");
                            final List<TopBanner.Datas.BannerBean> bannerBeanList = new Gson().fromJson(jsonArray.toString(), new TypeToken<List<TopBanner.Datas.BannerBean>>() {
                            }.getType());
                            List<TopBanner.Datas.LiveBean> liveBeanList
                                    = new Gson().fromJson(jsonArray1.toString(), new TypeToken<List<TopBanner.Datas.LiveBean>>() {
                            }.getType());
                            if (bannerBeanList != null && liveBeanList != null && bannerBeanList.size() > 0 && liveBeanList.size() > 0) {
                                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                                mBanner.setImageLoader(new FragmentIndexChoice.GlideImageLoader());
                                mBanner.setImages(bannerBeanList);
                                titles.clear();
                                for (int i = 0; i < bannerBeanList.size(); i++) {
                                    String title = bannerBeanList.get(i).getTitle();
                                    titles.add(title);
                                }
                                mBanner.setBannerTitles(titles);
                                mBanner.setBannerStyle(1);
                                mBanner.start();
                                mBanner.setOnBannerListener(new OnBannerListener() {
                                    @Override
                                    public void OnBannerClick(int position) {
                                        Intent intent = new Intent(context,HotVideoDetailActivity.class);
                                        intent.putExtra("id",bannerBeanList.get(position).id);
                                        startActivity(intent);
                                    }
                                });
                                getLiveImg(liveBeanList);
                                mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        Log.e("fhp","位置"+position);
                                        if (position<=titles.size()){
                                                                                    bannerTittle.setText(titles.get(position-1));

                                        }
                                        if (position==titles.size()+1){
                                            bannerTittle.setText(titles.get(0));
                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
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
                            final List<HotAdSpecial.HotAdSpecialData.HotBean> hotBeans = new Gson().fromJson(jsonArray.toString(),
                                    new TypeToken<List<HotAdSpecial.HotAdSpecialData.HotBean>>() {
                                    }.getType());
                            final List<HotAdSpecial.HotAdSpecialData.Special> specialList = new Gson().fromJson(jsonArray1.toString(),
                                    new TypeToken<List<HotAdSpecial.HotAdSpecialData.Special>>() {
                                    }.getType());

                            if (hotBeans != null && hotBeans.size() > 0 && specialList != null && specialList.size() > 0) {
                                hotDatas.addAll(hotBeans);
                                hotVideoRcyAdp.notifyDataSetChanged();
                                specialAdapter = new SpecialAdapter(context, specialList);
                                ryZhuanti.setAdapter(specialAdapter);
                                specialAdapter.setOnItemOnClick(new SpecialAdapter.OnItemOnClick() {
                                    @Override
                                    public void onItemClick(int position, View view) {
                                        Intent intent = new Intent(context, ArticleDetailActivity.class);
                                        intent.putExtra("id",specialList.get(position).id);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }

                    }
                });
    }

    //推荐
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
                                tuijianId = recommenDatas.get(0).id;
                                Glide.with(context).load(recommenDatas.get(0).cover).into(Imgtuijian);
                                tvTuijianTitle.setText(recommenDatas.get(0).title);
                                String s = JavaUtils.StampstoTime(String.valueOf(recommenDatas.get(0).getCreate_time()), "yyyy-MM-dd HH:mm:ss");
                                try {
                                    String format = JavaUtils.format(s);
                                    tvTuijianAuthor.setText(recommenDatas.get(0).release + " | " + format);
                                    rDatas.addAll(recommenDatas);
                                    recommendRcyAdp.notifyDataSetChanged();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });

    }


    public void getLiveImg(final List<TopBanner.Datas.LiveBean> liveBeanList){
        liveId = liveBeanList.get(0).id;
        tvLiveNewsTitle.setText(liveBeanList.get(0).live_title);
        for (int i = 0; i < liveBeanList.size(); i++) {
            if (i==0){
                seleHash.put(i,true);
            }else {
                seleHash.put(i,false);
            }
        }
        Glide.with(context).load(Constant.IMG_URL + liveBeanList.get(0).getLive_thumb()).into(imgXinwen);
        liveNewsAdapter = new LiveNewsAdapter(context,liveBeanList);
        gridLive.setNumColumns(liveBeanList.size());
        gridLive.setAdapter(liveNewsAdapter);
        gridLive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < seleHash.size(); i++) {
                    if (i==position){
                        seleHash.put(i,true);
                    }else {
                        seleHash.put(i,false);
                    }
                }

                Glide.with(context).load(Constant.IMG_URL + liveBeanList.get(position).getLive_thumb()).into(imgXinwen);
                tvTime.setText(JavaUtils.StampstoTime(String.valueOf(liveBeanList.get(position).create_at),"yyyy-MM-dd HH:mm:ss"));
                tvLiveNewsTitle.setText(liveBeanList.get(position).live_title);
                liveId = liveBeanList.get(position).id;
                liveNewsAdapter.notifyDataSetChanged();
            }
        });
    }

    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object banner, ImageView imageView) {
            if (((TopBanner.Datas.BannerBean) banner).getCover().contains("http")){

                Glide.with(context).load(((TopBanner.Datas.BannerBean)banner).getCover()).into(imageView);
            }else {
                Glide.with(context).load(Constant.IMG_URL+((TopBanner.Datas.BannerBean)banner).getCover()).into(imageView);
            }
        }

        @Override
        public ImageView createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }
    }
}
