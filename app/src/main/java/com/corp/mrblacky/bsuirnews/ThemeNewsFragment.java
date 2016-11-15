package com.corp.mrblacky.bsuirnews;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThemeNewsFragment extends Fragment {

    private String from;
    private String NewsRubricString = "/ru/news/rubric/";
    private String LooperString = "?start_from=";
    private int NewRubricCount = 0;

    private ViewPager mViewPager;
    private PagerAdapter pagerAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle extras = this.getArguments();
        if (extras != null) {
            from = extras.getString("From");
        }

        if(NewRubricCount == 0) {
            new GetCountOfNewsInRubricTask().execute(getContext().getResources().getString(com.corp.mrblacky.bsuirnews.R.string.bsuir_site)+ NewsRubricString+from+LooperString+"0");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.corp.mrblacky.bsuirnews.R.layout.theme_news_fragment, container, false);


        return view;
    }

    private class GetCountOfNewsInRubricTask extends AsyncTask<String,Void,Integer>
    {
        @Override
        protected Integer doInBackground (String... paths) {

            Integer result = 0;
            try {
                for (String path : paths) {
                    result = getContent(path);
                }
                return result;
            }
            catch (IOException ex) {

                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer count)  {

            Fragment tempf = (Fragment) getFragmentManager().findFragmentById(com.corp.mrblacky.bsuirnews.R.id.contentFragment);
            if (tempf instanceof ThemeNewsFragment) {
                NewRubricCount = count;

                TextView temp = (TextView) getActivity().findViewById(com.corp.mrblacky.bsuirnews.R.id.tx);
                temp.setText(count.toString());

                mViewPager = (ViewPager) getActivity().findViewById(com.corp.mrblacky.bsuirnews.R.id.pager);
                pagerAdapter = new ThemeNewsFragmentPagerAdapter(getChildFragmentManager());
                mViewPager.setAdapter(pagerAdapter);
                mViewPager.setPageMargin(100);
            }

        }

        private Integer getContent(String path) throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL(path);
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"cp1251"));
                StringBuilder buf=new StringBuilder();
                String line;
                while ((line=reader.readLine()) != null) {
                    buf.append(line).append("\n");
                }

                return findCount(buf.toString());
            }
            catch (Exception exept) {
                throw new IOException();
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }

        private int findCount(String text){

            int result = 0;
            Pattern patternCount = Pattern.compile("<td align=\"center\" .* NOWRAP .*</td>");
            Pattern patternNumbers = Pattern.compile("\\d+");

            Matcher matcherMain = patternCount.matcher(text);
            while (matcherMain.find()){

                String whatWeFind = matcherMain.group();
                Matcher matcherNumbers = patternNumbers.matcher(whatWeFind);
                while (matcherNumbers.find()){
                    result = Integer.parseInt(matcherNumbers.group());
                }
            }

            return  (int)Math.ceil(result/20);
        }


    }

    private class ThemeNewsFragmentPagerAdapter extends FragmentPagerAdapter {

        public ThemeNewsFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragmentForThemeNews.newInstance(position);
        }

        @Override
        public int getCount() {
            return NewRubricCount;
        }

    }

}
