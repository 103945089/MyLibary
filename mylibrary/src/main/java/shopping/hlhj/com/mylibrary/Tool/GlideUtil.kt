package shopping.hlhj.com.mylibrary.Tool

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tenma.ventures.bean.utils.TMSharedPUtil
import shopping.hlhj.com.mylibrary.R
import shopping.hlhj.com.mylibrary.data.Constant
import com.shuyu.gsyvideoplayer.utils.NetworkUtils.isConnected
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer


/**
 * Created by Never Fear   on 2018\10\23 0023.
Never More....
 */
object GlideUtil {
    fun loadIMg(c:Context,url:String,target:ImageView){

        if (TMSharedPUtil.getTMOnlyWiFiLoad(c)){
            if (isWifiConnect(c)){
                if (url.contains("http")){
                    Glide.with(c).load(url).into(target)
                }else{
                    Glide.with(c).load(Constant.IMG_URL+url).into(target)
                }
            }
        }else{
            if (url.contains("http")){
                Glide.with(c).load(url).into(target)
            }else{
                Glide.with(c).load(Constant.IMG_URL+url).into(target)
            }
        }

    }

    @SuppressLint("MissingPermission")
    fun isWifiConnect(c:Context): Boolean {
        val connManager = c.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        return mWifiInfo.isConnected()
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

    fun loadVideo(c:Context,videoPlayer: GSYBaseVideoPlayer){
        if (GlideUtil.isWifiConnect(c)) {
            videoPlayer.startPlayLogic()
        }

    }
}