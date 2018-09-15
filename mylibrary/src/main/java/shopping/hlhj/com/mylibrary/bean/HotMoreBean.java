package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;

public class HotMoreBean implements Serializable {
    public int code;
    public String message;
    public HotMoreDatas hotMoreDatas;

    @Override
    public String toString() {
        return "HotMoreBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", hotMoreDatas=" + hotMoreDatas +
                '}';
    }

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

    public HotMoreDatas getHotMoreDatas() {
        return hotMoreDatas;
    }

    public void setHotMoreDatas(HotMoreDatas hotMoreDatas) {
        this.hotMoreDatas = hotMoreDatas;
    }

    public class HotMoreDatas {
        public int id;
        public String title;
        public int create_time;
        public String video_url;
        public String cover;

        @Override
        public String toString() {
            return "HotMoreDatas{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", create_time=" + create_time +
                    ", video_url='" + video_url + '\'' +
                    ", cover='" + cover + '\'' +
                    '}';
        }

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

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }
}
