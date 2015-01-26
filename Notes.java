package sirfireydevs.rana.noidainternationaluniversity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Notes extends ActionBarActivity {
    String link;
    ArrayList<File_detail> file_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Intent intent = getIntent();
        link = intent.getStringExtra("link");

//        Toast.makeText(getApplicationContext(),link,Toast.LENGTH_SHORT).show();

        File_load file_load = new File_load(link);
        file_load.execute("");
        file_details = file_load.details();


//        Download download=new Download();
//        download.execute(link);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_admitcard) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class Download extends AsyncTask<String, Integer, String> {

        String fileName;
        File_load file_load;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            file_load = new File_load(link);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            fileName = file_details.get(0).getName_file();
//            Toast.makeText(getApplicationContext(),fileName,Toast.LENGTH_SHORT).show();
        }
    }

    public class File_load extends AsyncTask {

        ArrayList<File_detail> file_detail;
        String urlLink;

        public File_load(String link) {
            urlLink = link;
        }

        public ArrayList<File_detail> details() {

            return file_detail;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            file_detail = new ArrayList<File_detail>();
        }

        @Override
        protected Object doInBackground(Object[] params) {


            try {

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                URL url = new URL(urlLink);
                InputStream ins = url.openStream();
                Document doc = dBuilder.parse(ins);
                doc.getDocumentElement().normalize();

                NodeList nList = doc.getElementsByTagName("file");
                int j = nList.getLength();

                int k = 0;

                for (int i = 0; i < j; i++) {
                    Element item = (Element) nList.item(i);

                    String[] p = new String[]{
                            item.getAttribute("name"),
                            item.getAttribute("size")
                    };

                    file_detail.add(new File_detail(p));


                    k++;
                }

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

//        return file_detail;
//        details();
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//        String fileName=file_detail.get(0).getName_file();
//        Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();

            ArrayAdapter<File_detail> adapter = new MyNotesAdapter();
            ListView listView = (ListView) findViewById(R.id.notes_listview);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link.replace("/listfile.php", "/") + file_detail.get(position).getName_file()));
                    startActivity(intent);
                }
            });


        }
    }

    public class MyNotesAdapter extends ArrayAdapter<File_detail> {
        public MyNotesAdapter() {
            super(Notes.this, R.layout.list_item_views, file_details);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.file_detail_layout,
                        parent, false);
            }

            File_detail curData = file_details.get(position);
            ImageView image = (ImageView) view
                    .findViewById(R.id.file_det_icon);
            Drawable dr = null;
            if (curData.getName_file().endsWith(".txt")) {
                dr = getResources().getDrawable(R.drawable.txt);
            } else if (curData.getName_file().endsWith(".pdf")) {
                dr = getResources().getDrawable(R.drawable.pdf);
            } else if (curData.getName_file().endsWith(".pptx")) {
                dr = getResources().getDrawable(R.drawable.ppt);
            } else if (curData.getName_file().endsWith(".zip")) {
                dr = getResources().getDrawable(R.drawable.zip);
            } else if (curData.getName_file().endsWith(".html")) {
                dr = getResources().getDrawable(R.drawable.html);
            } else if (curData.getName_file().endsWith(".htm")) {
                dr = getResources().getDrawable(R.drawable.html);
            } else if (curData.getName_file().endsWith(".epub")) {
                dr = getResources().getDrawable(R.drawable.epub);
            } else if (curData.getName_file().endsWith(".jpg")) {
                dr = getResources().getDrawable(R.drawable.icon_image);

            } else if (curData.getName_file().endsWith(".docx")) {
                dr = getResources().getDrawable(R.drawable.docx);

            } else {
                dr = getResources().getDrawable(R.drawable.op_about);
            }
            image.setImageDrawable(dr);

            image.setMaxHeight(40);
            image.setMaxWidth(40);

            TextView titleText = (TextView) view.findViewById(R.id.fil_name_textview);
            titleText.setText(curData.getName_file());
            TextView timeText = (TextView) view.findViewById(R.id.fil_size_textview);
            timeText.setText(curData.getSize_file() + " Bytes");
//            TextView sizeText = (TextView) view.findViewById(R.id.text_size);
//            sizeText.setText(curData.getSize());

            return view;

        }

    }


}
