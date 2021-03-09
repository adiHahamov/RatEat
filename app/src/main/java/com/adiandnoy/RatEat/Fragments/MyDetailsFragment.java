package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.adiandnoy.RatEat.R;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

public class MyDetailsFragment extends Fragment {

    ImageButton backBtn;
    TextInputEditText name;
    TextInputEditText lastName;
    TextInputEditText mail;
    TextInputEditText password;
    ImageView profileImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_my_details, container, false);
        setHasOptionsMenu(true);
        name =  view.findViewById(R.id.dispFirstName);
        lastName =  view.findViewById(R.id.dispLastName);
        mail =  view.findViewById(R.id.dispMail);
        password =  view.findViewById(R.id.dispPassword);
        profileImg = view.findViewById(R.id.dispProfileImg);
        backBtn = view.findViewById(R.id.backBtnFromDishDtls);

        // Gets the parameters
        String imgUrlParam = MyDetailsFragmentArgs.fromBundle(getArguments()).getProfileImgUrl();
        String nameParam = MyDetailsFragmentArgs.fromBundle(getArguments()).getFirstName();
        String lastNameParam = MyDetailsFragmentArgs.fromBundle(getArguments()).getLastName();
        String mailParam = MyDetailsFragmentArgs.fromBundle(getArguments()).getMail();
        String passwordParam =  MyDetailsFragmentArgs.fromBundle(getArguments()).getPassword();

        Picasso.get().load(imgUrlParam).into(profileImg);
        profileImg.setRotation(90);
        name.setText(nameParam);
        lastName.setText(lastNameParam);
        mail.setText(mailParam);
        password.setText(passwordParam);

        backBtn = view.findViewById(R.id.backBtnFromMyDtls);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(getView()).popBackStack();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        menu.clear();
        inflater.inflate(R.menu.top_menu,menu);
    }
}