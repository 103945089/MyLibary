package shopping.hlhj.com.mylibrary.bean;

import java.util.List;

/**
 * Created by Never Fear   on 2018\10\24 0024.
 * Never More....
 */

public class CatListBean {


    /**
     * code : 200
     * message : 获取数据成功！
     * data : [{"id":21,"title":"测试精选","cover":"http://cdn.376600500.com/f86c6201806111114211996.png","video_url":"","create_time":"1528686862"}]
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
         * id : 21
         * title : 测试精选
         * cover : http://cdn.376600500.com/f86c6201806111114211996.png
         * video_url :
         * create_time : 1528686862
         */

        private int id;
        private String title;
        private String cover;
        private String video_url;
        private String create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
