package sirfireydevs.rana.noidainternationaluniversity;

/**
 * Created by root on 11/1/15.
 */
public class Teacher_profile {
    private String id;
    private String name;
    private String subjects;
    private String link_file;
    private String profile;
    private String extra_fir;
    private String extra_sec;


    public Teacher_profile(String[] string) {
        for (int i = 0; i < string.length; i++) {
            id = string[0];
            name = string[1];
            subjects = string[2];
            profile = string[3];
            link_file = string[4];
            extra_fir = string[5];
            extra_sec = string[6];
        }
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubjects() {
        return subjects;
    }

    public String getLink_file() {
        return link_file;
    }

    public String getProfile() {
        return profile;
    }

    public String getExtra_fir() {
        return extra_fir;
    }

    public String getExtra_sec() {
        return extra_sec;
    }
}
