package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.adiandnoy.RatEat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class UpdateDishFragment extends Fragment {

    TextInputEditText dishName;
    TextInputEditText dishDescription;
    TextInputEditText resturantName;
    TextInputEditText dishIngredients;
    ImageView dishImage;
    RatingBar dishStars;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_dish, container, false);

        dishName = view.findViewById(R.id.editDishName);
        dishDescription =  view.findViewById(R.id.editDishDescription);
        resturantName =  view.findViewById(R.id.editResturantName);
        dishIngredients =  view.findViewById(R.id.editDishIngredient);
        dishImage = view.findViewById(R.id.editImage);
        dishStars = view.findViewById(R.id.editDishRatingBar);
        dishName.requestFocus();

        // Gets the parameters
        String imgUrlParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishImgUrl();
        String dishNameParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishName();
        String dishDescriptionParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishDescription();
        String dishIngredientsParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getDishIngredients();
        String resturantNameParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getResturantName();
        Float ratingBarParam = UpdateDishFragmentArgs.fromBundle(getArguments()).getStars();

        Picasso.get().load(imgUrlParam).into(dishImage);
        dishName.setText(dishNameParam);
        dishDescription.setText(dishDescriptionParam);
        dishIngredients.setText(dishIngredientsParam);
        resturantName.setText(resturantNameParam);
        dishStars.setRating(ratingBarParam);
        return view;
    }
}