package shopping.hlhj.com.mylibrary.Tool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by zhengzaihong on 2016/10/24 0024.
 */

public class ClassTools {

    public static void toAct(Context mContext, Class<?> cls) {
        toAct(mContext, cls, null);
    }

    public static void toAct(Context mContext, Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        mContext.startActivity(intent);
    }

    public static void toActClearTop(Context mContext, Class<?> cls) {
        toActClearTop(mContext, cls, null);
    }

    public static void toActClearTop(Context mContext, Class<?> cls,
                                     Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mContext.startActivity(intent);
    }

}