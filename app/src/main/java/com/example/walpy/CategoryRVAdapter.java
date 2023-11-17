package com.example.walpy;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.walpy.Models.CategoryRVModel;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.ViewHolder> {
    private ArrayList<CategoryRVModel>categoryRVModelsArrayList;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryRVAdapter(ArrayList<CategoryRVModel> categoryRVModelsArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModelsArrayList = categoryRVModelsArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.category_rv_item,parent,false);
        return new CategoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.ViewHolder holder, int position) {
    CategoryRVModel categoryRVModel=categoryRVModelsArrayList.get(position);
    holder.categoryTv.setText(categoryRVModel.getCategory());
    Glide.with(context).load(categoryRVModel.getCategoryIVUrl()).into(holder.categoryIv);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
    categoryClickInterface.onCategoryClick(position);
        }
    });
    }

    @Override
    public int getItemCount() {
        return categoryRVModelsArrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{
        TextView categoryTv;
        ImageView categoryIv;
        public ViewHolder(@NonNull View view) {
            super(view);
            categoryIv=view.findViewById(R.id.categoryIv);
            categoryTv=view.findViewById(R.id.categoryTv);

        }
    }
    public interface  CategoryClickInterface{
        void onCategoryClick(int position);
    }
}
