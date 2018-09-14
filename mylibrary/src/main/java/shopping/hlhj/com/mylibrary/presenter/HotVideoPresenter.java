package shopping.hlhj.com.mylibrary.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;

import shopping.hlhj.com.mylibrary.BasePresenter;
import shopping.hlhj.com.mylibrary.BaseView;
import shopping.hlhj.com.mylibrary.data.Constant;

public class HotVideoPresenter extends BasePresenter<HotVideoPresenter.HotVideoView>{

    public HotVideoPresenter(HotVideoView baseview) {
        super(baseview);
    }

    public void loadHotVideoData(Context context){
    }

    public interface HotVideoView extends BaseView{
        void loadDataSuccess();

        void loadFailed(String msg);
    }
}
