package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adiandnoy.RatEat.R;
import com.google.android.material.textfield.TextInputEditText;

public class UpdateDishFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_dish, container, false);
        TextInputEditText dishName = view.findViewById(R.id.editDishName);
        dishName.requestFocus();
        return view;
    }
}