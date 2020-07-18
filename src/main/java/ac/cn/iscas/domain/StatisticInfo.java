package ac.cn.iscas.domain;

public class StatisticInfo implements Comparable<StatisticInfo>{
    private String title;
    private String time;
    private String context;
    private String unit;
    private String keyword;
    private String url;

    @Override
    public int compareTo(StatisticInfo o) {
        return getTime().compareTo(o.getTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
