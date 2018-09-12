package shopping.hlhj.com.mylibrary.bean;

import java.util.List;

/**
 * Created by Administrator on 2018\5\28 0028.
 */

public class DanMuBean {

    /**
     * code : 1
     * msg : 获取成功
     * data : [{"content":"11111"},{"content":"呢呢呢呢呢呢"},{"content":"就急急忙忙"},{"content":"就急急忙忙"},{"content":"就急急忙忙vvvv"},{"content":"更换哈哈哈哈"},{"content":"哈哈哈你呢现场v"},{"content":"昆明妈妈"},{"content":"行家就行哈更厚您喊您贵宾你侯斌您狗比斌刚比斌更不搬家刚喊姐姐刚后半钢板耨不表白表白更表白版后表白吧刚"},{"content":"表白表白吧"},{"content":"仅仅您"},{"content":"表白表白吧崩追周边贵州刚表白吧贵州吧规表白斌峰追周边中"},{"content":"航班棒棒"},{"content":"叔叔"},{"content":"斤斤计较"},{"content":"喊姐姐"},{"content":"擅拒绝"},{"content":"斤斤计较姐姐"},{"content":"就表白表白"},{"content":"bbbbbw"},{"content":"表白表白"},{"content":"航航航"},{"content":"斤斤计较家"},{"content":"斤斤计较"},{"content":"刚哼"},{"content":"就看您"},{"content":"靠靠长辈您耨不必追"},{"content":"您您厚您刚斌呢刚斌航"},{"content":"喊就好"},{"content":"周边表白表白进耨"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * content : 11111
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
