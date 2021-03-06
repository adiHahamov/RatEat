package com.adiandnoy.RatEat.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
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
    RatingBar ratingBar;
    int position;

    public DishViewHolder(@NonNull View itemView) {
        super(itemView);
        dishName = itemView.findViewById(R.id.dishNameLisrRow);
        dishImage = itemView.findViewById(R.id.listrow_image);
        ratingBar = itemView.findViewById(R.id.viewDishRatingBar);

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
        dishImage.setImageResource(R.drawable.dish);
        if (dish.getImageUrl() != null){
            Picasso.get().load(dish.getImageUrl()).placeholder(R.drawable.dish).into(dishImage);
//            dishImage.setRotation(90);
        }

        if (dish.getStars() != null) {
            double d  =  dish.getStars();
            float f = (float)d;
            ratingBar.setRating(f);
        }
        this.position = position;
    }
}
