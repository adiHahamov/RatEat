package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.DishAdapter;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;

import java.util.List;

public class DishesListFragment extends Fragment {
    List<Dish> dishList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dishes_list, container, false);
        RecyclerView rv = view.findViewById(R.id.studentlistfrag_list);

        rv.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);

        Model.instance.getAllDishes(new Model.GetAllDishesListener() {
            @Override
            public void onComplete(List<Dish> data) {
                dishList = data;
//                for (Dish dish:data) {
                    DishAdapter adapter = new DishAdapter(getLayoutInflater());
                    adapter.data = data;
                    rv.setAdapter(adapter);
                    adapter.setOnClickListener(new DishAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
//                            DishesListFragmentDirections.actionDishListFragmentToDishDetailsFragment("noy");
                           Navigation.findNavController(view).navigate(R.id.action_DishListFragment_to_dishDetailsFragment);                        }
                    });
//                }
            }
        });

//        StudentsAdapter adapter = new StudentsAdapter(getLayoutInflater());
//        adapter.data = data;
//        rv.setAdapter(adapter);
//

        return view;
    }

}