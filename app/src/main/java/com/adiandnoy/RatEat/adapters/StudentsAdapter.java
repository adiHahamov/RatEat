package com.adiandnoy.RatEat.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Student;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentViewHolder>{
    public List<Student> data;
    LayoutInflater inflater;

    public StudentsAdapter(LayoutInflater inflater){
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
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row,parent,false);
        StudentViewHolder holder = new StudentViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = data.get(position);
        holder.bindData(student,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}