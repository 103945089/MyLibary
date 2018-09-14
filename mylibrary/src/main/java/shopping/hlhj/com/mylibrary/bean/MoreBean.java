package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class MoreBean implements Serializable {
    public int code;
    public String message;
    public List<MoreDatas> hotMoreDatas;

    @Override
    public String toString() {
        return "MoreBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", hotMoreDatas=" + hotMoreDatas +
                '}';
    }

    public List<MoreDatas> getHotMoreDatas() {
        return hotMoreDatas;
    }

    public void setHotMoreDatas(List<MoreDatas> hotMoreDatas) {
        this.hotMoreDatas = hotMoreDatas;
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


    public class MoreDatas {
        public int id;
        public String title;
        public int create_time;
        public String video_url;
        public String cover;
        public String summary;
        public String name;
        public String channel_thumb;

        @Override
        public String toString() {
            return "MoreDatas{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", create_time=" + create_time +
                    ", video_url='" + video_url + '\'' +
                    ", cover='" + cover + '\'' +
                    ", summary='" + summary + '\'' +
                    ", name='" + name + '\'' +
                    ", channel_thumb='" + channel_thumb + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChannel_thumb() {
            return channel_thumb;
        }

        public void setChannel_thumb(String channel_thumb) {
            this.channel_thumb = channel_thumb;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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
