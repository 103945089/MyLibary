package shopping.hlhj.com.mylibrary.data;

public class Constant {
//    public static String POST_URL = "http://47.97.160.30/hlhj_newslive/";
//    public static String POST_URL = "http://review.360tianma.com/hlhj_webcast";
    public static String POST_URL = "http://tm.trf9.cn/hlhj_webcast";
    public static String IMG_URL = "http://tm.trf9.cn";

    //轮播&直播
    public static String BANNER_URL = POST_URL + "/Api/live_top";
    //直播更多
    public static String LIVE_MORE = POST_URL + "/Api/live";
    //直播详情
    public static String LIVE_DETAIL = POST_URL + "/Api/live_detail";
    //专题&热门
    public static String HOT_URL = POST_URL + "/Api/hot_topics";
    //热门更多
    public static String HOT_MORE = POST_URL + "/Api/hot_list";
    //推荐
    public static String TUIJIAN_URL = POST_URL + "/Api/recommend";

    //详情
    public static String DETAIL_URL = POST_URL + "/Api/detail";

    //搜索
    public static String SEARCH_URL = POST_URL + "/Api/search_article";

    //专题详情
    public static String ARTICLE_DETAIL = POST_URL + "/Api/topics_detail";
    //专题更多
    public static String ARTICLE_MORE = POST_URL + "/Api/topics_list";

    //弹幕
    public static String getDanmu=POST_URL+"/Api/vote_list";

    //发送弹幕
    public static String senDDanmu=POST_URL+"/Api/public_vote";



}
