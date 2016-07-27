package com.example.mrblacky.bsuirnews;

/**
 * Created by Mr.Blacky on 27.07.2016.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ElementViewHolder> {

    public static class ElementViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView newsDate;
        TextView newsName;
        TextView newsTag;
        ImageView newsPhoto;

        ElementViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);

            newsDate = (TextView)itemView.findViewById(R.id.news_date);
            newsName = (TextView)itemView.findViewById(R.id.news_name);
            newsTag = (TextView)itemView.findViewById(R.id.news_tag);
            newsPhoto = (ImageView)itemView.findViewById(R.id.news_photo);

        }
    }

    List<Element> news;

    RVAdapter(List<Element> news){
        this.news = news;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ElementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ElementViewHolder pvh = new ElementViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ElementViewHolder elementViewHolder, int i) {
        elementViewHolder.newsDate.setText(news.get(i).getDate());
        elementViewHolder.newsName.setText(news.get(i).getAlt());
        elementViewHolder.newsTag.setText(news.get(i).getTheme());
       // elementViewHolder.newsPhoto.setImageResource(R.mipmap.ic_launcher);

        elementViewHolder.newsPhoto.setImageBitmap(news.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
