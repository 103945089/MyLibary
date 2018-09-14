package shopping.hlhj.com.mylibrary;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<T extends BaseView>  {

    private WeakReference<T> weakReference;

    public BasePresenter(T baseview){
        setView(baseview);
    }

    public T getView(){
        return weakReference != null ?weakReference.get() : null;
    }

    public void setView(T view) {
        this.weakReference = new WeakReference<>(view);
    }

    public void recycle(){
        if (weakReference != null){
            weakReference.clear();
            weakReference = null;
        }
    }
}
