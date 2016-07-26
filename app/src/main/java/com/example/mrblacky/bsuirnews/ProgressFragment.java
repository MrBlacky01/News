package com.example.mrblacky.bsuirnews;

/**
 * Created by Mr.Blacky on 25.07.2016.
 */
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.widget.Toast;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgressFragment extends Fragment {

    TextView contentView;
    String contentText = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        contentView = (TextView) view.findViewById(R.id.content);
        if(contentText!=null)
            contentView.setText(contentText);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(contentText==null)
            new ProgressTask().execute();
    }

    class ProgressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... path) {

            String content;
            try{

                content = getContent("http://bsuir.by");
            }
            catch (IOException ex){
                content = ex.getMessage();
            }

            return content;
        }
        @Override
        protected void onProgressUpdate(Void... items) {
        }
        @Override
        protected void onPostExecute(String content) {

            contentText=content;
            contentView.setText(content);
            Toast.makeText(getActivity(), "Данные загружены", Toast.LENGTH_SHORT).show();
        }

        private String getContent(String path) throws IOException {
            BufferedReader reader=null;
            try {
                URL url=new URL(path);
                HttpURLConnection urlConnection =(HttpURLConnection)url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                reader= new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"cp1251"));
                StringBuilder buf=new StringBuilder();
                String line=null;
                while ((line=reader.readLine()) != null) {
                    buf.append(line + "\n");
                }

                ArrayList<Element> elements = new ArrayList<>();
                elements = findElements(buf.toString());
                if (elements.size()== 0 )
                {
                    return ("Отсутствуют необходимые данные");
                }
                
                StringBuilder returned = new StringBuilder();
                for (int i = 0; i < elements.size(); i++){
                    returned.append(i+"\n");
                    returned.append(elements.get(i).getHref()+"\n");
                    returned.append(elements.get(i).getSrc()+"\n");
                    returned.append(elements.get(i).getAlt()+"\n");
                    returned.append(elements.get(i).getDate()+"\n");
                    returned.append(elements.get(i).getTheme()+"\n");
                }

                return(returned.toString());
            }
            finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }

        private String convertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
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
                String whatWeFind = matcherMain.group().toString();

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