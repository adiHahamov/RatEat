package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class AddDishFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_dish, container, false);
        Button addDish = view.findViewById(R.id.save_dish_buttom);

        TextInputEditText name =  view.findViewById(R.id.input_dish_name);
        TextInputEditText description =  view.findViewById(R.id.input_dish_description);
        TextInputEditText resturant =  view.findViewById(R.id.input_resturant_name);
        TextInputEditText ingredient =  view.findViewById(R.id.input_dish_ingredient);

        addDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dish dish = new Dish();
                dish.setId(UUID.randomUUID().toString());
                dish.setDishName(name.getText().toString());
                dish.setDishDescription(description.getText().toString());
                dish.setResturantName(resturant.getText().toString());
                dish.setIngredients(ingredient.getText().toString());
                Model.instance.addDish(dish,new Model.AddDisheListener() {
                    @Override
                    public void onComplete() {
                        int i;
                    }
                });
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}