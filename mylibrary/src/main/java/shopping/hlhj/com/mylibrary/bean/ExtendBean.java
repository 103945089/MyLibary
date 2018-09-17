package shopping.hlhj.com.mylibrary.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Never Fear   on 2018\8\30 0030.
 * Never More....
 */

public class ExtendBean {
    /**
     * iosInfo : {"native":true,"src":"HViewController","paramStr":"","wwwFolder":""}
     * androidInfo : {"native":false,"src":"index.html","paramStr":"21e364890c730daff9e413660e04d924","wwwFolder":"comp01/page/"}
     */

    private IosInfoBean iosInfo;
    private AndroidInfoBean androidInfo;

    public IosInfoBean getIosInfo() {
        return iosInfo;
    }

    public void setIosInfo(IosInfoBean iosInfo) {
        this.iosInfo = iosInfo;
    }

    public AndroidInfoBean getAndroidInfo() {
        return androidInfo;
    }

    public void setAndroidInfo(AndroidInfoBean androidInfo) {
        this.androidInfo = androidInfo;
    }

    public static class IosInfoBean {
        /**
         * native : true
         * src : HViewController
         * paramStr :
         * wwwFolder :
         */

        @SerializedName("native")
        private boolean nativeX;
        private String src;
        private String paramStr;
        private String wwwFolder;

        public boolean isNativeX() {
            return nativeX;
        }

        public void setNativeX(boolean nativeX) {
            this.nativeX = nativeX;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getParamStr() {
            return paramStr;
        }

        public void setParamStr(String paramStr) {
            this.paramStr = paramStr;
        }

        public String getWwwFolder() {
            return wwwFolder;
        }

        public void setWwwFolder(String wwwFolder) {
            this.wwwFolder = wwwFolder;
        }
    }

    public static class AndroidInfoBean {
        /**
         * native : false
         * src : index.html
         * paramStr : 21e364890c730daff9e413660e04d924
         * wwwFolder : comp01/page/
         */

        @SerializedName("native")
        private boolean nativeX;
        private String src;
        private String paramStr;
        private String wwwFolder;

        public boolean isNativeX() {
            return nativeX;
        }

        public void setNativeX(boolean nativeX) {
            this.nativeX = nativeX;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public String getParamStr() {
            return paramStr;
        }

        public void setParamStr(String paramStr) {
            this.paramStr = paramStr;
        }

        public String getWwwFolder() {
            return wwwFolder;
        }

        public void setWwwFolder(String wwwFolder) {
            this.wwwFolder = wwwFolder;
        }
    }
}
