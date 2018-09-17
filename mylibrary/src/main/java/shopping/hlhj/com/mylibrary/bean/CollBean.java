package shopping.hlhj.com.mylibrary.bean;

/**
 * Created by Never Fear   on 2018\7\19 0019.
 * Never More....
 */

public class CollBean {

    /**
     * code : 200
     * data : {"star_id":17}
     * msg : 已被收藏
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * star_id : 17
         */

        private int star_id;

        public int getStar_id() {
            return star_id;
        }

        public void setStar_id(int star_id) {
            this.star_id = star_id;
        }
    }
}
