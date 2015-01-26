package sirfireydevs.rana.noidainternationaluniversity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private SharedPreferences preferences;


    public void fragment_layout(int layoutID) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        fragmentManager.beginTransaction().replace(R.id.container, Dynamic_fragment.newInstance(layoutID)).commit();
    }

    public void why_niu(View view) {
        fragment_layout(R.layout.lay_why_niu);
    }

    public void vc_message(View view) {
        fragment_layout(R.layout.lay_vc_message);
    }

    public void our_foundation(View view) {
        fragment_layout(R.layout.our_foundation);
    }

    public void grow_with_niu(View view) {
        fragment_layout(R.layout.lay_grow_up_with_niu);
    }

    //About Set
    public void faculty_directory(View view) {
        setTitle("Faculty Directory");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/Fac_dir.html");

    }

    public void set_department(View view) {
        setTitle("SET Department");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/set_dep.html");
    }

    public void set_presentation(View view) {
        setTitle("Download Presentation");
        fragment_layout(R.layout.lay_download_presentation);
    }

    public void downloadPresentation(View view) {
        Intent inten = new Intent(Intent.ACTION_VIEW, Uri.parse("http://set.niu.edu.in/wp-content/uploads/2014/06/SET-PPT-FINAL.ppt"));
        startActivity(inten);
    }

    //Webview Functionality
    public void webViewFunction(String url) {
        ConnectivityCheck check = new ConnectivityCheck();

        if (!(check.connectionStatus(getApplicationContext()) == ConnectivityCheck.TYPE_NOT_CONNECTED)) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, WebView_Dynamic.newInstance(url)).commit();
        } else {
            setTitle("Error");
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, ErrorShow.newInstance(1)).commit();
        }
    }

//    Academics & fee
//    http://www.sandeeprana011.hostfree.us/web_pages/hostel_fee.html

    public void hostel_fee(View view) {
        setTitle("Hostel & Fee");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/hostel_fee.html");
    }

    public void courses_fee(View view) {
        setTitle("Courses & Fee");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/course_fee.html");
    }

    public void research_publication(View view) {
        setTitle("Research & Publications");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/res_pub.html");
    }

    public void admission() {
        setTitle("Admission @ NiU");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/admissions.html");
    }

    public void contact() {
        setTitle("Contact NIU");
        webViewFunction("http://www.sandeeprana011.hostfree.us/web_pages/contact.html");
    }

    public void updates() {
        setTitle("Updates");
        webViewFunction("http://www.sandeeprana011.hostfree.us/news.html");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_root_parent);
        preferences = getSharedPreferences("pref", 0);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Home.newInstance(position + 1))
                    .commit();
        }
        if (position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, AboutNIU.newInstance(position + 1))
                    .commit();
        }
        if (position == 2) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Schools.newInstance(position + 1))
                    .commit();
        }
        if (position == 3) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, Academics_Fee.newInstance(position + 1))
                    .commit();
        }
        if (position == 4) {

            admission();
            mTitle="Admissions @ NIU";
            restoreActionBar();


        }
        if (position == 5) {
            contact();
            mTitle="Contact Us";
            restoreActionBar();


        }
        if (position == 6) {
            ConnectivityCheck check = new ConnectivityCheck();
            if (!(check.connectionStatus(getApplicationContext()) == ConnectivityCheck.TYPE_NOT_CONNECTED)) {
                Intent intent = new Intent(getApplicationContext(), ClassList.class);
                startActivity(intent);
            } else {
                fragmentManager.beginTransaction()
//                    .replace(R.id.container,new news_feeds())
                        .replace(R.id.container, ErrorShow.newInstance(position + 1))
                        .commit();
            }

        }
        
        if (position == 7) {
            fragmentManager.beginTransaction()
//                    .replace(R.id.container,new news_feeds())
                    .replace(R.id.container, Latest_Announcement.newInstance(position + 1))
                    .commit();
        }
        if (position == 8) {


            ConnectivityCheck check = new ConnectivityCheck();
            if (!(check.connectionStatus(getApplicationContext()) == ConnectivityCheck.TYPE_NOT_CONNECTED)) {
                Intent intent = new Intent(MainActivity.this, AdmitCard.class);
                Toast.makeText(getApplicationContext(), "Beta Mode", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ErrorShow.newInstance(position + 1))
                        .commit();
            }


}
        if (position == 9) {

            ConnectivityCheck check = new ConnectivityCheck();
            if (!(check.connectionStatus(getApplicationContext()) == ConnectivityCheck.TYPE_NOT_CONNECTED)) {
                Intent intent = new Intent(MainActivity.this, FacultyDashboard.class);
//                Toast.makeText(getApplicationContext(), "Activity Under Beta Mode\n(Testing Mode)", Toast.LENGTH_LONG).show();
                startActivity(intent);
            } else {
                fragmentManager.beginTransaction()
//                    .replace(R.id.container,new news_feeds())
                        .replace(R.id.container, ErrorShow.newInstance(position + 1))
                        .commit();
            }

        }
        if (position == 10) {
            ConnectivityCheck check = new ConnectivityCheck();
            if (!(check.connectionStatus(getApplicationContext()) == ConnectivityCheck.TYPE_NOT_CONNECTED)) {
                Intent intent = new Intent(MainActivity.this, Result.class);
                startActivity(intent);
            } else {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ErrorShow.newInstance(position + 1))
                        .commit();
            }

        }
        if (position == 11) {
            updates();
        }

        if (position == 12) {


            fragmentManager.beginTransaction()
                    .replace(R.id.container, AboutDevs.newInstance(position + 1))
                    .commit();

        }

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section5);
                break;
            case 5:
                mTitle = getString(R.string.title_section4);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_activity_admit_card);
                break;
            case 10:
                mTitle = getString(R.string.notes_heading);
                break;
            case 11:
                mTitle = getString(R.string.results);
                break;
            case 12:
                mTitle = getString(R.string.about_devs);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


class sleep extends Thread {
    public void Thread() throws InterruptedException {
        this.sleep(100);
    }
}
 class BackFalse extends AsyncTask<String,Integer,String>{

     @Override
     protected String doInBackground(String... params) {
         try {
             sleep.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         return null;
     }

     @Override
     protected void onPostExecute(String s) {
         super.onPostExecute(s);

         SharedPreferences.Editor editor = preferences.edit();
         editor.putBoolean("exit", false);
         editor.commit();

     }
 }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getBoolean("exit", false)) {

            editor.putBoolean("exit", false);
            editor.commit();
            super.onBackPressed();


        } else {
            new BackFalse().execute("");

            Toast.makeText(getApplicationContext(), "press Back again to exit", Toast.LENGTH_SHORT).show();
            editor.putBoolean("exit", true);
            editor.commit();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.niu_on_map) {
            Intent inteb = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=28.372125,77.540731(Noida International University)"));
            startActivity(inteb);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class Home extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Home() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Home newInstance(int sectionNumber) {
            Home fragment = new Home();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class AboutNIU extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public AboutNIU() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AboutNIU newInstance(int sectionNumber) {
            AboutNIU fragment = new AboutNIU();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.lay_about_niu, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    public static class Academics_Fee extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Academics_Fee() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Academics_Fee newInstance(int sectionNumber) {
            Academics_Fee fragment = new Academics_Fee();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.lay_academics_fee, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    public static class Latest_Announcement extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Latest_Announcement() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Latest_Announcement newInstance(int sectionNumber) {
            Latest_Announcement fragment = new Latest_Announcement();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.lat_news, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

    public static class AboutDevs extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public AboutDevs() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AboutDevs newInstance(int sectionNumber) {
            AboutDevs fragment = new AboutDevs();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_about_us, container, false);
            return rootView;
        }

        public void why_niu(View view) {

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class Dynamic_fragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int LAYOUT_ID;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Dynamic_fragment newInstance(int layout_id) {
            LAYOUT_ID = layout_id;
            Dynamic_fragment fragment = new Dynamic_fragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(LAYOUT_ID, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
//            ((MainActivity) activity).onSectionAttached(
//                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public static class WebView_Dynamic extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static int LAYOUT_ID;
        private static String url;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static WebView_Dynamic newInstance(String urle) {
            url = urle;
            WebView_Dynamic fragment = new WebView_Dynamic();
            return fragment;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            WebView webView = (WebView) view.findViewById(R.id.web_view);
            webView.loadUrl(url);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.webview_ayout, container, false);
            WebView webView = (WebView) rootView.findViewById(R.id.web_view);
//
            webView.loadUrl(url);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
    }

    public static class ErrorShow extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public ErrorShow() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ErrorShow newInstance(int sectionNumber) {
            ErrorShow fragment = new ErrorShow();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.lay_errorshow, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }
///
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

    public static class Teachers_profile extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";


        public Teachers_profile() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Teachers_profile newInstance(int sectionNumber) {
            Teachers_profile fragment = new Teachers_profile();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.teacher_profile, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

    }

    public static class Schools extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public Schools() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Schools newInstance(int sectionNumber) {
            Schools fragment = new Schools();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//            View rootView = inflater.inflate(R.layout.lay_about_set, container, false);
            View rootView = inflater.inflate(R.layout.schools, container, false);
//        e);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


    }
    public void set_engineer(View view){
        mTitle="School of Eng. & Tech.";
        restoreActionBar();
//        getActionBar().setTitle(mTitle);
//        setTitle("");
        fragment_layout(R.layout.lay_about_set);
    }
    public void set_science(View view){
        mTitle="School of Science";


        restoreActionBar();
//        setTitle("School of Science");
        fragment_layout(R.layout.school_science);
    }
    public void set_law(View view){

        mTitle="School of LAW";
        restoreActionBar();
        fragment_layout(R.layout.school_law);
    }
    public void school_jmc(View view){

        mTitle="School of Journalism & Mass Communication";
        restoreActionBar();
        fragment_layout(R.layout.school_jmc);
    }
    public void school_nursing(View view){

        mTitle="School of Nursing";
        restoreActionBar();
        fragment_layout(R.layout.school_nursing);
    }
    public void school_fine_arts(View view){

        mTitle="School of Fine Arts";
        restoreActionBar();
        fragment_layout(R.layout.school_fine_arts);
    }
    public void school_liberal_arts(View view){

        mTitle="School of Liberal Arts";
        restoreActionBar();
        fragment_layout(R.layout.school_lib_arts);
    }
    public void school_architecture(View view){

        mTitle="School of Architecture";
        restoreActionBar();
        fragment_layout(R.layout.school_architecture);
    }
    public void school_edu(View view){

        mTitle="School of Education";
        restoreActionBar();
        fragment_layout(R.layout.school_edu);
    }
    public void school_defence(View view){

        mTitle="School of Defence ,Security & Intelligence";
        restoreActionBar();
        fragment_layout(R.layout.school_defense);
    }




}

