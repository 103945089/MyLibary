package shopping.hlhj.com.myarr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import shopping.hlhj.com.mylibrary.fragment.MainFragment;

/**
 * Created by Never Fear   on 2018\10\18 0018.
 * Never More....
 */

public class FgmAty extends AppCompatActivity {
    private PagerAdp pagerAdp;
    private ArrayList<Fragment> fragments=new ArrayList<>();
    private ViewPager viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_fgm);
        viewPager=findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        MainFragment mainFragment = new MainFragment();
        fragments.add(mainFragment);
        pagerAdp=new PagerAdp(fragments,getSupportFragmentManager());
        viewPager.setAdapter(pagerAdp);
    }
}
