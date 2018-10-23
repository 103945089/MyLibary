package shopping.hlhj.com.mylibrary.Tool

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import shopping.hlhj.com.mylibrary.data.Constant

/**
 * Created by Never Fear   on 2018\10\23 0023.
Never More....
 */
object GlideUtil {
    fun loadIMg(c:Context,url:String,target:ImageView){
        if (url.contains("http")){
            Glide.with(c).load(url).into(target)
        }else{
            Glide.with(c).load(Constant.IMG_URL+url).into(target)
        }
    }
}