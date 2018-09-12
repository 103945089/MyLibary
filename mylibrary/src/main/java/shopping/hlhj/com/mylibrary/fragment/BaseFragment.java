package shopping.hlhj.com.mylibrary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;

public abstract class BaseFragment <T extends BasePresenter<? extends BaseView>> extends Fragment implements BaseView {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private T presenter;

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }


}
