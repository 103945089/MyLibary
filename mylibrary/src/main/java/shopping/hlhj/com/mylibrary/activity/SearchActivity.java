package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.adapter.SearchAdapter;
import shopping.hlhj.com.mylibrary.adapter.SearchResultAdapter;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.db.DBHelper;
import shopping.hlhj.com.mylibrary.fragment.FragmentIndexChoice;
import shopping.hlhj.com.mylibrary.presenter.SearchPresenter;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchPresenter.MyGridView {

    private EditText etSearch;
    private ImageView imgBtnBack;
    private TextView tvSearch;
    private LinearLayout llSearch, llSearchHot, llSearchHistroy;
    private RelativeLayout rlSearchHistroy;
    private FrameLayout fmSearchNomal;
    private GridView gridView_histroy, gridView_hot;
    private GridView gdSearch;
    private SpringView spView;
    private SearchResultAdapter resultAdapter;
    private String string;
    private int page = 1;
    private DBHelper dbHelper;
    private List<String> strings = new ArrayList<>();

    @Override
    protected int getContentResId() {
        return R.layout.activity_search;

    }


    @Override
    protected void beforeinit() {

    }

    @Override
    protected void initView() {
        imgBtnBack = findViewById(R.id.btExit);
        etSearch = findViewById(R.id.etSearch);
        tvSearch = findViewById(R.id.tvSearch);
        llSearch = findViewById(R.id.ll_search);
        llSearchHot = findViewById(R.id.ll_search_hot);
        llSearchHistroy = findViewById(R.id.ll_search_histroy);
        rlSearchHistroy = findViewById(R.id.rl_search_histroy);
        gdSearch = findViewById(R.id.gride_search);
        fmSearchNomal = findViewById(R.id.fm_search_nomarl);
        gridView_histroy = findViewById(R.id.grideview_histroy);
        gridView_hot = findViewById(R.id.grideview_hot);
        spView = findViewById(R.id.spView);

    }

    @Override
    protected void initData() {
        dbHelper = new DBHelper(this);
        dbHelper.getWritableDatabase();
        strings.clear();
        for (int i = 0; i < 1; i++) {
            strings.add("关键字");
        }
        List<String> stringList = dbHelper.findAll();
        if (stringList == null || stringList.size() == 0) {
            llSearchHistroy.setVisibility(View.GONE);
        }
        setPresenter(new SearchPresenter(SearchActivity.this));
        getPresenter().loadSearchData(SearchActivity.this, string, page);
        getPresenter().loadSearchHot(this);
        SearchAdapter historyAdapter = new SearchAdapter(this, stringList, true);
        gridView_histroy.setAdapter(historyAdapter);

    }

    @Override
    protected void setOnClick() {
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                finish();
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string = etSearch.getText().toString();
                if (null == string || "" == string || TextUtils.isEmpty(string)) {
                    fmSearchNomal.setVisibility(View.VISIBLE);
                    llSearch.setVisibility(View.GONE);
                } else {
                    getPresenter().loadSearchData(SearchActivity.this, string, page);
                    //TODO 重复添加
                    dbHelper.add(string);
                }
            }
        });
        //先判断数据库是否为空在来展示
        rlSearchHistroy.setVisibility(View.VISIBLE);
        spView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
            }

            @Override
            public void onLoadmore() {
                page++;
            }
        });
    }

    @Override
    public void loadSuccess(List<Search.SearchData.SearchBean> searchBeanList) {
        if (searchBeanList != null && searchBeanList.size() > 0) {
            resultAdapter = new SearchResultAdapter(this, searchBeanList);
            gdSearch.setAdapter(resultAdapter);
            gdSearch.setVisibility(View.VISIBLE);
            fmSearchNomal.setVisibility(View.GONE);
            llSearchHistroy.setVisibility(View.GONE);
            llSearchHot.setVisibility(View.GONE);
            return;
        }
    }

    @Override
    public void loadFailed(String msg) {
        fmSearchNomal.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.GONE);
    }

    @Override
    public void loadHotData(String[] data) {
//        strings.clear();
//        for (String s : data){
//            strings.add(s);
//        }
//        SearchAdapter hotAdapter = new SearchAdapter(this, strings, false);
//        gridView_hot.setAdapter(hotAdapter);
    }
}
