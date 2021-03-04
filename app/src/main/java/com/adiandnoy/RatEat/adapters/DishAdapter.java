package com.adiandnoy.RatEat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishViewHolder>{
    public MutableLiveData<List<Dish>> data;
    LayoutInflater inflater;

    public DishAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row,parent,false);
        DishViewHolder holder = new DishViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = data.getValue().get(position);
        holder.bindData(dish,position);
    }

    @Override
    public int getItemCount() {
        return data.getValue().size();
    }
}