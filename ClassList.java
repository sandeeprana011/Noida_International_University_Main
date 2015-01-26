package sirfireydevs.rana.noidainternationaluniversity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.widget.ProgressBar;
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


public class ClassList extends ActionBarActivity {
    SharedPreferences pref;
    DownloadClassData dat;
    ArrayList<DataList> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);

        pref = getSharedPreferences("mypref", 0);
        if (pref.getBoolean("classes", false)) {

        }

        dat = new DownloadClassData();
        dat.execute("http://niucse2ndyear.hostfree.us/classes.txt");
        executeOnClickList();

        setTitle("Select Your Class");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.class_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void populateClassList() {
        // TODO Auto-generated method stub

        BufferedReader reader = null;
        FileInputStream fileStream = null;
        try {

            fileStream = openFileInput("classes.txt");
            reader = new BufferedReader(new InputStreamReader(
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
            ListView listV = (ListView) findViewById(R.id.listview_classes);
            listV.setAdapter(adapter);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            try {
                reader.close();
                fileStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    private void executeOnClickList() {
        // TODO Auto-generated method stub
        ListView lView = (ListView) findViewById(R.id.listview_classes);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked,
                                    int position, long id) {

                DataList dalist = listData.get(position);

                String url = dalist.getUrl();
                String title = dalist.getTitle();
                String time = dalist.getTime();
                String size = dalist.getSize();
//                if (!url.endsWith(".txt")) {

                Intent i = new Intent(ClassList.this, NotesList.class);
//                    i.setData(Uri.parse(url));


//                } else {

                String[] dataArr = {title, url, time, size};
//                    Intent i = new Intent(ClassList.this, TextFileView.class);
                i.putExtra("dataArr", dataArr);
//                    startActivity(i);
                startActivity(i);
//                }

                // Toast.makeText(MainActivity.this, sh,
                // Toast.LENGTH_SHORT).show();;

                // TODO Auto-generated method stubo

            }
        });

    }


    public class MyListAdaptor extends ArrayAdapter<DataList> {
        public MyListAdaptor() {
            super(ClassList.this, R.layout.list_item_views, listData);
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
            // return super.getView(position, convertView, parent);
        }

    }


    public class DownloadClassData extends AsyncTask<String, Integer, String> {

        BufferedWriter writer;

        @Override
        protected void onCancelled() {
//            super.onCancelled();
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            TextView te = (TextView) findViewById(R.id.textTest);
//            te.setVisibility(View.VISIBLE);
//            te.setText("Refreshing Data Please Wait....");

        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("classesfile", true);
            editor.commit();

            ListView list = (ListView) findViewById(R.id.listview_classes);
            ProgressBar t = (ProgressBar) findViewById(R.id.progressbar_classes);
            t.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);

            populateClassList();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);

        }

        // TextView t=(TextView)findViewById(R.id.textTest);
        @Override
        protected String doInBackground(String... params) {

            // TODO Auto-generated method stub
            HttpClient client = new DefaultHttpClient();
            String data = "";
            try {

                URI uri = new URI(params[0]);
                HttpGet get = new HttpGet(uri);
                HttpResponse response = client.execute(get);
                writer = new BufferedWriter(
                        new OutputStreamWriter(openFileOutput("classes.txt", 0)));

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
                ins.close();

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

            }

            return data;
        }

    }

}
