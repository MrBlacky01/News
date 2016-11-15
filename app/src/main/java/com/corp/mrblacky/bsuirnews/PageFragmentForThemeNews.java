package com.corp.mrblacky.bsuirnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PageFragmentForThemeNews extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    private int pageNumber;

    static PageFragmentForThemeNews newInstance(int page) {
        PageFragmentForThemeNews pageFragment = new PageFragmentForThemeNews();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.corp.mrblacky.bsuirnews.R.layout.theme_news_page_fragment, null);

        TextView tvPage = (TextView) view.findViewById(com.corp.mrblacky.bsuirnews.R.id.page_loader);
        tvPage.setText("Page " + pageNumber);

        return view;
    }
}
