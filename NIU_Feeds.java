package sirfireydevs.rana.noidainternationaluniversity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NIU_Feeds {

    //    private String data;
    private String text;

//    public static void main(String[] args) throws MalformedURLException, IOException {
//
//        RegexTestHarness reg=new RegexTestHarness();
//        System.out.println(reg.htmlData());
//
//
//    }

    public String htmlData() {
        BufferedReader reader = null;
        try {
            StringBuffer buf = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(new URL("http://niu.ac.in/#").openStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {

                buf.append(line);
            }
            String data = String.valueOf(buf);

            Pattern pattern = null;
            pattern = Pattern.compile("(?=<h5><span style=\"color:#FFCC00;\">Announcements</span>).*(?=Noida International University proudly announces admissions in BBA LLB)");
            Matcher matcher
                    = pattern.matcher(buf.toString());


            while (matcher.find()) {

                text = matcher.group();

                text = "<html><body bgcolor=\"#336699\"><font color=\"white\"><b>" + text + " --> </marquee></b></font></body></html>";

                Pattern p = Pattern.compile("marquee");
                Matcher ma = p.matcher(text);
                text = ma.replaceAll("font");


            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(NIU_Feeds.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(NIU_Feeds.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(NIU_Feeds.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return text;
    }
}