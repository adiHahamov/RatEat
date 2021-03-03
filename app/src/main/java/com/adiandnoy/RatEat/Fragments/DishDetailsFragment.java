package com.adiandnoy.RatEat.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.adiandnoy.RatEat.R;

public class DishDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dish_details, container, false);
//        ImageView dishImage = view.findViewById(R.id.dish_img);
//        Uri convImgUri = Uri.parse(imgUrl);
//        dishImage.setImageURI(convImgUri);
        return view;
    }
}