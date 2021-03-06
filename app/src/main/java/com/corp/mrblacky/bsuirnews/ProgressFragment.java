package com.corp.mrblacky.bsuirnews;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.widget.Toast;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgressFragment extends Fragment {

    private List<ShortNewsFromMainPage> news;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.corp.mrblacky.bsuirnews.R.layout.fragment_progress, container, false);

        rv=(RecyclerView)view.findViewById(com.corp.mrblacky.bsuirnews.R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(inflater.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(this.news==null) {
                new ProgressTask().execute();
        }
    }

    private class ProgressTask extends AsyncTask<String, Void, ArrayList<ShortNewsFromMainPage>> {

        @Override
        protected ArrayList<ShortNewsFromMainPage> doInBackground(String... path) {

            ArrayList<ShortNewsFromMainPage> content;
            try {

                content = getContent();
                return content;
            }
            catch (IOException ex) {
                return new ArrayList<>();
            }
        }

        @Override
        protected void onProgressUpdate(Void... items) {
        }

        @Override
        protected void onPostExecute(ArrayList<ShortNewsFromMainPage> content)  {

            Fragment tempf = (Fragment) getFragmentManager().findFragmentById(com.corp.mrblacky.bsuirnews.R.id.contentFragment);
            if (tempf instanceof ProgressFragment) {
                if (content != null && content.size() != 0) {

                    RVAdapter adapter = new RVAdapter(getContext(), content);
                    rv.setAdapter(adapter);
                    // contentText=content;
                    // contentView.setText(content);
                    Toast.makeText(getActivity(), "Данные загружены", Toast.LENGTH_SHORT).show();
                    TextView textView = (TextView) getActivity().findViewById(com.corp.mrblacky.bsuirnews.R.id.content);
                    textView.setText("");
                } else {

                    Toast.makeText(getActivity(), "Ошибка соединения, включите интернет и попробуйте зайти снова", Toast.LENGTH_SHORT).show();
                    TextView temp = (TextView) getActivity().findViewById(com.corp.mrblacky.bsuirnews.R.id.content);
                    temp.setText("Ошибка соединения, включите интернет и попробуйте зайти снова");

                }
            }
        }

        private ArrayList<ShortNewsFromMainPage> getContent() throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL(getResources().getString(com.corp.mrblacky.bsuirnews.R.string.bsuir_site));
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"cp1251"));
                StringBuilder buf=new StringBuilder();
                String line;
                while ((line=reader.readLine()) != null) {
                    buf.append(line).append("\n");
                }

                ArrayList<ShortNewsFromMainPage> elements;
                elements = findElements(buf.toString());
                buf.delete(0,buf.length());
                if (elements.size()== 0 ) {
                    return null;
                }
                else {
                    return elements;
                }
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

        private  ArrayList findElements(String text){
            ArrayList <ShortNewsFromMainPage> result = new ArrayList<>();

            Pattern patternHref = Pattern.compile("(href=\")[^\"]*");
            Pattern patternSrc = Pattern.compile("(src=\")[^\"]*");
            Pattern patternAlt = Pattern.compile("(alt=\")[^\"]*");
            Pattern patternDate = Pattern.compile("<span>[^<]*");
            Pattern patternTheme = Pattern.compile("<div class=\"rub_[^<]*");

            Pattern patternMain = Pattern.compile("((<div class=\"item\".*>[.\\s]*<div class=\"img\".*>[\\S\\s]*?<\\/span>))");
            Matcher matcherMain = patternMain.matcher(text);
            while (matcherMain.find()){
                String whatWeFind = matcherMain.group();

                Matcher matcherHref = patternHref.matcher(whatWeFind);
                Matcher matcherSrc = patternSrc.matcher(whatWeFind);
                Matcher matcherAlt = patternAlt.matcher(whatWeFind);
                Matcher matcherDate = patternDate.matcher(whatWeFind);
                Matcher matcherTheme = patternTheme.matcher(whatWeFind);

                if (matcherHref.find() && matcherSrc.find() && matcherAlt.find() && matcherDate.find() && matcherTheme.find()) {
                    result.add(new ShortNewsFromMainPage(matcherHref.group().substring(6),
                            matcherSrc.group().substring(5),
                            CheckAlt(matcherAlt.group().substring(5)),
                            matcherDate.group().substring(6),
                            matcherTheme.group().substring(28))
                            );
                }
            }
            return result;
        }

        private String CheckAlt(String alt){
            while ((alt.contains("&laquo;"))||(alt.contains("&raquo;"))){
                if(alt.contains("&laquo;")){
                    alt = alt.replace("&laquo;","\u00AB");
                }
                else{
                    alt = alt.replace("&raquo;","\u00BB");
                }
            }
            return alt;
        }

    }
}