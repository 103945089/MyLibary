package shopping.hlhj.com.mylibrary.bean;


import java.io.Serializable;
import java.util.List;

public class TopBanner implements Serializable {
    public int code;
    public String message;
    public Datas data;

    @Override
    public String toString() {
        return "TopBanner{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
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

    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
        this.data = data;
    }


    public class Datas {
        public List<BannerBean> beanList;
        public List<LiveBean> liveBeans;

        @Override
        public String toString() {
            return "Datas{" +
                    "beanList=" + beanList +
                    ", liveBeans=" + liveBeans +
                    '}';
        }

        public List<BannerBean> getBeanList() {
            return beanList;
        }

        public void setBeanList(List<BannerBean> beanList) {
            this.beanList = beanList;
        }

        public List<LiveBean> getLiveBeans() {
            return liveBeans;
        }

        public void setLiveBeans(List<LiveBean> liveBeans) {
            this.liveBeans = liveBeans;
        }

        public class BannerBean {
            public int id;
            public String title;
            public String cover;
            public String video_url;

            @Override
            public String toString() {
                return "BannerBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", cover='" + cover + '\'' +
                        ", video_url='" + video_url + '\'' +
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
        }

        public class LiveBean {
            public int id;
            public int create_at;
            public String live_title;
            public String live_thumb;
            public String live_desc;
            public String live_source;
            public String live_type;
            public String live_status;

            @Override
            public String toString() {
                return "LiveBean{" +
                        "id=" + id +
                        ", create_at=" + create_at +
                        ", live_title='" + live_title + '\'' +
                        ", live_thumb='" + live_thumb + '\'' +
                        ", live_desc='" + live_desc + '\'' +
                        ", live_source='" + live_source + '\'' +
                        ", live_type='" + live_type + '\'' +
                        ", live_status='" + live_status + '\'' +
                        '}';
            }

            public int getCreate_at() {
                return create_at;
            }

            public void setCreate_at(int create_at) {
                this.create_at = create_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLive_title() {
                return live_title;
            }

            public void setLive_title(String live_title) {
                this.live_title = live_title;
            }

            public String getLive_thumb() {
                return live_thumb;
            }

            public void setLive_thumb(String live_thumb) {
                this.live_thumb = live_thumb;
            }

            public String getLive_desc() {
                return live_desc;
            }

            public void setLive_desc(String live_desc) {
                this.live_desc = live_desc;
            }

            public String getLive_source() {
                return live_source;
            }

            public void setLive_source(String live_source) {
                this.live_source = live_source;
            }

            public String getLive_type() {
                return live_type;
            }

            public void setLive_type(String live_type) {
                this.live_type = live_type;
            }

            public String getLive_status() {
                return live_status;
            }

            public void setLive_status(String live_status) {
                this.live_status = live_status;
            }
        }
    }
}
