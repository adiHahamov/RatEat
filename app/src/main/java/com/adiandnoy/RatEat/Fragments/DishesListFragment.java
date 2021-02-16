package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.StudentsAdapter;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.adiandnoy.RatEat.model.Student;

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
                    StudentsAdapter adapter = new StudentsAdapter(getLayoutInflater());
                    adapter.data = data;
                    rv.setAdapter(adapter);
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