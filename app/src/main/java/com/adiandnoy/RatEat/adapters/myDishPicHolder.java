package com.adiandnoy.RatEat.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.squareup.picasso.Picasso;

public class myDishPicHolder extends RecyclerView.ViewHolder {
    public myDishPicAdapter.OnItemClickListener listener;
    ImageView dishImage = itemView.findViewById(R.id.image_my_list_row);
    ImageButton edit =  itemView.findViewById(R.id.editBtn);
    ImageButton delete =  itemView.findViewById(R.id.delBtn);

    int position;

    public myDishPicHolder(@NonNull View itemView) {
        super(itemView);
        dishImage = itemView.findViewById(R.id.image_my_list_row);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Dish dish, int position) {
        if (dish.getImageUrl() != null){
            Picasso.get().load(dish.getImageUrl()).placeholder(R.drawable.dish).into(dishImage);
        }


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dish.setDeleted(true);
                Model.instance.addDish(dish, new Model.AddDisheListener() {
                    @Override
                    public void onComplete() {

                        Model.instance.refreshAllDishes(new Model.Listener() {
                            @Override
                            public void onComplete(Object data) {

                            }
                        });
                    }
                });
            }
        });

        this.position = position;
    }
}
