package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class CommentBean implements Serializable {
    public int code;
    public String msg;
    public List<CommentData> commentData;

    @Override
    public String toString() {
        return "CommentBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", commentData=" + commentData +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CommentData> getCommentData() {
        return commentData;
    }

    public void setCommentData(List<CommentData> commentData) {
        this.commentData = commentData;
    }

    public class CommentData {
        public int id;
        public int laud_num;
        public int user_id;
        public int nid;     //热门和文章的评论
        public int uid;     //热门和文章的评论
        public String create_at;
        public String create_time; //热门和文章的评论
        public int live_id;
        public int is_laud;
        public String avatar;       //热门和文章的评论
        public String username;     //热门和文章的评论
        public String reply;        //热门和文章的评论
        public String content;
        public String head_pic;
        public String member_name;

        @Override
        public String toString() {
            return "CommentData{" +
                    "id=" + id +
                    ", laud_num=" + laud_num +
                    ", user_id=" + user_id +
                    ", nid=" + nid +
                    ", uid=" + uid +
                    ", create_at=" + create_at +
                    ", create_time=" + create_time +
                    ", live_id=" + live_id +
                    ", is_laud=" + is_laud +
                    ", avatar='" + avatar + '\'' +
                    ", username='" + username + '\'' +
                    ", reply='" + reply + '\'' +
                    ", content='" + content + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    ", member_name='" + member_name + '\'' +
                    '}';
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
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

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getLive_id() {
            return live_id;
        }

        public void setLive_id(int live_id) {
            this.live_id = live_id;
        }

        public int getIs_laud() {
            return is_laud;
        }

        public void setIs_laud(int is_laud) {
            this.is_laud = is_laud;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getMember_name() {
            return member_name;
        }

        public void setMember_name(String member_name) {
            this.member_name = member_name;
        }
    }
}
