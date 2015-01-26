package sirfireydevs.rana.noidainternationaluniversity;

/**
 * Created by root on 12/1/15.
 */
public class File_detail {
    private String name_file;
    private String size_file;
//    private String url_file;

    public File_detail(String[] strArray) {
        name_file = strArray[0];
        size_file = strArray[1];
//        url_file=baseUrlDirectory.replace("/listfile.php","/")+url_file;
    }

    public String getName_file() {
        return name_file;
    }

    public String getSize_file() {
        return size_file;
    }

//    public String getUrl_file() {
//        return url_file;
//    }
}
