package sirfireydevs.rana.noidainternationaluniversity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class news_feeds extends Fragment {

    WebView webV;
    ProgressBar pb;
    TextView textView;
    String data;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.lay_news_feeds, container, false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View vie = getView();
        webV = (WebView) vie.findViewById(R.id.web_view1);
        pb = (ProgressBar) vie.findViewById(R.id.progress_newsfeeds);
        textView = (TextView) vie.findViewById(R.id.textview_newsfeeds_refreshingdata);

        ConnectivityCheck ch = new ConnectivityCheck();

        if (ch.connectionStatus(getActivity().getApplicationContext()) == 0) {
            pb.setVisibility(View.GONE);
            textView.setTextColor(Color.RED);
            textView.setText("Data Connection Not Available. News  Feeds can not be Downloaded.\nTurn-on data Connection and re-try");
        } else {
            DownHtml htm = new DownHtml();
            htm.execute("");
        }


    }

    public class DownHtml extends AsyncTask<String, Integer, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... strings) {
//        if ()
            NIU_Feeds feed = new NIU_Feeds();
            data = feed.htmlData();
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            webV.loadData(data, "text/html", "UTF-8");
            webV.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);


        }
    }
}
