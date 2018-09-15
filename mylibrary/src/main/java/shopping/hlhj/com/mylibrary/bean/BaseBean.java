package shopping.hlhj.com.mylibrary.bean;

/**
 * Created by Administrator on 2018\5\28 0028.
 */

public class BaseBean {

    /**
     * code : 1
     * msg : 评论成功
     * data : []
     */

    private int code;
    private String msg;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
