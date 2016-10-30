package com.example.mrblacky.bsuirnews;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ThemeNewsFragment extends Fragment {

    String from;

    public ThemeNewsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.theme_news, container, false);
        Bundle extras = this.getArguments();
        if (extras != null)
        {
            String text = extras.getString("From");
            TextView temp = (TextView) view.findViewById(R.id.tx);
            temp.setText(text);
        }
        return view;
    }

}
