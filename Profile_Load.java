/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sirfireydevs.rana.noidainternationaluniversity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author rana
 */
public class Profile_Load {

    public ArrayList<Teacher_profile> profile() {

        ArrayList<Teacher_profile> data_profile = new ArrayList<Teacher_profile>();

        try {

            String urlString = "http://sandeeprana011.hostfree.us/diwali/teacher_notes_profile.php";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            URL url = new URL(urlString);
            InputStream ins = url.openStream();
            Document doc = dBuilder.parse(ins);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("teacher_id");
            int j = nList.getLength();

            int k = 0;
            ;

            for (int i = 0; i < j; i++) {
                Element item = (Element) nList.item(i);

                String[] p = new String[]{
                        item.getAttribute("id"),
                        item.getAttribute("name"),
                        item.getAttribute("subjects"),
                        item.getAttribute("profile"),
                        item.getAttribute("link_file"),
                        item.getAttribute("extra_fir"),
                        item.getAttribute("extra_sec")
                };

                data_profile.add(new Teacher_profile(p));


                k++;
            }

        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Profile_Load.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Profile_Load.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Profile_Load.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Profile_Load.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data_profile;
    }


}
