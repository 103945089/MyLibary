package shopping.hlhj.com.myarr;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import shopping.hlhj.com.mylibrary.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {
    private FrameLayout rp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);

     /*   Intent intent = new Intent(this, shopping.hlhj.com.mylibrary.activity.MainActivity.class);

        startActivity(intent);*/

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MainFragment mainFgm = new MainFragment();
        fragmentTransaction.add(mainFgm,"");
        fragmentTransaction.show(mainFgm);
        fragmentTransaction.commit();


    }
}
