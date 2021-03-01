package com.adiandnoy.RatEat.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;

public class StudentViewHolder extends RecyclerView.ViewHolder{
    public StudentsAdapter.OnItemClickListener listener;
    TextView dishName = itemView.findViewById(R.id.dishNameLisrRow);;
    ImageView studentImage = itemView.findViewById(R.id.listrow_image);
    int position;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        dishName = itemView.findViewById(R.id.dishNameLisrRow);
        studentImage = itemView.findViewById(R.id.listrow_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Dish dish, int position) {
        if (dish.getDishName() != null) {
            dishName.setText(dish.getDishName());
        }
        this.position = position;
    }
}
