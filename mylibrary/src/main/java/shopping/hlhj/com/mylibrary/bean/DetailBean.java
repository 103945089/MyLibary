package shopping.hlhj.com.mylibrary.bean;

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
        public List<CommentBean> commentBeans;

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
                    ", commentBeans=" + commentBeans +
                    '}';
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

        public List<CommentBean> getCommentBeans() {
            return commentBeans;
        }

        public void setCommentBeans(List<CommentBean> commentBeans) {
            this.commentBeans = commentBeans;
        }

        public class CommentBean {
            public int id;
            public int nid;
            public int uid;
            public String avatar;
            public String username;
            public String content;
            public String reply;
            public int create_time;

            @Override
            public String toString() {
                return "CommentBean{" +
                        "id=" + id +
                        ", nid=" + nid +
                        ", uid=" + uid +
                        ", avatar='" + avatar + '\'' +
                        ", username='" + username + '\'' +
                        ", content='" + content + '\'' +
                        ", reply='" + reply + '\'' +
                        ", create_time=" + create_time +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }
        }
    }
}
