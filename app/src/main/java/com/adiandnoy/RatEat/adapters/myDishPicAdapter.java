package com.adiandnoy.RatEat.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;

import java.util.List;

public class myDishPicAdapter extends RecyclerView.Adapter<myDishPicHolder> {
    LayoutInflater inflater;
    public List<Dish> data;

    public myDishPicAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private myDishPicAdapter.OnItemClickListener listener;

    public void setOnClickListener(myDishPicAdapter.OnItemClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public myDishPicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.my_dish_row, parent,false);
        myDishPicHolder holder = new myDishPicHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull myDishPicHolder holder, int position) {
        Dish dish = data.get(position);
        holder.bindData(dish, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
