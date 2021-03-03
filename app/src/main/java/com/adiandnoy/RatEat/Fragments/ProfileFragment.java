package com.adiandnoy.RatEat.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.DishAdapter;
import com.adiandnoy.RatEat.adapters.myDishPicAdapter;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;

import java.util.List;

public class ProfileFragment extends Fragment {

    Button dtls_btn;
    RecyclerView dishesList;
    List<Dish> myDishes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        dtls_btn = view.findViewById(R.id.my_dtls_btn);
        dishesList = view.findViewById(R.id.rv_my_dish);

        dishesList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dishesList.setLayoutManager(layoutManager);

        Model.instance.getAllDishes(new Model.GetAllDishesListener() {
            @Override
            public void onComplete(List<Dish> data) {
                myDishes = data;
//                for (Dish dish:data) {
                myDishPicAdapter adapter = new myDishPicAdapter(getLayoutInflater());
                adapter.data = data;
                dishesList.setAdapter(adapter);
                adapter.setOnClickListener(new myDishPicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                            DishesListFragmentDirections.actionDishListFragmentToDishDetailsFragment("noy");
                        Navigation.findNavController(view).navigate(R.id.action_DishListFragment_to_dishDetailsFragment);                        }
                });
//                }
            }
        });
        return view;
    }
}