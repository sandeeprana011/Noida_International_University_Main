package sirfireydevs.rana.noidainternationaluniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class NotesList extends ActionBarActivity {
    SharedPreferences pref;
    List<DataList> listData = null;

    String url;
    FileInputStream fileStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("mypref", 0);
        // pref.getBoolean("initialize", false);


        Intent intent = getIntent();
        String[] strArr = intent.getStringArrayExtra("dataArr");
        url = strArr[1];
        String title = strArr[0];
        String date = strArr[2];
        String size = strArr[3];

        setTitle(title);

        if (pref.getBoolean("title", false)) {

        }
        downloadData(url);
        executeOnClickList();

    }

    //Error may occur check
    public void home(View vi) {
        Intent intent = new Intent(NotesList.this, sirfireydevs.rana.noidainternationaluniversity.MainActivity.class);
        startActivity(intent);
    }

    private void executeOnClickList() {
        // TODO Auto-generated method stub
        ListView lView = (ListView) findViewById(R.id.listView1);
        lView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                DataList dalist = listData.get(position);

                String url = dalist.getUrl();
                String title = dalist.getTitle();
                String time = dalist.getTime();
                String size = dalist.getSize();
                if (!url.endsWith(".txt")) {

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                } else {
                    String[] dataArr = {title, url, time, size};
                    Intent i = new Intent(NotesList.this, TextFileView.class);
                    i.putExtra("dataArr", dataArr);
                    startActivity(i);

                }

                // Toast.makeText(MainActivity.this, sh,
                // Toast.LENGTH_SHORT).show();;

                // TODO Auto-generated method stub

            }
        });

    }

    public void setDa(View view) {

    }

    private void downloadData(String url) {
        DownloadData download = new DownloadData();
        download.execute(url);

        // TODO Auto-generated method stub

    }

    public void exit(View v) {
        System.exit(-1);
    }

    private void populateList() {
        // TODO Auto-generated method stub
        try {

            fileStream = openFileInput("data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    fileStream));

            listData = new ArrayList<DataList>();

            String[] strArr = null;
            String lin;
            // DataList dat;
            while ((lin = reader.readLine()) != null) {
                strArr = lin.split("~");
                if (strArr.length >= 4) {
                    listData.add(new DataList(strArr));
                }
            }
            reader.close();
            if (fileStream != null) {
                fileStream.close();
            }

            ArrayAdapter<DataList> adapter = new MyListAdaptor();
            ListView listV = (ListView) findViewById(R.id.listView1);
            listV.setAdapter(adapter);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (
                    fileStream != null) {
                try {
                    fileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    public void refresh(View view) {
        downloadData(url);
        populateList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void share(View view) {
        String urlShare = "Download Noida International University and experience the brand new flat UI,Know about NIU,Fee Structure,Academics,Admission process,Locate NIU in a single click,Download NIU-CSE class notes and much more....https://play.google.com/store/apps/details?id=sirfireys.rana.noidainternationaluniversity ";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, urlShare);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void showHelp(View view) {
        // TODO Auto-generated method stub

        Intent intent = new Intent(NotesList.this, AboutUsActivity.class);
        intent.putExtra("START", "HELP_START");
        startActivity(intent);
    }

    public void aboutUs(View vi) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(NotesList.this, AboutUsActivity.class);
        intent.putExtra("START", "ABOUT_US_START");
        startActivity(intent);

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container,
//                    false);
            return null;
        }
    }

    public class DownloadData extends AsyncTask<String, Integer, String> {

        BufferedWriter writer;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            TextView te = (TextView) findViewById(R.id.textTest);
            te.setVisibility(View.VISIBLE);
            te.setText("Refreshing Data Please Wait....");

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("initialize", true);
            editor.commit();

            TextView t = (TextView) findViewById(R.id.textTest);
            t.setVisibility(View.GONE);
            populateList();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // TextView t=(TextView)findViewById(R.id.textTest);
        @Override
        protected String doInBackground(String... params) {
//            BufferedWriter writer=null;
            // TODO Auto-generated method stub
            HttpClient client = new DefaultHttpClient();
            String data = "";
            try {
                URI uri = new URI(params[0]);
                HttpGet get = new HttpGet(uri);
                HttpResponse response = client.execute(get);
                writer = new BufferedWriter(
                        new OutputStreamWriter(openFileOutput("data.txt", 0)));

                InputStream ins = response.getEntity().getContent();
                BufferedReader bufReader = new BufferedReader(
                        new InputStreamReader(ins));
                StringBuffer buffer = new StringBuffer("");
                String line = "";
                String linesep = System.getProperty("line.separator");
                while ((line = bufReader.readLine()) != null) {
                    buffer.append(line + linesep);
                    writer.write(line + linesep);
                }
                writer.close();
                bufReader.close();

                data = buffer.toString();

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return data;
        }

    }

    public class MyListAdaptor extends ArrayAdapter<DataList> {
        public MyListAdaptor() {
            super(NotesList.this, R.layout.list_item_views, listData);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.list_item_views,
                        parent, false);
            }

            DataList curData = listData.get(position);
            ImageView image = (ImageView) view
                    .findViewById(R.id.imageView_icon_file);
            Drawable dr = null;
            if (curData.getUrl().endsWith(".txt")) {
                dr = getResources().getDrawable(R.drawable.txt);
            } else if (curData.getUrl().endsWith(".pdf")) {
                dr = getResources().getDrawable(R.drawable.pdf);
            } else if (curData.getUrl().endsWith(".pptx")) {
                dr = getResources().getDrawable(R.drawable.ppt);
            } else if (curData.getUrl().endsWith(".zip")) {
                dr = getResources().getDrawable(R.drawable.zip);
            } else if (curData.getUrl().endsWith(".html")) {
                dr = getResources().getDrawable(R.drawable.html);
            } else if (curData.getUrl().endsWith(".htm")) {
                dr = getResources().getDrawable(R.drawable.html);
            } else if (curData.getUrl().endsWith(".epub")) {
                dr = getResources().getDrawable(R.drawable.epub);
            } else if (curData.getUrl().endsWith(".jpg")) {
                dr = getResources().getDrawable(R.drawable.icon_image);

            } else if (curData.getUrl().endsWith(".docx")) {
                dr = getResources().getDrawable(R.drawable.docx);

            } else {
                dr = getResources().getDrawable(R.drawable.op_about);
            }
            image.setImageDrawable(dr);

            image.setMaxHeight(40);
            image.setMaxWidth(40);

            TextView titleText = (TextView) view.findViewById(R.id.text_title);
            titleText.setText(curData.getTitle());
            TextView timeText = (TextView) view.findViewById(R.id.text_time);
            timeText.setText(curData.getTime());
            TextView sizeText = (TextView) view.findViewById(R.id.text_size);
            sizeText.setText(curData.getSize());

            return view;

        }

    }

}