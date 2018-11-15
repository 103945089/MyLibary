package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class ArticleBean implements Serializable {
    public int code;
    public String message;
    public ArticleDatas articleDatas;

    @Override
    public String toString() {
        return "ArticleBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", articleDatas=" + articleDatas +
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

    public ArticleDatas getArticleDatas() {
        return articleDatas;
    }

    public void setArticleDatas(ArticleDatas articleDatas) {
        this.articleDatas = articleDatas;
    }

    public class ArticleDatas {
        public int id;
        public String name;
        public String summary;
        public int create_time;
        public String channel_thumb;
        public List<ArticleDetailBean> detailBean;

        @Override
        public String toString() {
            return "ArticleDatas{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", summary='" + summary + '\'' +
                    ", create_time=" + create_time +
                    ", channel_thumb='" + channel_thumb + '\'' +
                    ", detailBean=" + detailBean +
                    '}';
        }

        public List<ArticleDetailBean> getDetailBean() {
            return detailBean;
        }

        public void setDetailBean(List<ArticleDetailBean> detailBean) {
            this.detailBean = detailBean;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getChannel_thumb() {
            return channel_thumb;
        }

        public void setChannel_thumb(String channel_thumb) {
            this.channel_thumb = channel_thumb;
        }


        public class ArticleDetailBean {
            public int id;
            public String cover;
            public String title;
            public int create_time;
            public String video_url;
            public String release;

            public String getRelease() {
                return release;
            }

            public void setRelease(String release) {
                this.release = release;
            }

            @Override
            public String toString() {
                return "ArticleDetailBean{" +
                        "id=" + id +
                        ", cover='" + cover + '\'' +
                        ", title='" + title + '\'' +
                        ", create_time=" + create_time +
                        ", video_url='" + video_url + '\'' +
                        '}';
            }

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
        }
    }
}
