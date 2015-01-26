package sirfireydevs.rana.noidainternationaluniversity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends ActionBarActivity {
    Result_Data res_output;
    String table_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Spinner spinner = (Spinner) findViewById(R.id.res_spin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.table_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
        getMenuInflater().inflate(R.menu.menu_result, menu);

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

    public void getResult(View view) {
        EditText enroll_no = (EditText) findViewById(R.id.result_enroll_no);
        EditText prf_no = (EditText) findViewById(R.id.result_prf_no);
        EditText sem = (EditText) findViewById(R.id.result_sem);

        EditText pass = (EditText) findViewById(R.id.result_password);

        String enroll = String.valueOf(enroll_no.getText());


//        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }
//        else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else        if(enroll.contains("SET")){
//            table_name="NIU_SET";
//        }else

        //get data first from Text Fields
//        String enroll_no, String sem, String prf, String table_name


        if ((enroll_no.getText().toString() != "" || prf_no.getText().toString() != "") && sem.getText().toString() != "") {

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
            setContentView(R.layout.result_req);
            if (res_output != null && res_output.getPulish() != "N") {

                TextView name = (TextView) findViewById(R.id.res_op_name);
                TextView father_name = (TextView) findViewById(R.id.res_op_f_name);
                TextView dob = (TextView) findViewById(R.id.res_op_dob);
                TextView sem = (TextView) findViewById(R.id.res_op_sem);
                TextView course = (TextView) findViewById(R.id.res_op_course);
                TextView enroll_num = (TextView) findViewById(R.id.res_op_enroll_no);
                TextView prf = (TextView) findViewById(R.id.res_op_prf);

                ListView listView = (ListView) findViewById(R.id.res_listview_op_subjects);

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
                listView.setAdapter(arrayAdapter);

            } else {
                Toast.makeText(getApplicationContext(), "Either filled details are wrong or Your Result is Not Available.", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_result);
            }

        }
    }


}
