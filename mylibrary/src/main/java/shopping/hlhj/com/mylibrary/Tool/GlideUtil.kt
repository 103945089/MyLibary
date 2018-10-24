package shopping.hlhj.com.mylibrary.Tool

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import shopping.hlhj.com.mylibrary.R
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

    fun loadHead(c:Context,url:String,target:ImageView){
        val options = RequestOptions()
        options.placeholder(R.drawable.ic_bl_touxiang)
                .error(R.drawable.ic_bl_touxiang)
        if (url.contains("http")){
            Glide.with(c).load(url).apply(options).into(target)
        }else{
            Glide.with(c).load(Constant.IMG_URL+url).apply(options).into(target)
        }
    }
}