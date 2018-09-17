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
        public int create_at;
        public int live_id;
        public int is_laud;
        public String content;
        public String head_pic;
        public String member_name;

        @Override
        public String toString() {
            return "CommentBean{" +
                    "id=" + id +
                    ", laud_num=" + laud_num +
                    ", user_id=" + user_id +
                    ", create_at=" + create_at +
                    ", live_id=" + live_id +
                    ", is_laud=" + is_laud +
                    ", content='" + content + '\'' +
                    ", head_pic='" + head_pic + '\'' +
                    ", member_name='" + member_name + '\'' +
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCreate_at() {
            return create_at;
        }

        public void setCreate_at(int create_at) {
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
