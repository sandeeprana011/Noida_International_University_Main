package sirfireydevs.rana.noidainternationaluniversity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class AdmitCard extends ActionBarActivity {
    int table_nu;
    Result_Data res_output;
    String table_name = null;
    private String path_admitCard;
    private boolean saved=false;
    MenuItem menuItem;
    private Menu currentMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit_card);
        Spinner spinner = (Spinner) findViewById(R.id.admit_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.table_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                table_nu=position;
                switch (position) {
                    case 0:
                        table_name = "NIU_ARCH";
                        break;
                    case 1:
                        table_name = "NIU_EDU";
                        break;
                    case 2:
                        table_name = "NIU_SET";
                        break;
                    case 3:
                        table_name = "NIU_SCI";
                        break;
                    case 4:
                        table_name = "NIU_LAW";
                        break;
                    case 5:
                        table_name = "NIU_JMC";
                        break;
                    case 6:
                        table_name = "NIU_NUR";
                        break;
                    case 7:
                        table_name = "NIU_FA";
                        break;
                    case 8:
                        table_name = "NIU_LA";
                        break;
                    case 9:
                        table_name = "NIU_DSI";
                        break;

                }


//                Toast.makeText(getApplicationContext(), table_name, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admit_card, menu);
        currentMenu=menu;
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
            saveAdmitCard();
            if(saved){
                Toast.makeText(getApplicationContext(),"Admit Card saved to "+path_admitCard,Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(),"Error: Admit Card can't be saved.",Toast.LENGTH_LONG).show();
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getResult(View view) {
        EditText enroll_no = (EditText) findViewById(R.id.admit_enroll_no);
        EditText prf_no = (EditText) findViewById(R.id.admit_prf_no);
        EditText sem = (EditText) findViewById(R.id.admit_sem);

        EditText pass = (EditText) findViewById(R.id.admit_password);

        String enroll = String.valueOf(enroll_no.getText());


        if (enroll_no.getText() != null && prf_no.getText() != null && sem.getText() != null) {

            Res_Execute res_execute = new Res_Execute();
            res_execute.execute(new String[]{enroll, String.valueOf(sem.getText()), String.valueOf(prf_no.getText()), table_name, String.valueOf(pass.getText())});

        } else {

            Toast.makeText(getApplicationContext(), "Fill all Required Fields", Toast.LENGTH_LONG).show();

        }

    }


    public class Res_Execute extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            Result_Download result_download = new Result_Download();
            res_output = result_download.result(params[0], params[1], params[2], params[3], params[4]);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);


            if (res_output != null && res_output.getPulish() != "N") {
                setContentView(R.layout.admit_card);
                menuItem=currentMenu.findItem(R.id.action_admitcard);
//                menuItem= (MenuItem) findViewById(R.id.action_admitcard);
                menuItem.setVisible(true);

                new DownloadImageTask((ImageView) findViewById(R.id.admit_image_view))
                        .execute("http://sandeeprana011.hostfree.us/barcode/barcode.php?text=" + res_output.getPrf() + "_" + res_output.getSem()+"_"+table_nu);
                new DownloadImageTask((ImageView) findViewById(R.id.admit_dp))
                        .execute("http://sandeeprana011.hostfree.us/student_dp/"+res_output.getPrf()+".jpg");
//                new DownloadImageTask(ImageView)

                TextView name = (TextView) findViewById(R.id.admit_op_name);
                TextView father_name = (TextView) findViewById(R.id.admit_op_f_name);
                TextView dob = (TextView) findViewById(R.id.admit_op_dob);
                TextView sem = (TextView) findViewById(R.id.admit_op_sem);
                TextView course = (TextView) findViewById(R.id.admit_op_course);
                TextView enroll_num = (TextView) findViewById(R.id.admit_op_enroll_no);
                TextView prf = (TextView) findViewById(R.id.admit_op_prf);


                String stri[];
                stri = res_output.getMarks().split(",");
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, stri);

                name.setText(res_output.getName());
                father_name.setText(res_output.getFather_name());
                dob.setText(res_output.getDob());
                course.setText(res_output.getCourse());
                sem.setText(res_output.getSem());
                enroll_num.setText(res_output.getEnroll_no());
                prf.setText(res_output.getPrf());


            } else {
                Toast.makeText(getApplicationContext(), "Either filled details are wrong or Your AdmitCard is Not Available.", Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_admit_card);
            }

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

    public boolean saveAdmitCard() {
        View content = findViewById(R.id.lin_admit_card);

        content.setDrawingCacheEnabled(true);

        String root = Environment.getExternalStorageDirectory().toString();

        Bitmap bitmap = content.getDrawingCache();

        Random gen = new Random();
        int n = 10000;
        n = gen.nextInt(n);
        String num=String.valueOf(n);

        File dir=new File(root+File.separator+"AdmitCard");

        if(!dir.exists()) {
            boolean suc = dir.mkdirs();
            if (suc == false) {
                boolean dirMk = dir.mkdir();

            }
        }
        path_admitCard=root + File.separator+"AdmitCard"+File.separator+"AdmitCard-PRF-" + res_output.getPrf()+"-Sem-"+res_output.getSem() +"-"+num+ ".png";
        File file = new File(root + File.separator+"AdmitCard"+File.separator+"AdmitCard-PRF-" + res_output.getPrf()+"-Sem-"+res_output.getSem() +"-"+num+ ".png");

        {
            if (!file.exists()) {
                try {

                    file.createNewFile();

                    FileOutputStream ostream = null;

                    ostream = new FileOutputStream(file);

                    saved=bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);

                    ostream.close();

                    content.invalidate();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                content.setDrawingCacheEnabled(false);
            }
        }
        return saved;
    }
}

