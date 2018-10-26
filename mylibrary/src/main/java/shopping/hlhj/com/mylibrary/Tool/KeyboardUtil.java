package shopping.hlhj.com.mylibrary.Tool;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Never Fear   on 2018\10\25 0025.
 * Never More....
 */

public class KeyboardUtil {


    /**
     * 显示软键盘，当布局加载完成后调用，否则无效
     * @param context
     * @param focusView:必须为EditText或者其子类并且获得焦点，并且是VISIBLE
     */
    public static void showSoftInput(Context context, EditText focusView){
        InputMethodManager inputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            focusView.requestFocus();
            inputMethodManager.showSoftInput(focusView,0);
        }
    }

    /**
     * 隐藏软键盘，当布局加载完成后调用，否则无效
     * @param context
     */
    public static void hideSoftInput(Context context){
        InputMethodManager inputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity)context).getWindow().getDecorView().getWindowToken(),0);
        }
    }

    /**
     * 切换软键盘状态（隐藏-显示或显示-隐藏），当布局加载完成后调用，否则无效
     * @param context
     */
    public static void toggleSoftInput(Context context){
        InputMethodManager inputMethodManager= (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(0,0);
        }
    }

}
