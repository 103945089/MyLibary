package shopping.hlhj.com.mylibrary.data;

import com.tenma.ventures.bean.utils.TMSharedPUtil;
import com.tenma.ventures.config.TMServerConfig;

public class Constant {
//    public static String POST_URL = "http://review.360tianma.com/hlhj_webcast";
//    public static String IMG_URL = "http://review.360tianma.com/";
    public static String POST_URL = "http://linhai.360tianma.com/hlhj_webcast";
    public static String IMG_URL = "http://linhai.360tianma.com/";
//    public static String POST_URL = "http://tianma.wufenhuyu.com/hlhj_webcast";
//    public static String IMG_URL = "http://tianma.wufenhuyu.com";
//    public static String POST_URL = "http://tmgkgy.360tianma.com/hlhj_webcast";
//    public static String IMG_URL = "http://tmgkgy.360tianma.com";
//    public static String POST_URL = TMServerConfig.BASE_URL+"/hlhj_webcast";
//    public static String IMG_URL = TMServerConfig.BASE_URL;

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
    //热门搜索
    public static String SEARCH_HOT = POST_URL + "/Api/hotsearch";

    //专题详情
    public static String ARTICLE_DETAIL = POST_URL + "/Api/topics_detail";
    //专题更多
    public static String ARTICLE_MORE = POST_URL + "/Api/topics_list";

    //弹幕
    public static String getDanmu=POST_URL+"/Api/vote_list";

    //发送弹幕
    public static String senDDanmu=POST_URL+"/Api/public_vote";

    //直播的评论列表
    public static String COMMENT_LIST = POST_URL + "/Api/comment_list";

    //直播的发布评论
    public static String SEND_COMMENT = POST_URL+"/Api/comment";

    //热门和文章的评论列表
    public static String OTHER_COMMENT_LIST = POST_URL + "/Api/all_comment";

    //热门和文章的发布评论
    public static String OTHER_SEND_COMMENT = POST_URL + "/Api/add_comment";

    //是否收藏
    public static String IS_COLL=IMG_URL+"/member/Memberstar/checkIsStar/";

    //添加收藏
    public static String ADD_COLL=IMG_URL+"/member/Memberstar/addStar/";

    //取消收藏
    public static String CANCEL_COLL=IMG_URL+"/member/Memberstar/deleteStar";

    //添加历史浏览记录
    public static String ADD_HIS=IMG_URL+"/member/Memberfootprint/addFootprint/";

    //点赞
    public static String ITS_GOOD=POST_URL+"/Api/laud";
    //收藏
    public static String CollMine =POST_URL+"/Api/collection";
    //导航栏
    public static String CATLIST=POST_URL+"/Api/obtain_nav";
    public static String Cat_DEtail=POST_URL+"/Api/nav_content";

    //APPID
    public static String APP_ID="hlhj.new.kankan";
}
