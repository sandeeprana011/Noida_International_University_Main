package sirfireydevs.rana.noidainternationaluniversity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class TextFileView extends ActionBarActivity {
    String data;
    TextView txt_title;
    TextView txt_size;
    TextView txt_date;
    TextView txt_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_file_view);

        Intent intent = getIntent();
        String[] strArr = intent.getStringArrayExtra("dataArr");
        String url = strArr[1];
        String title = strArr[0];
        String date = strArr[2];
        String size = strArr[3];
        txt_title = (TextView) findViewById(R.id.text_txt_data);
        txt_date = (TextView) findViewById(R.id.text_txt_data_date);

        txt_data = (TextView) findViewById(R.id.text_a);

        txt_size = (TextView) findViewById(R.id.text_txt_data_size);

        txt_date.setText(date);
        txt_size.setText(size);
        txt_title.setText(title);


//        Toast.makeText(this, strArr[1], Toast.LENGTH_SHORT).show();

        TextDownload download = new TextDownload();
        download.execute(url);

        // txt_data=(TextView)findViewById(R.id.t);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.text_file_view, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
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
            // View rootView = inflater.inflate(R.layout.text_file_view,
            // container, false);
            return null;
        }
    }

    public class TextDownload extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            txt_data = (TextView) findViewById(R.id.text_a);
            txt_data.setText(data);

        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String line = "";
            StringBuffer buff = new StringBuffer("");
            String newLine = System.getProperty("line.separator");
            try {
                BufferedReader bufRead = new BufferedReader(
                        new InputStreamReader(new URL(params[0]).openStream()));
                while ((line = bufRead.readLine()) != null) {
                    buff.append(line + newLine);

                }
                bufRead.close();
                data = buff.toString();

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

    }

}