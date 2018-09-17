package shopping.hlhj.com.mylibrary.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import shopping.hlhj.com.mylibrary.BaseActivity;
import shopping.hlhj.com.mylibrary.R;

public class ConfirmLoginActivity extends BaseActivity{

    private TextView tv_cancle, tv_login;
    @Override
    protected int getContentResId() {
        return R.layout.activity_confirm_login;
    }

    @Override
    protected void beforeinit() {

    }

    @Override
    protected void initView() {
        tv_cancle = findViewById(R.id.tv_cancle);
        tv_login = findViewById(R.id.tv_login);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setOnClick() {
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getPackageName() + ".usercenter.login");
                startActivity(intent);
            }
        });
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
