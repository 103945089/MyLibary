package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class MoreBean implements Serializable {
    public int code;
    public String message;
    public String url;
    public List<MoreDatas> hotMoreDatas;

    @Override
    public String toString() {
        return "MoreBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                ", hotMoreDatas=" + hotMoreDatas +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
        public int read_num;        //观看
        public int collection_num;  //收藏
        public int laud_num;    //点赞
        public int comment_num;    //评论
        public String title;
        public int create_time;
        public String video_url;
        public String cover;
        public String summary;
        public String name;
        public String channel_thumb;
        public String live_thumb;
        public String live_title;
        public String live_source;  //直播地址
        public int live_type;
        public int live_status;
        public int is_laud;         //是点赞
        public int is_collection;   //是收藏
        public int sid;

        @Override
        public String toString() {
            return "MoreDatas{" +
                    "id=" + id +
                    ", read_num=" + read_num +
                    ", collection_num=" + collection_num +
                    ", laud_num=" + laud_num +
                    ", comment_num=" + comment_num +
                    ", title='" + title + '\'' +
                    ", create_time=" + create_time +
                    ", video_url='" + video_url + '\'' +
                    ", cover='" + cover + '\'' +
                    ", summary='" + summary + '\'' +
                    ", name='" + name + '\'' +
                    ", channel_thumb='" + channel_thumb + '\'' +
                    ", live_thumb='" + live_thumb + '\'' +
                    ", live_title='" + live_title + '\'' +
                    ", live_source='" + live_source + '\'' +
                    ", live_type=" + live_type +
                    ", live_status=" + live_status +
                    ", is_laud=" + is_laud +
                    ", is_collection=" + is_collection +
                    ", sid=" + sid +
                    '}';
        }

        public int getRead_num() {
            return read_num;
        }

        public void setRead_num(int read_num) {
            this.read_num = read_num;
        }

        public int getCollection_num() {
            return collection_num;
        }

        public void setCollection_num(int collection_num) {
            this.collection_num = collection_num;
        }

        public int getLaud_num() {
            return laud_num;
        }

        public void setLaud_num(int laud_num) {
            this.laud_num = laud_num;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getIs_laud() {
            return is_laud;
        }

        public void setIs_laud(int is_laud) {
            this.is_laud = is_laud;
        }

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        public int getSid() {
            return sid;
        }

        public void setSid(int sid) {
            this.sid = sid;
        }

        public String getLive_thumb() {
            return live_thumb;
        }

        public void setLive_thumb(String live_thumb) {
            this.live_thumb = live_thumb;
        }

        public String getLive_title() {
            return live_title;
        }

        public void setLive_title(String live_title) {
            this.live_title = live_title;
        }

        public String getLive_source() {
            return live_source;
        }

        public void setLive_source(String live_source) {
            this.live_source = live_source;
        }

        public int getLive_type() {
            return live_type;
        }

        public void setLive_type(int live_type) {
            this.live_type = live_type;
        }

        public int getLive_status() {
            return live_status;
        }

        public void setLive_status(int live_status) {
            this.live_status = live_status;
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
