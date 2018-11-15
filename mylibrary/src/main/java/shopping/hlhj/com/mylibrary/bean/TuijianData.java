package shopping.hlhj.com.mylibrary.bean;

import java.util.List;

/**
 * Created by Never Fear   on 2018\11\5 0005.
 * Never More....
 */

public class TuijianData {

    /**
     * code : 200
     * message : 查询推荐成功
     * data : {"hot":[{"id":67,"cover":"http://pdhfer5jc.bkt.clouddn.com/d88d3201811051538007013.jpg","title":"接受你的小脾气","release":"G客星球","create_time":1541403481,"video_url":"http://pdhfer5jc.bkt.clouddn.com/53b1c201811051537589235.mp4","type":14},{"id":66,"cover":"http://pdhfer5jc.bkt.clouddn.com/0533620181105153306182.jpg","title":"真假\u201c蒙娜丽莎\u201d","release":"G客星球","create_time":1541403186,"video_url":"http://pdhfer5jc.bkt.clouddn.com/8ad21201811051532552075.mp4","type":14},{"id":65,"cover":"http://pdhfer5jc.bkt.clouddn.com/d2fb2201811051513287323.jpg","title":"只因在人群中多看了你一眼","release":"G客星球","create_time":1541402009,"video_url":"http://pdhfer5jc.bkt.clouddn.com/83b2c201811051513265199.mp4","type":14}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<HotBean> hot;

        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public static class HotBean {
            /**
             * id : 67
             * cover : http://pdhfer5jc.bkt.clouddn.com/d88d3201811051538007013.jpg
             * title : 接受你的小脾气
             * release : G客星球
             * create_time : 1541403481
             * video_url : http://pdhfer5jc.bkt.clouddn.com/53b1c201811051537589235.mp4
             * type : 14
             */

            private int id;
            private String cover;
            private String title;
            private String release;
            private String create_time;
            private String video_url;
            private int type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getRelease() {
                return release;
            }

            public void setRelease(String release) {
                this.release = release;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getVideo_url() {
                return video_url;
            }

            public void setVideo_url(String video_url) {
                this.video_url = video_url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
