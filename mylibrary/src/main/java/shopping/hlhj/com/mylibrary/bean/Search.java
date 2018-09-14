package shopping.hlhj.com.mylibrary.bean;

import java.io.Serializable;
import java.util.List;

public class Search implements Serializable {
    public int code;
    public String msg;
    public SearchData searchData;

    @Override
    public String toString() {
        return "Search{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", searchData=" + searchData +
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

    public SearchData getSearchData() {
        return searchData;
    }

    public void setSearchData(SearchData searchData) {
        this.searchData = searchData;
    }

    public class SearchData {
        public List<SearchBean> searchBeanList;

        @Override
        public String toString() {
            return "SearchData{" +
                    "searchBeanList=" + searchBeanList +
                    '}';
        }

        public List<SearchBean> getSearchBeanList() {
            return searchBeanList;
        }

        public void setSearchBeanList(List<SearchBean> searchBeanList) {
            this.searchBeanList = searchBeanList;
        }

        public class SearchBean {
            public int id;
            public String title;
            public String cover;
            public String video_url;
            public int create_time;
            public int type;

            @Override
            public String toString() {
                return "SearchBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", cover='" + cover + '\'' +
                        ", video_url='" + video_url + '\'' +
                        ", create_time=" + create_time +
                        ", type=" + type +
                        '}';
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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
