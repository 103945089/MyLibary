package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class RecommendBean implements Serializable {
    public int code;
    public String message;
    public RecommendData data;

    @Override
    public String toString() {
        return "RecommendBean{" +
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

    public RecommendData getData() {
        return data;
    }

    public void setData(RecommendData data) {
        this.data = data;
    }

    public class RecommendData {
        List<RecommenDatas> datas;

        @Override
        public String toString() {
            return "RecommendData{" +
                    "datas=" + datas +
                    '}';
        }

        public List<RecommenDatas> getDatas() {
            return datas;
        }

        public void setDatas(List<RecommenDatas> datas) {
            this.datas = datas;
        }

        public class RecommenDatas {
            public int id;
            public String cover;
            public String title;
            public int create_time;
            public String video_url;
            public int type;

            @Override
            public String toString() {
                return "RecommendData{" +
                        "id=" + id +
                        ", cover='" + cover + '\'' +
                        ", title='" + title + '\'' +
                        ", create_time=" + create_time +
                        ", video_url='" + video_url + '\'' +
                        ", type=" + type +
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
