package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.adiandnoy.RatEat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class DishDetailsFragment extends Fragment {

    TextInputEditText name;
    TextInputEditText description;
    TextInputEditText resturant;
    TextInputEditText ingredient;
    ImageView dishImage;
    RatingBar dishStars;
    ImageButton backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);

        name =  view.findViewById(R.id.dish_name_details_fragment);
        description =  view.findViewById(R.id.dish_description_details_fragment);
        resturant =  view.findViewById(R.id.resturant_name_details_fragment);
        ingredient =  view.findViewById(R.id.dish_ingredient_details_fragment);
        dishImage = view.findViewById(R.id.dish_img_details_fragment);
        dishStars = view.findViewById(R.id.ratingBar_details_fragment);
        backBtn = view.findViewById(R.id.backBtnFromDishDtls);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        // Gets the parameters
        String imgUrlParam = DishDetailsFragmentArgs.fromBundle(getArguments()).getDishImgUrl();
        String dishNameParam = DishDetailsFragmentArgs.fromBundle(getArguments()).getDishName();
        String dishDescriptionParam = DishDetailsFragmentArgs.fromBundle(getArguments()).getDishDescription();
        String dishIngredientParam = DishDetailsFragmentArgs.fromBundle(getArguments()).getDishIngredients();
        String restutantNameParam =  DishDetailsFragmentArgs.fromBundle(getArguments()).getResturantName();
        Float starsParam =  DishDetailsFragmentArgs.fromBundle(getArguments()).getStars();

        Picasso.get().load(imgUrlParam).into(dishImage);
        dishImage.setRotation(90);
        name.setText(dishNameParam);
        description.setText(dishDescriptionParam);
        resturant.setText(restutantNameParam);
        ingredient.setText(dishIngredientParam);
        resturant.setText(restutantNameParam);
        dishStars.setRating(starsParam);

        return view;
    }
}