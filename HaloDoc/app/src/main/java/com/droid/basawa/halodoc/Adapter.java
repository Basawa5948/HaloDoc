package com.droid.basawa.halodoc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public String google = "https://www.google.com";

    private ArrayList<HashMap<String,String>> arrayList;
    private Context context;

    public Adapter(ArrayList<HashMap<String, String>> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,
                parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap = arrayList.get(position);

        String show_date = hashMap.get("DATE");
        String show_title = hashMap.get("TITLE");
        final String show_url = hashMap.get("URL");
        String show_author = hashMap.get("AUTHOR");

        holder.created.setText("Created_On = "+show_date);
        holder.heading.setText("Title = "+show_title);
        /*if(show_url.equals("") || show_url.equals("null")){
            holder.link.setText("URL = www.google.com");
        }else{
            holder.link.setText("URL = "+show_url);
        }*/
        holder.author.setText("Author = "+show_author);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DisplayNews.class);
                Bundle bundle = new Bundle();
                if(show_url.equals("") || show_url.equals("null")){
                    bundle.putString("Url",google);
                }else{
                    bundle.putString("Url",show_url);
                }
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView created;
        TextView heading;
        //TextView link;
        TextView author;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            created = (TextView) itemView.findViewById(R.id.showdate);
            heading = (TextView) itemView.findViewById(R.id.showtitle);
            //link = (TextView) itemView.findViewById(R.id.showurl);
            author = (TextView) itemView.findViewById(R.id.showauthor);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
