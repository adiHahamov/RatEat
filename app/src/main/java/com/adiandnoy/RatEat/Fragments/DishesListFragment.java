package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adiandnoy.RatEat.DishListViewModel;
import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.DishAdapter;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class DishesListFragment extends Fragment {
//    List<Dish> dishList;
    RecyclerView rv;
    ProgressBar pr;
    DishListViewModel viewModel;
    DishAdapter dishAdapter;
    SwipeRefreshLayout sref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dishes_list, container, false);
//        RecyclerView rv = view.findViewById(R.id.studentlistfrag_list);

        viewModel = new ViewModelProvider(this).get(DishListViewModel.class);
        pr =  view.findViewById(R.id.progressBar_dishe_list);
        pr.setVisibility(View.INVISIBLE);

        //RecyclerView
        rv = view.findViewById(R.id.studentlistfrag_list);
        rv.hasFixedSize();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        reloadData();

        dishAdapter = new DishAdapter(getLayoutInflater());
        dishAdapter.data = viewModel.getList();

        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
//                dishAdapter.notifyDataSetChanged();
                dishAdapter.data =viewModel.getList();
            }
        });

        sref = view.findViewById(R.id.DishList_swipe);
        sref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sref.setRefreshing(true);
                reloadData();

            }
        });
        return view;
    }

    void reloadData(){
        pr.setVisibility(View.VISIBLE);
        Model.instance.refreshAllDishes(new Model.Listener() {
            @Override
            public void onComplete(Object data) {
                pr.setVisibility(View.INVISIBLE);
                sref.setRefreshing(false);

                //Update the rv
                dishAdapter.data = viewModel.getList();
                rv.setAdapter(dishAdapter);

                dishAdapter.setOnClickListener(new DishAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        double starsNumber = 0.0;
                        if (viewModel.getList().getValue().get(position).getStars() != null){
                            starsNumber = viewModel.getList().getValue().get(position).getStars();
                        }
                        float starsNumberf = (float)starsNumber;
                        DishesListFragmentDirections.ActionDishListFragmentToDishDetailsFragment dishInfoAction = DishesListFragmentDirections.actionDishListFragmentToDishDetailsFragment(viewModel.getList().getValue().get(position).getImageUrl(),viewModel.getList().getValue().get(position).getDishName(),viewModel.getList().getValue().get(position).getDishDescription(),viewModel.getList().getValue().get(position).getResturantName(),viewModel.getList().getValue().get(position).getIngredients(),starsNumberf);
                        Navigation.findNavController(getView()).navigate(dishInfoAction);
                    }
                });
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.top_menu,menu);
    }

}