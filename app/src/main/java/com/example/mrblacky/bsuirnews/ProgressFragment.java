package com.example.mrblacky.bsuirnews;

/**
 * Created by Mr.Blacky on 25.07.2016.
 */
import android.os.Bundle;
import android.app.Fragment;
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

    private List<Element> news;
    private RecyclerView rv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        rv=(RecyclerView)view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(inflater.getContext());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        //contentView = (TextView) view.findViewById(R.id.content);
        //if(contentText!=null)
         //   contentView.setText(contentText);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(this.news==null) {
                new ProgressTask().execute();

        }

    }

    class ProgressTask extends AsyncTask<String, Void, ArrayList<Element>> {

        @Override
        protected ArrayList<Element> doInBackground(String... path) {

            ArrayList<Element> content;
            try{

                content = getContent();
                return content;
            }
            catch (IOException ex){
               // Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                return new ArrayList<>();
            }


        }
        @Override
        protected void onProgressUpdate(Void... items) {
        }

        @Override
        protected void onPostExecute(ArrayList<Element> content)  {

            if (content.size()!=0)
            {
                RVAdapter adapter = new RVAdapter(content);
                rv.setAdapter(adapter);
                // contentText=content;
                // contentView.setText(content);
                Toast.makeText(getActivity(), "Данные загружены", Toast.LENGTH_SHORT).show();
                TextView textView = (TextView)getActivity().findViewById(R.id.content);
                textView.setText("");
            }
            else
            {
                Toast.makeText(getActivity(), "Ошибка соединения, включите интернет и попробуйте зайти снова", Toast.LENGTH_SHORT).show();
                TextView temp = (TextView) getActivity().findViewById(R.id.content);
                temp.setText("Ошибка соединения, включите интернет и попробуйте зайти снова");

            }
        }

        private ArrayList<Element> getContent() throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL(getResources().getString(R.string.bsuir_site));
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"cp1251"));
                StringBuilder buf=new StringBuilder();
                String line;
                while ((line=reader.readLine()) != null) {
                    buf.append(line).append("\n");
                }

                ArrayList<Element> elements;
                elements = findElements(buf.toString());
                if (elements.size()== 0 )
                {
                    return null;
                }
                else
                {
                    return elements;
                }


            }
            catch (Exception exept)
            {
                throw new IOException();
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }

        private  ArrayList findElements(String text){
            ArrayList <Element> result = new ArrayList<>();

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

                if (matcherHref.find() && matcherSrc.find() && matcherAlt.find() && matcherDate.find() && matcherTheme.find()){
                    result.add(new Element(matcherHref.group().substring(6), matcherSrc.group().substring(5), matcherAlt.group().substring(5),
                            matcherDate.group().substring(6), matcherTheme.group().substring(28)));

                }
            }
            return result;
        }

    }
}