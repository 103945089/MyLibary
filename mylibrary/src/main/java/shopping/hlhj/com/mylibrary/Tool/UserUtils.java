package shopping.hlhj.com.mylibrary.Tool;


public class UserUtils {
    //单列模式获取用户
    private static UserUtils userUtils = null;
    public static UserUtils getInstance()
    {
        if (userUtils == null)
        {
            synchronized(UserUtils.class) {  //1
                if (userUtils == null)          //2
                    userUtils = new UserUtils();  //3
            }
        }
        return userUtils;
    }


}
