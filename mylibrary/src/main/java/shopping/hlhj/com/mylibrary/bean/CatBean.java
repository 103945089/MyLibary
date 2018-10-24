package shopping.hlhj.com.mylibrary.bean;

import java.util.List;

/**
 * Created by Never Fear   on 2018\10\24 0024.
 * Never More....
 */

public class CatBean {
    @Override
    public String toString() {
        return "CatBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 200
     * message : 获取导航成功！
     * data : [{"id":4,"nav_name":"热门视频"},{"id":5,"nav_name":"体育"},{"id":10,"nav_name":"都是"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 4
         * nav_name : 热门视频
         */

        private int id;
        private String nav_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNav_name() {
            return nav_name;
        }

        public void setNav_name(String nav_name) {
            this.nav_name = nav_name;
        }
    }
}
