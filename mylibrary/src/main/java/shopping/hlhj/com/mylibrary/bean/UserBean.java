package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;

public class UserBean implements Serializable{


    /**
     * code : 200
     * data : {"token":"BBF236B7F379AEEEC451DC58ED60AB8B","member_info":{"member_id":63,"member_code":"1D0916EF9A29336083BFB0017C90EAEA","member_name":"177****1226","member_nickname":null,"site_code":"hlhj","email":null,"mobile":"17781481226","head_pic":"/uploads/default/head.jpg","create_time":1536652548,"status":0,"deleted":0,"birthday":null,"sex":null,"password":"1020b5c460abcd8f31e35fa5b182707d","wx":null,"qq":null,"wb":null}}
     * msg : 登录成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * token : BBF236B7F379AEEEC451DC58ED60AB8B
         * member_info : {"member_id":63,"member_code":"1D0916EF9A29336083BFB0017C90EAEA","member_name":"177****1226","member_nickname":null,"site_code":"hlhj","email":null,"mobile":"17781481226","head_pic":"/uploads/default/head.jpg","create_time":1536652548,"status":0,"deleted":0,"birthday":null,"sex":null,"password":"1020b5c460abcd8f31e35fa5b182707d","wx":null,"qq":null,"wb":null}
         */

        private String token;
        private MemberInfoBean member_info;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public MemberInfoBean getMember_info() {
            return member_info;
        }

        public void setMember_info(MemberInfoBean member_info) {
            this.member_info = member_info;
        }

        public static class MemberInfoBean {
            /**
             * member_id : 63
             * member_code : 1D0916EF9A29336083BFB0017C90EAEA
             * member_name : 177****1226
             * member_nickname : null
             * site_code : hlhj
             * email : null
             * mobile : 17781481226
             * head_pic : /uploads/default/head.jpg
             * create_time : 1536652548
             * status : 0
             * deleted : 0
             * birthday : null
             * sex : null
             * password : 1020b5c460abcd8f31e35fa5b182707d
             * wx : null
             * qq : null
             * wb : null
             */

            private int member_id;
            private String member_code;
            private String member_name;
            private Object member_nickname;
            private String site_code;
            private Object email;
            private String mobile;
            private String head_pic;
            private int create_time;
            private int status;
            private int deleted;
            private Object birthday;
            private Object sex;
            private String password;
            private Object wx;
            private Object qq;
            private Object wb;

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public String getMember_code() {
                return member_code;
            }

            public void setMember_code(String member_code) {
                this.member_code = member_code;
            }

            public String getMember_name() {
                return member_name;
            }

            public void setMember_name(String member_name) {
                this.member_name = member_name;
            }

            public Object getMember_nickname() {
                return member_nickname;
            }

            public void setMember_nickname(Object member_nickname) {
                this.member_nickname = member_nickname;
            }

            public String getSite_code() {
                return site_code;
            }

            public void setSite_code(String site_code) {
                this.site_code = site_code;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            public int getCreate_time() {
                return create_time;
            }

            public void setCreate_time(int create_time) {
                this.create_time = create_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getDeleted() {
                return deleted;
            }

            public void setDeleted(int deleted) {
                this.deleted = deleted;
            }

            public Object getBirthday() {
                return birthday;
            }

            public void setBirthday(Object birthday) {
                this.birthday = birthday;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Object getWx() {
                return wx;
            }

            public void setWx(Object wx) {
                this.wx = wx;
            }

            public Object getQq() {
                return qq;
            }

            public void setQq(Object qq) {
                this.qq = qq;
            }

            public Object getWb() {
                return wb;
            }

            public void setWb(Object wb) {
                this.wb = wb;
            }
        }
    }
}
