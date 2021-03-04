package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
    RecyclerView rv;
    ProgressBar pr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dishes_list, container, false);
//        RecyclerView rv = view.findViewById(R.id.studentlistfrag_list);

        pr =  view.findViewById(R.id.progressBar_dishe_list);
        pr.setVisibility(View.INVISIBLE);

        //RecyclerView
        rv = view.findViewById(R.id.studentlistfrag_list);
        rv.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        reloadData();
        return view;
    }

    void reloadData(){
        pr.setVisibility(View.VISIBLE);
        Model.instance.getAllDishes(new Model.GetAllDishesListener() {
            @Override
            public void onComplete(List<Dish> data) {
                pr.setVisibility(View.INVISIBLE);
                dishList = data;
//                for (Dish dish:data) {
                DishAdapter adapter = new DishAdapter(getLayoutInflater());
                adapter.data = data;
                rv.setAdapter(adapter);
                adapter.setOnClickListener(new DishAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Float startsNumber = Float.valueOf(0);
                        if (dishList.get(position).getStars() != null){
                            startsNumber = dishList.get(position).getStars();
                        }
                        DishesListFragmentDirections.ActionDishListFragmentToDishDetailsFragment dishInfoAction = DishesListFragmentDirections.actionDishListFragmentToDishDetailsFragment(dishList.get(position).getImageUrl(),dishList.get(position).getDishName(),dishList.get(position).getDishDescription(),dishList.get(position).getResturantName(),dishList.get(position).getIngredients(),startsNumber);
                        Navigation.findNavController(getView()).navigate(dishInfoAction);
                    }
                });
            }
        });

    }

}