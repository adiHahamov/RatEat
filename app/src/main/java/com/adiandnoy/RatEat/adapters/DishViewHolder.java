package com.adiandnoy.RatEat.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.squareup.picasso.Picasso;

public class DishViewHolder extends RecyclerView.ViewHolder{
    public DishAdapter.OnItemClickListener listener;
    TextView dishName = itemView.findViewById(R.id.dishNameLisrRow);;
    ImageView dishImage = itemView.findViewById(R.id.listrow_image);
    int position;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        dishName = itemView.findViewById(R.id.dishNameLisrRow);
        dishImage = itemView.findViewById(R.id.listrow_image);

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
        if (dish.getImageUrl() != null){
            Picasso.get().load(dish.getImageUrl()).into(dishImage);
        }
        this.position = position;
    }
}
