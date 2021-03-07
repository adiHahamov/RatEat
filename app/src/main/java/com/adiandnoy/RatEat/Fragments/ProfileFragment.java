package com.adiandnoy.RatEat.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adiandnoy.RatEat.DishListViewModel;
import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.adapters.DishAdapter;
import com.adiandnoy.RatEat.adapters.myDishPicAdapter;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.adiandnoy.RatEat.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    Button dtls_btn;
    RecyclerView dishesList;
    List<Dish> myDishes;
    myDishPicAdapter dishAdapter;
    DishListViewModel viewModel;
    ImageView imageViewProfile;
    NestedScrollView nestedScrollView;
    SwipeRefreshLayout refreshLayout;

    FirebaseUser currentUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loadProfileData();
    }

    public void loadProfileData() {
        final NavController navController = Navigation.findNavController(getView());
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            showData();
        } else {
            Toast.makeText(getContext(), "Not allowed! sign in first", Toast.LENGTH_LONG).show();
            navController.navigate(R.id.tabBarFragment);
        }
        refreshLayout.setRefreshing(false);
    }

    public void showData() {
        viewModel = new ViewModelProvider(this).get(DishListViewModel.class);
        String uid = currentUser.getUid();
        dtls_btn = getView().findViewById(R.id.my_dtls_btn);
        dishesList = getView().findViewById(R.id.rv_my_dish);
        imageViewProfile = getView().findViewById(R.id.profilePic);
        nestedScrollView = getView().findViewById(R.id.nestedScroll);

        dishesList.setHasFixedSize(false);
        nestedScrollView.setNestedScrollingEnabled(false);

        dtls_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        dishesList.setLayoutManager(layoutManager);

        reloadData(uid);

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

        viewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                User DBuser = new User();

                for (User user : viewModel.getUserList().getValue()) {
                    if (user.getId().equals(uid)) {
                        DBuser = user;

                        Picasso.get().load(DBuser.getImageUrl()).into(imageViewProfile);
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        refreshLayout = view.findViewById(R.id.profile_swipe);

        refreshLayout.setOnRefreshListener(() -> {
            refreshLayout.setRefreshing(true);
            loadProfileData();
        });

        return view;
    }

    void reloadData(String uid) {
//        pr.setVisibility(View.VISIBLE);
        Model.instance.refreshAllDishes(new Model.Listener() {
            @Override
            public void onComplete(Object data) {

                List<Dish> myDish = new ArrayList<Dish>();

                for (Dish dishP : viewModel.getList().getValue()) {
                    if (dishP.getUserID().equals(uid) && dishP.getDeleted().equals(false)) {
                        myDish.add(dishP);
                    }
                }

                dishAdapter.data = myDish;
//                dishAdapter.data = viewModel.getList();
                dishesList.setAdapter(dishAdapter);

                dishAdapter.setOnClickListener(new myDishPicAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                        double starsNumber = 0.0;
//                        if (viewModel.getList().getValue().get(position).getStars() != null){
//                            starsNumber = viewModel.getList().getValue().get(position).getStars();
//                        }
//                        float starsNumberf = (float)starsNumber;
//                        ProfileFragmentDirections.ActionProfileFragmentToUpdateDishFragment dishUpdAction =
//                        ProfileFragmentDirections.actionProfileFragmentToUpdateDishFragment(viewModel.getList().getValue().get(position).getImageUrl(),viewModel.getList().getValue().get(position).getDishName(),viewModel.getList().getValue().get(position).getDishDescription(),viewModel.getList().getValue().get(position).getResturantName(),viewModel.getList().getValue().get(position).getIngredients(),starsNumberf);
//                        Navigation.findNavController(getView()).navigate(dishUpdAction);
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