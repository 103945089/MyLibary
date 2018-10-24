package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;

public class LiveDetailBean implements Serializable{

    public int code;
    public String msg;
    public LiveDetail liveDetail;

    @Override
    public String toString() {
        return "LiveDetailBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", liveDetail=" + liveDetail +
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

    public LiveDetail getLiveDetail() {
        return liveDetail;
    }

    public void setLiveDetail(LiveDetail liveDetail) {
        this.liveDetail = liveDetail;
    }

    public class LiveDetail {
        public int laud_num;
        public String live_title;
        public String live_desc;
        public String live_source;
        public int live_type;
        public int online;
        public int live_status;
        public int comment_status;
        public int vote_status;
        public int is_laud;
        public int is_collection;

        public int getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(int is_collection) {
            this.is_collection = is_collection;
        }

        @Override
        public String toString() {
            return "LiveDetail{" +
                    "laud_num=" + laud_num +
                    ", live_title='" + live_title + '\'' +
                    ", live_desc='" + live_desc + '\'' +
                    ", live_source='" + live_source + '\'' +
                    ", live_type=" + live_type +
                    ", online=" + online +
                    ", live_status=" + live_status +
                    ", comment_status=" + comment_status +
                    ", vote_status=" + vote_status +
                    ", is_laud=" + is_laud +
                    ", is_collection=" + is_collection +
                    '}';
        }

        public int getLaud_num() {
            return laud_num;
        }

        public void setLaud_num(int laud_num) {
            this.laud_num = laud_num;
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

        public int getComment_status() {
            return comment_status;
        }

        public void setComment_status(int comment_status) {
            this.comment_status = comment_status;
        }

        public int getVote_status() {
            return vote_status;
        }

        public void setVote_status(int vote_status) {
            this.vote_status = vote_status;
        }

        public int getIs_laud() {
            return is_laud;
        }

        public void setIs_laud(int is_laud) {
            this.is_laud = is_laud;
        }
    }
}
