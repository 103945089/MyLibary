package shopping.hlhj.com.mylibrary.presenter

import com.alibaba.fastjson.JSON
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Response
import shopping.hlhj.com.mylibrary.BasePresenter
import shopping.hlhj.com.mylibrary.BaseView
import shopping.hlhj.com.mylibrary.bean.CollBean
import shopping.hlhj.com.mylibrary.data.Constant

/**
 * Created by Never Fear   on 2018\9\17 0017.
Never More....
 */
class CollectPresenter(baseView:CollectView) :BasePresenter<CollectPresenter.CollectView>(baseView) {
    /**
     * 是否已收藏
     */
    fun isColl(member_code:String,app_id:String,article_id:String,token:String){
        OkGo.post<String>(Constant.IS_COLL)
                .params("member_code",member_code)
                .params("app_id",app_id)
                .params("article_id",article_id)
                .headers("token",token)
                .execute(object :StringCallback(){
                    override fun onSuccess(response: Response<String>?) {
                        val body = response?.body()
                        val jsonObject = JSON.parseObject(body)
                        val code = jsonObject.getInteger("code")!!
                        if (code==200){
                            try {
                                val collBean = Gson().fromJson(body, CollBean::class.java)
                                view?.hasCollected(collBean)
                            }catch (e:Exception){
                                view?.notCollected()
                            }
                        }else{
                            view?.notCollected()
                        }
                    }
                })
    }


    /**
     * 添加收藏
     */
    fun addColl(member_code:String,title:String,intro:String
                ,app_id:String,article_id:String,extend:String,tag:String,type:String,pic:String,token: String){

        OkGo.post<String>(Constant.ADD_COLL)
                .params("member_code",member_code)
                .params("title",title)
                .params("intro",intro)
                .params("app_id",app_id)
                .params("article_id",article_id)
                .params("extend",extend)
                .params("tag",tag)//传空字符串
                .params("type",type)//类型 1 文章 2视频
                .params("pic",pic)//缩略图
                .params("token",token)
                .execute(object :StringCallback(){
                    override fun onSuccess(response: Response<String>?) {
                        val body = response!!.body()
                        val jsonObject = JSON.parseObject(body)
                        val code = jsonObject.getInteger("code")!!
                        if (code==200){
                            try {
                                val collBean = Gson().fromJson(body, CollBean::class.java)
                                view?.addCollect(collBean)
                            }catch (e:Exception){
                                view?.addCollectError(e)
                            }
                        }else{
                            view?.addCollectError()
                        }
                    }
                })
    }

    /**
     * 取消收藏
     */
    fun cancelColl(star_id:Int,token: String){
        OkGo.post<String>(Constant.CANCEL_COLL)
                .params("star_id",star_id)
                .headers("token",token)
                .execute(object :StringCallback(){
                    override fun onSuccess(response: Response<String>?) {
                        val body = response!!.body()
                        val jsonObject = JSON.parseObject(body)
                        val code = jsonObject.getInteger("code")!!
                        if (code==200){
                            view?.cancelCollect()
                        }else{
                            view?.cancelCollectErro()
                        }

                    }
                })
    }
    interface CollectView :BaseView{
        fun hasCollected(collBean: CollBean)
        fun notCollected()

        fun addCollect(collBean: CollBean)
        fun addCollectError(e:Exception)
        fun addCollectError()

        fun cancelCollect()
        fun cancelCollectErro()
    }
}