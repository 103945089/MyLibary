package shopping.hlhj.com.mylibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tenma.ventures.bean.utils.TMSharedPUtil;

import java.util.List;
import shopping.hlhj.com.mylibrary.R;
import shopping.hlhj.com.mylibrary.activity.LiveNewsActivity;
import shopping.hlhj.com.mylibrary.bean.CollBean;
import shopping.hlhj.com.mylibrary.bean.ExtendBean;
import shopping.hlhj.com.mylibrary.bean.MoreBean;
import shopping.hlhj.com.mylibrary.bean.ParamsBean;
import shopping.hlhj.com.mylibrary.cv.GoLoginDialog;
import shopping.hlhj.com.mylibrary.data.Constant;

public class LiveMoreAdapter extends BaseAdapter{

    private Context context;
    private List<MoreBean.MoreDatas> moreDatas;
    private OnLikeClick onLikeClick;
    public LiveMoreAdapter(Context context, List<MoreBean.MoreDatas> moreDatas,OnLikeClick onLikeClick) {
        this.context = context;
        this.moreDatas = moreDatas;
        this.onLikeClick=onLikeClick;
    }

    @Override
    public int getCount() {
        return moreDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return moreDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LiveMoreViewHolder holder = null;
        if (convertView == null){
            holder = new LiveMoreViewHolder();
            convertView = View.inflate(context, R.layout.adapter_livemore,null);
            holder.imgLiveMore = convertView.findViewById(R.id.img_livemore);
            holder.imgMoreLanud = convertView.findViewById(R.id.img_more_lanud);
            holder.imgMoreCollected = convertView.findViewById(R.id.img_more_collected);
            holder.imgMoreShared = convertView.findViewById(R.id.img_more_shared);
            holder.tvLiveMoreTitle = convertView.findViewById(R.id.tv_livemore_title);
            holder.tvLooknum = convertView.findViewById(R.id.tv_looknum);
            holder.tvMoreLanud = convertView.findViewById(R.id.tv_more_lanud);
            holder.tvMoreComment = convertView.findViewById(R.id.tv_more_comment);
            holder.llLivemoreLanud = convertView.findViewById(R.id.ll_livemore_lanud);
            holder.llLivemoreComment = convertView.findViewById(R.id.ll_livemore_comment);
            holder.linearLayout = convertView.findViewById(R.id.ll_livemore);
            convertView.setTag(holder);
        }else {
            holder = (LiveMoreViewHolder) convertView.getTag();
        }
        Glide.with(context).load(Constant.IMG_URL + moreDatas.get(position).live_thumb).into(holder.imgLiveMore);
        holder.tvLiveMoreTitle.setText(moreDatas.get(position).live_title);
        holder.tvLooknum.setText(moreDatas.get(position).read_num + "");
        holder.tvMoreLanud.setText(moreDatas.get(position).laud_num + "");
        holder.tvMoreComment.setText(moreDatas.get(position).comment_num + "");
        if (moreDatas.get(position).is_laud == 1){
            holder.imgMoreLanud.setImageResource(R.drawable.ic_home_praise_select);
        }else {
            holder.imgMoreLanud.setImageResource(R.drawable.ic_home_praise_normal);
        }
        if (moreDatas.get(position).is_collection == 1){
            holder.imgMoreCollected.setImageResource(R.drawable.ic_collection);
        }else {
            holder.imgMoreCollected.setImageResource(R.drawable.ic_sc_normal);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LiveNewsActivity.class);
                intent.putExtra("id",moreDatas.get(position).id);
                context.startActivity(intent);
            }
        });
        holder.llLivemoreLanud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikeClick.clickLike(moreDatas.get(position).getId(),position);
            }
        });
        //收藏
        //todo ExtenStr的配置
        ParamsBean paramsBean = new ParamsBean();
        paramsBean.setID(moreDatas.get(position).getId());
        ExtendBean extendBean = new ExtendBean();
        ExtendBean.AndroidInfoBean androidInfoBean = new ExtendBean.AndroidInfoBean();
        ExtendBean.IosInfoBean iosInfoBean = new ExtendBean.IosInfoBean();
        androidInfoBean.setNativeX(true);
        androidInfoBean.setParamStr(new Gson().toJson(paramsBean));
        androidInfoBean.setSrc("shopping.hlhj.com.mylibrary.activity.LiveNewsActivity");
        androidInfoBean.setWwwFolder("");

        iosInfoBean.setNativeX(true);
        iosInfoBean.setParamStr(new Gson().toJson(paramsBean));
        iosInfoBean.setSrc("");
        androidInfoBean.setWwwFolder("");

        extendBean.setAndroidInfo(androidInfoBean);
        extendBean.setIosInfo(iosInfoBean);

        final String extendStr=new Gson().toJson(extendBean);

        holder.imgMoreCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moreDatas.get(position).is_collection==0){//去收藏
                    OkGo.<String>post(Constant.ADD_COLL)
                            .params("member_code", TMSharedPUtil.getTMUser(context).getMember_code())
                            .params("title",moreDatas.get(position).getLive_title())
                            .params("intro",moreDatas.get(position).getLive_title())
                            .params("app_id",Constant.APP_ID)
                            .params("article_id",moreDatas.get(position).getId())
                            .params("extend",extendStr)
                            .params("tag","")//传空字符串
                            .params("type",2)//类型 1 文章 2视频
                            .params("pic",moreDatas.get(position).getLive_thumb())//缩略图
                            .headers("token",TMSharedPUtil.getTMToken(context))
                            .execute(new StringCallback(){
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String str = response.body();
                                    JSONObject object = JSON.parseObject(str);
                                    if (object.getInteger("code")==200){
                                        moreDatas.get(position).is_collection=1;
                                        notifyDataSetChanged();

                                        CollBean collBean = new Gson().fromJson(str, CollBean.class);
                                        moreDatas.get(position).setSid(collBean.getData().getStar_id());
                                        OkGo.<String>post(Constant.CollMine)
                                                .params("id",moreDatas.get(position).getId())
                                                .params("type",1)
                                                .params("token",TMSharedPUtil.getTMToken(context))
                                                .headers("token",TMSharedPUtil.getTMToken(context))
                                                .params("sid",collBean.getData().getStar_id())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String> response) {

                                                    }
                                                });
                                    }else {
                                        new GoLoginDialog(context).show();
                                    }
                                }
                            });
                }else {//取消收藏
                    OkGo.<String>post(Constant.CANCEL_COLL)
                            .params("star_id",moreDatas.get(position).getSid())
                            .headers("token",TMSharedPUtil.getTMToken(context))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    String body = response.body();
                                    JSONObject object = JSON.parseObject(body);
                                    if (object.getInteger("code")==200){
                                        moreDatas.get(position).setIs_collection(0);
                                        notifyDataSetChanged();

                                        OkGo.<String>post(Constant.CollMine)
                                                .params("id",moreDatas.get(position).getId())
                                                .params("type",1)
                                                .params("token",TMSharedPUtil.getTMToken(context))
                                                .headers("token",TMSharedPUtil.getTMToken(context))
                                                .params("sid",moreDatas.get(position).getSid())
                                                .execute(new StringCallback() {
                                                    @Override
                                                    public void onSuccess(Response<String> response) {

                                                    }
                                                });
                                    }else {
                                        new GoLoginDialog(context).show();
                                    }
                                }
                            });
                }
//                onLikeClick.clickColl(moreDatas.get(position).getId(),position);
            }
        });
        return convertView;
    }

    class LiveMoreViewHolder {
        ImageView imgLiveMore,imgMoreLanud,imgMoreCollected,imgMoreShared;
        TextView tvLiveMoreTitle,tvLooknum,tvMoreLanud,tvMoreComment;
        LinearLayout linearLayout,llLivemoreLanud,llLivemoreComment;
    }
    public interface OnLikeClick{
        void clickLike(int id,int poi);

        void clickColl(int id, int poi);
    }

}
