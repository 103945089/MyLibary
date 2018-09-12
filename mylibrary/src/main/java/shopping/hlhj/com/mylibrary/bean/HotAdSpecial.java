package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class HotAdSpecial implements Serializable {
    public int code;
    public String message;
    public HotAdSpecialData datas;

    @Override
    public String toString() {
        return "HotAdSpecial{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", datas=" + datas +
                '}';
    }

    public HotAdSpecialData getDatas() {
        return datas;
    }

    public void setDatas(HotAdSpecialData datas) {
        this.datas = datas;
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


    public class HotAdSpecialData {
        public List<HotBean> hotBeans;
        public List<Special> specialList;

        @Override
        public String toString() {
            return "HotAdSpecialData{" +
                    "hotBeans=" + hotBeans +
                    ", specialList=" + specialList +
                    '}';
        }

        public List<HotBean> getHotBeans() {
            return hotBeans;
        }

        public void setHotBeans(List<HotBean> hotBeans) {
            this.hotBeans = hotBeans;
        }

        public List<Special> getSpecialList() {
            return specialList;
        }

        public void setSpecialList(List<Special> specialList) {
            this.specialList = specialList;
        }

        public class HotBean {
            public int id;
            public String cover;
            public String title;
            public int create_time;
            public String video_url;
            public int type;

            @Override
            public String toString() {
                return "HotBean{" +
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

        public class Special {
            public int id;
            public String channel_thumb;

            @Override
            public String toString() {
                return "Special{" +
                        "id=" + id +
                        ", channel_thumb='" + channel_thumb + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChannel_thumb() {
                return channel_thumb;
            }

            public void setChannel_thumb(String channel_thumb) {
                this.channel_thumb = channel_thumb;
            }
        }
    }
}
