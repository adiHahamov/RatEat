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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adiandnoy.RatEat.DishListViewModel;
import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.DishAdapter;
import com.adiandnoy.RatEat.adapters.myDishPicAdapter;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    Button dtls_btn;
    RecyclerView dishesList;
    List<Dish> myDishes;
    myDishPicAdapter dishAdapter;
    DishListViewModel viewModel;
    ImageView imageViewProfile;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String uid = currentUser.getUid();
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        viewModel = new ViewModelProvider(this).get(DishListViewModel.class);

        dtls_btn = view.findViewById(R.id.my_dtls_btn);
        dishesList = view.findViewById(R.id.rv_my_dish);
        imageViewProfile = view.findViewById(R.id.profilePic);
        dishesList.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dishesList.setLayoutManager(layoutManager);

        reloadData();

        dishAdapter = new myDishPicAdapter(getLayoutInflater());

        List<Dish> myDish = new ArrayList<Dish>();

        for (Dish dishP : viewModel.getList().getValue()) {
            if (dishP.getUserID().equals(uid) && dishP.getDeleted().equals(false)) {
                myDish.add(dishP);
            }
        }
            dishAdapter.data = myDish;
//        dishAdapter.data = viewModel.getList();

        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<Dish>>() {
            @Override
            public void onChanged(List<Dish> dishes) {
                List<Dish> myDish = new ArrayList<Dish>();
//                dishAdapter.notifyDataSetChanged();
                for (Dish dishP : viewModel.getList().getValue()) {
                    if (dishP.getUserID().equals(uid) && dishP.getDeleted().equals(false)) {
                        myDish.add(dishP);
                    }
                }
                    dishAdapter.data = myDish;
//                dishAdapter.data =viewModel.getList();
            }
        });

        return view;
    }

    void reloadData(){
//        pr.setVisibility(View.VISIBLE);
        Model.instance.refreshAllDishes(new Model.Listener() {
            @Override
            public void onComplete(Object data) {

                List<Dish> myDish = new ArrayList<Dish>();

                for (Dish dishP : viewModel.getList().getValue()) {
                if (dishP.getUserID().equals(uid)&& dishP.getDeleted().equals(false)) {
                    myDish.add(dishP);
                }
            }

                dishAdapter.data = myDish;
//                dishAdapter.data = viewModel.getList();
                dishesList.setAdapter(dishAdapter);

                dishAdapter.setOnClickListener(new myDishPicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                    }
                });


            }
        });
    }
}