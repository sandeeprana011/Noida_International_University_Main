package sirfireydevs.rana.noidainternationaluniversity;

public class DataList {

    private String time;
    private String title;
    private String size;
    private String url;
    private String[] arrData;

    /**
     * @return the time
     */
    public DataList(String[] str) {
        this.title = str[0];
        this.url = str[1];
        this.size = str[3];
        this.time = str[2];
        this.arrData = str;
    }

    public String[] getArrData() {
        return arrData;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

}