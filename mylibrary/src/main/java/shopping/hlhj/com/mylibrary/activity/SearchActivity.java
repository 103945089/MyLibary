package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

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
    private ImageView imgBtnBack, img_delAll;
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
    private View loBack;

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
        img_delAll = findViewById(R.id.img_delAll);
        loBack=findViewById(R.id.loBack);
        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
        }

    }

    @Override
    protected void initData() {
        dbHelper = new DBHelper(this);
        dbHelper.getWritableDatabase();
        List<String> stringList = dbHelper.findAll();
        if (stringList == null || stringList.size() == 0) {
            llSearchHistroy.setVisibility(View.GONE);
        }
        setPresenter(new SearchPresenter(SearchActivity.this));
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
                    llSearchHistroy.setVisibility(View.VISIBLE);
                    llSearchHot.setVisibility(View.VISIBLE);
                } else {
                    llSearch.setVisibility(View.VISIBLE);
                    fmSearchNomal.setVisibility(View.GONE);
                    llSearchHistroy.setVisibility(View.GONE);
                    llSearchHot.setVisibility(View.GONE);
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
        img_delAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.delAll();
                gridView_histroy.setVisibility(View.GONE);
            }
        });
        gridView_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = strings.get(position).toString();
                etSearch.setText(s.toString());
                getPresenter().loadSearchData(SearchActivity.this, s, page);
            }
        });
    }

    @Override
    public void loadSuccess(final List<Search.SearchData.SearchBean> searchBeanList) {
        resultAdapter = new SearchResultAdapter(this, searchBeanList);
        gdSearch.setAdapter(resultAdapter);
        gdSearch.setVisibility(View.VISIBLE);
        fmSearchNomal.setVisibility(View.GONE);
        llSearchHistroy.setVisibility(View.GONE);
        llSearchHot.setVisibility(View.GONE);
        gdSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this,HotVideoDetailActivity.class);
                intent.putExtra("id",searchBeanList.get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadFailed(String msg) {
        Toast.makeText(SearchActivity.this, "无搜索结果", Toast.LENGTH_SHORT).show();
        fmSearchNomal.setVisibility(View.VISIBLE);
        llSearch.setVisibility(View.GONE);
    }

    @Override
    public void loadHotData(String[] data) {
        strings.clear();
        for (String s : data) {
            strings.add(s);
        }
        SearchAdapter hotAdapter = new SearchAdapter(this, strings, false);
        gridView_hot.setAdapter(hotAdapter);

    }
}
