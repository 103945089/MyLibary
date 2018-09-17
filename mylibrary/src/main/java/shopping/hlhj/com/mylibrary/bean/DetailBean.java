package shopping.hlhj.com.mylibrary.bean;

import org.w3c.dom.Comment;

import java.io.Serializable;
import java.util.List;

public class DetailBean implements Serializable {
    public int code;
    public String message;
    public DetailDatas datas;

    @Override
    public String toString() {
        return "DetailBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", datas=" + datas +
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

    public DetailDatas getDatas() {
        return datas;
    }

    public void setDatas(DetailDatas datas) {
        this.datas = datas;
    }

    public class DetailDatas {
        public int id;
        public String title;
        public String cover;
        public int type;
        public int topics;
        public String video_url;
        public int create_time;
        public String source;
        public String release;
        public String content;
        public int is_top;
        public int nav_id;
        public int read_num;
        public int is_laud;
        public int is_collection;
        public int status;
        public int like;
        public String live_title;
        public String live_desc;
        public String live_source;
        public int live_type;
        public int online;
        public int live_status;
        public int vote_status;
        public int comment_status;
        public List<TextDetailComment> comment;

        @Override
        public String toString() {
            return "DetailDatas{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", cover='" + cover + '\'' +
                    ", type=" + type +
                    ", topics=" + topics +
                    ", video_url='" + video_url + '\'' +
                    ", create_time=" + create_time +
                    ", source='" + source + '\'' +
                    ", release='" + release + '\'' +
                    ", content='" + content + '\'' +
                    ", is_top=" + is_top +
                    ", nav_id=" + nav_id +
                    ", read_num=" + read_num +
                    ", is_laud=" + is_laud +
                    ", is_collection=" + is_collection +
                    ", status=" + status +
                    ", like=" + like +
                    ", live_title='" + live_title + '\'' +
                    ", live_desc='" + live_desc + '\'' +
                    ", live_source='" + live_source + '\'' +
                    ", live_type=" + live_type +
                    ", online=" + online +
                    ", live_status=" + live_status +
                    ", vote_status=" + vote_status +
                    ", comment_status=" + comment_status +
                    ", comment=" + comment +
                    '}';
        }

        public List<TextDetailComment> getComment() {
            return comment;
        }

        public void setComment(List<TextDetailComment> comment) {
            this.comment = comment;
        }

        public String getLive_title() {
            return live_title;
        }

        public void setLive_title(String live_title) {
            this.live_title = live_title;
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

        public int getLive_type() {
            return live_type;
        }

        public void setLive_type(int live_type) {
            this.live_type = live_type;
        }

        public int getOnline() {
            return online;
        }

        public void setOnline(int online) {
            this.online = online;
        }

        public int getLive_status() {
            return live_status;
        }

        public void setLive_status(int live_status) {
            this.live_status = live_status;
        }

        public int getVote_status() {
            return vote_status;
        }

        public void setVote_status(int vote_status) {
            this.vote_status = vote_status;
        }

        public int getComment_status() {
            return comment_status;
        }

        public void setComment_status(int comment_status) {
            this.comment_status = comment_status;
        }

        public int getNav_id() {
            return nav_id;
        }

        public void setNav_id(int nav_id) {
            this.nav_id = nav_id;
        }

        public int getRead_num() {
            return read_num;
        }

        public void setRead_num(int read_num) {
            this.read_num = read_num;
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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getTopics() {
            return topics;
        }

        public void setTopics(int topics) {
            this.topics = topics;
        }

        public String getVideo_url() {
            return video_url;
        }

        public void setVideo_url(String video_url) {
            this.video_url = video_url;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getRelease() {
            return release;
        }

        public void setRelease(String release) {
            this.release = release;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getIs_top() {
            return is_top;
        }

        public void setIs_top(int is_top) {
            this.is_top = is_top;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public class TextDetailComment {
            public int id;
            public int laud_num;
            public int nid;
            public int uid;
            public int create_time;
            public String avatar;
            public String username;
            public String content;
            public String reply;

            @Override
            public String toString() {
                return "TextDetailComment{" +
                        "id=" + id +
                        ", laud_num=" + laud_num +
                        ", nid=" + nid +
                        ", uid=" + uid +
                        ", create_time=" + create_time +
                        ", avatar='" + avatar + '\'' +
                        ", username='" + username + '\'' +
                        ", content='" + content + '\'' +
                        ", reply='" + reply + '\'' +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getLaud_num() {
                return laud_num;
            }

            public void setLaud_num(int laud_num) {
                this.laud_num = laud_num;
            }

            public int getNid() {
                return nid;
            }

            public void setNid(int nid) {
                this.nid = nid;
            }

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }
        }
    }

}
