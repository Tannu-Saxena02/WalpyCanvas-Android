package com.example.walpy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;
import com.example.walpy.Models.CategoryRVModel;
import com.example.walpy.Models.Wallpaper;
import com.example.walpy.Response.GetWallpaperResponse.PhotosItem;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class WallpaperRVAdapter  extends RecyclerView.Adapter<WallpaperRVAdapter.ViewHolder> {
    private List<PhotosItem> wallpaperRVModelsArrayList;
    private Context context;

    public WallpaperRVAdapter(List<PhotosItem> wallpaperRVModelsArrayList, Context context) {
        this.wallpaperRVModelsArrayList = wallpaperRVModelsArrayList;
        this.context = context;
    }

    //    private CategoryRVAdapter.CategoryClickInterface categoryClickInterface;
    @NonNull
    @Override
    public WallpaperRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.wallpaper_rv_item,parent,false);
        return new WallpaperRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperRVAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(wallpaperRVModelsArrayList.get(position).getSrc().getPortrait()).into(holder.wallpaperIv);
      holder.itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent=new Intent(context,WallpaperActivity.class);
              intent.putExtra("imgurl",wallpaperRVModelsArrayList.get(position).getSrc().getPortrait());
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return wallpaperRVModelsArrayList.size();
    }
   public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView wallpaperIv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIv=itemView.findViewById(R.id.wallpaperIv);
        }
    }
}
