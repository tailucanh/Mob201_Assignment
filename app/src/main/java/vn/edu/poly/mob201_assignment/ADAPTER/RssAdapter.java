package vn.edu.poly.mob201_assignment.ADAPTER;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import vn.edu.poly.mob201_assignment.DTO.ObjItemsRss;
import vn.edu.poly.mob201_assignment.R;

public class RssAdapter extends RecyclerView.Adapter<RssAdapter.MyItemViewHolder> {

    ArrayList<ObjItemsRss> listsItem;
    Context context;

    public RssAdapter(ArrayList<ObjItemsRss> listsItem,Context context) {
        this.listsItem = listsItem;
        this.context = context;
    }

    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_rss,parent,false);
        return new RssAdapter.MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {
        ObjItemsRss items = listsItem.get(position);
        String link = items.getLink();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        holder.tvTitle.setOnClickListener(tv ->{
            intent.setData(Uri.parse(link));
            context.startActivity(intent);
        });
        holder.tvContent.setOnClickListener(tv ->{
            intent.setData(Uri.parse(link));
            context.startActivity(intent);
        });


        holder.tvTitle.setText(items.getTitle());
        holder.tvContent.setText(Html.fromHtml(items.getDescription()));
        holder.tvDate.setText(items.getPubDate());
    }

    @Override
    public int getItemCount() {
        return listsItem.size();
    }


    public class MyItemViewHolder extends  RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvDate;
        public MyItemViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvContent = view.findViewById(R.id.tvContent);
            tvDate = view.findViewById(R.id.tvDateTime);

        }
    }

}
