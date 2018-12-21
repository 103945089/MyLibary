package shopping.hlhj.com.mylibrary.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.widget.SpringView;
import com.tenma.ventures.bean.utils.TMSharedPUtil;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.Tool.FullyGridLayoutManager;
import shopping.hlhj.com.mylibrary.Tool.TimeUtil;
import shopping.hlhj.com.mylibrary.adapter.SearchRcyAdp;
import shopping.hlhj.com.mylibrary.adapter.SearchResultAdapter;
import shopping.hlhj.com.mylibrary.bean.Search;
import shopping.hlhj.com.mylibrary.db.DBHelper;
import shopping.hlhj.com.mylibrary.presenter.SearchPresenter;

public class SearchActivity extends BaseActivity<SearchPresenter> implements SearchPresenter.MyGridView {

    private EditText etSearch;
    private ImageView imgBtnBack, img_delAll;
    private TextView tvSearch,btS,btE;
    private LinearLayout llSearch, llSearchHot, llSearchHistroy;
    private RelativeLayout rlSearchHistroy;
    private FrameLayout fmSearchNomal;
    private RecyclerView gridView_histroy,gridView_hot;
    private SearchRcyAdp hotadp,hisAdp;
    private GridView gdSearch;
    private SpringView spView;
    private SearchResultAdapter resultAdapter;
    private String string;
    private View btYes;
    private int page = 1;
    private DBHelper dbHelper;
    private List<String> hotDatas = new ArrayList<>();
    private List<String> hisDatas=new ArrayList<>();

    private View loBack;
    private int mYear1=0;
    private int mMonth1=0;
    private int mDay1=0;

    private int mYear2=0;
    private int mMonth2=0;
    private int mDay2=0;

    @Override
    protected int getContentResId() {
        return R.layout.activity_kk_search;

    }


    @Override
    protected void beforeinit() {
          /*初始化年月份*/
        Calendar ca = Calendar.getInstance();
        mYear1 = ca.get(Calendar.YEAR);
        mMonth1 = ca.get(Calendar.MONTH);
        mDay1 = ca.get(Calendar.DAY_OF_MONTH);

        mYear2 = ca.get(Calendar.YEAR);
        mMonth2 = ca.get(Calendar.MONTH);
        mDay2 = ca.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void initView() {
        imgBtnBack = findViewById(R.id.btExit);
        etSearch = findViewById(R.id.etSearch);
        tvSearch = findViewById(R.id.tvSearch);
        llSearch = findViewById(R.id.ll_search);
        btYes=findViewById(R.id.btYes);
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
        btS=findViewById(R.id.btS);
        btE=findViewById(R.id.btE);
        hotadp=new SearchRcyAdp(hotDatas,2);
        hisAdp=new SearchRcyAdp(hisDatas,1);
        gridView_hot.setAdapter(hotadp);
        gridView_hot.setLayoutManager(new FullyGridLayoutManager(this,2));

        gridView_histroy.setAdapter(hisAdp);
        gridView_histroy.setLayoutManager(new FullyGridLayoutManager(this,2));


        if (TMSharedPUtil.getTMThemeColor(this)!=null&&!TMSharedPUtil.getTMThemeColor(this).isEmpty()){
            loBack.setBackgroundColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
            GradientDrawable drawable = (GradientDrawable) btS.getBackground();
            drawable.setColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
            btS.setBackground(drawable);

            GradientDrawable drawable2 = (GradientDrawable) btE.getBackground();
            drawable2.setColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
            btE.setBackground(drawable2);
            GradientDrawable drawable3 = (GradientDrawable) btYes.getBackground();
            drawable3.setColor(Color.parseColor(TMSharedPUtil.getTMThemeColor(this)));
            btYes.setBackground(drawable3);

        }

    }

    @Override
    protected void initData() {
        dbHelper = new DBHelper(this);
        dbHelper.getWritableDatabase();
        hisDatas.addAll(dbHelper.findAll());
        hisAdp.notifyDataSetChanged();
        List<String> stringList = dbHelper.findAll();
        if (stringList == null || stringList.size() == 0) {
            llSearchHistroy.setVisibility(View.GONE);
        }

        setPresenter(new SearchPresenter(SearchActivity.this));
        getPresenter().loadSearchHot(this);

    }

    @Override
    protected void setOnClick() {
        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string = etSearch.getText().toString();
                    llSearch.setVisibility(View.VISIBLE);
                    fmSearchNomal.setVisibility(View.GONE);
                    llSearchHistroy.setVisibility(View.GONE);
                    llSearchHot.setVisibility(View.GONE);
                    getPresenter().loadSearchData(SearchActivity.this, string,btS.getText().toString(),btE.getText().toString(),page);

                    /*//TODO 重复添加
                    List<String> all = dbHelper.findAll();
                    if (!all.contains(string)){
                        dbHelper.add(string);
                    }*/
            }
        });
        btS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(SearchActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        btS.setText(format.format(date));
                    }
                }).build();
                pvTime.show();
            }
        });

        btE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerView pvTime = new TimePickerBuilder(SearchActivity.this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        btE.setText(format.format(date));
                    }
                }).build();
                pvTime.show();
            }
        });

        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string = etSearch.getText().toString();
                if (btS.getText().toString().equals("开始时间")||btS.getText().toString().equals("结束时间")){
                    if (null == string || "" == string || TextUtils.isEmpty(string)) {
                        Toast.makeText(getApplicationContext(),"请输入搜索内容",Toast.LENGTH_LONG).show();

                        return;
                    }
                }
                string = etSearch.getText().toString();
                llSearch.setVisibility(View.VISIBLE);
                fmSearchNomal.setVisibility(View.GONE);
                llSearchHistroy.setVisibility(View.GONE);
                llSearchHot.setVisibility(View.GONE);
                getPresenter().loadSearchData(SearchActivity.this, string,btS.getText().toString(),btE.getText().toString(),page);
                //TODO 重复添加
                List<String> all = dbHelper.findAll();
                if (!all.contains(string)){
                    if (TextUtils.isEmpty(string))return;
                    dbHelper.add(string);
                }
                /*if (null == string || "" == string || TextUtils.isEmpty(string)) {
                    Toast.makeText(getApplicationContext(),"请输入搜索内容",Toast.LENGTH_LONG).show();
                } else {
                    llSearch.setVisibility(View.VISIBLE);
                    fmSearchNomal.setVisibility(View.GONE);
                    llSearchHistroy.setVisibility(View.GONE);
                    llSearchHot.setVisibility(View.GONE);
                    getPresenter().loadSearchData(SearchActivity.this, string, page);
                }*/
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
        hisAdp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String s = dbHelper.findAll().get(position);
                etSearch.setText(s.toString());
                getPresenter().loadSearchData(SearchActivity.this, s, page);
            }
        });
        hotadp.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String s = hotDatas.get(position).toString();
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
                if (null==searchBeanList.get(position).getVideo_url()||searchBeanList.get(position).getVideo_url().isEmpty()){
                    Intent intent = new Intent(SearchActivity.this, TextDetailsActivity.class);
                    intent.putExtra("id",searchBeanList.get(position).id);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SearchActivity.this, FhpVideoDetailAty.class);
                    intent.putExtra("id",searchBeanList.get(position).id);
                    startActivity(intent);
                }
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
        hotDatas.clear();
        for (String s : data) {
            hotDatas.add(s);
        }
        hotadp.notifyDataSetChanged();
    }
}
