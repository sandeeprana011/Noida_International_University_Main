package sirfireydevs.rana.noidainternationaluniversity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FacultyDashboard extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea_pro_activity);
        DownloadTeachersProfile downloadTeachersProfile = new DownloadTeachersProfile();
        downloadTeachersProfile.execute("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tea_pro_activity, menu);
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

    public class DownloadTeachersProfile extends AsyncTask<String, Integer, String> {
        ArrayList<Teacher_profile> arrateacher_profiles;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(getApplicationContext(),"PreExecuted",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            Profile_Load profile_load = new Profile_Load();
            arrateacher_profiles = profile_load.profile();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayAdapter<Teacher_profile> teacher_profileArrayAdapter = new MyTPAdapter();

            ListView listView = (ListView) findViewById(R.id.listview_teacher_profile);
            listView.setAdapter(teacher_profileArrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String link_file = arrateacher_profiles.get(position).getLink_file();

                    Intent intent = new Intent(getApplicationContext(), Notes.class);
                    intent.putExtra("link", link_file);
                    startActivity(intent);

//                    Toast.makeText(getApplicationContext(),link_file,Toast.LENGTH_SHORT).show();

                }
            });
        }


        public class MyTPAdapter extends ArrayAdapter<Teacher_profile> {
            public MyTPAdapter() {
                super(FacultyDashboard.this, R.layout.list_item_views, arrateacher_profiles);
            }
            URL url;
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                View view = convertView;
                if (view == null) {
                    view = getLayoutInflater().inflate(R.layout.lay_teach_pro,
                            parent, false);
                }

                Teacher_profile curData = arrateacher_profiles.get(position);

//                new DownloadImageTask((ImageView) findViewById(R.id.admit_image_view)).execute(curData.getLink_file().replace("listfile.php","200")+".jpg");

                TextView ProID = (TextView) view.findViewById(R.id.textView_id);
                ProID.setText(curData.getId());
                TextView pro_name = (TextView) view.findViewById(R.id.textView_name);
                pro_name.setText(curData.getName());
                TextView proSub = (TextView) view.findViewById(R.id.textView_subjects);
                proSub.setText(curData.getSubjects());
                TextView proExtraFir = (TextView) view.findViewById(R.id.textView_extra_fir);
                proExtraFir.setText(curData.getExtra_fir());
                TextView proExtraSec = (TextView) view.findViewById(R.id.textView_extra_sec);
                proExtraSec.setText(curData.getExtra_sec());

                return view;
                // return super.getView(position, convertView, parent);
            }


        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11=BitmapFactory.decodeResource(getResources(),R.drawable.image_not_found);
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }


                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }



    }





}
