package com.example.mrblacky.bsuirnews;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ElementViewHolder> {

    public static class ElementViewHolder extends RecyclerView.ViewHolder {

        final CardView cv;
        final TextView newsDate;
        final TextView newsName;
        final TextView newsTag;
        final ImageView newsPhoto;
        String href;

        ElementViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            cv.setPreventCornerOverlap(false);

            newsDate = (TextView)itemView.findViewById(R.id.news_date);
            newsName = (TextView)itemView.findViewById(R.id.news_name);
            newsTag = (TextView)itemView.findViewById(R.id.news_tag);
            newsPhoto = (ImageView)itemView.findViewById(R.id.news_photo);

            cv.setOnClickListener(new View.OnClickListener() {

                @Override public void onClick(View v) {
                    //...
                    Toast.makeText(v.getContext(), href, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), WebResourse.class);
                    intent.putExtra("HrefValue","http://bsuir.by"+href);
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    private final List<ShortNewsFromMainPage> news;
    AdapterView.OnItemClickListener mItemClickListener;

    RVAdapter(List<ShortNewsFromMainPage> news){
        this.news = news;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ElementViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);

        return new ElementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ElementViewHolder elementViewHolder, int i) {
        elementViewHolder.newsDate.setText(news.get(i).getDate());
        elementViewHolder.newsName.setText(news.get(i).getAlt());
        elementViewHolder.newsTag.setText(news.get(i).getTheme());
       // elementViewHolder.newsPhoto.setImageResource(R.mipmap.ic_launcher);

        try {
            Picasso.with(elementViewHolder.newsPhoto.getContext()).load("http://bsuir.by"+news.get(i).getSrc())
                    .placeholder(R.drawable.vasilec).into(elementViewHolder.newsPhoto);
        }
        catch (Exception exept)
        {

        }
        elementViewHolder.href=news.get(i).getHref();
        //elementViewHolder.newsPhoto.setImageBitmap(news.get(i).getImage());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
