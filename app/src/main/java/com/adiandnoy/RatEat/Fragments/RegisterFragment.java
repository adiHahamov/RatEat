package com.adiandnoy.RatEat.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

public class RegisterFragment extends Fragment {

    TextInputEditText name;
    TextInputEditText lastName;
    TextInputEditText mail;
    TextInputEditText password;
    ImageButton editImage;
    ImageView userImage;
    Button saveUserButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        name =  view.findViewById(R.id.input_first_name);
        lastName =  view.findViewById(R.id.input_last_name);
//        mail =  view.findViewById(R.id.input_resturant_name);
        password =  view.findViewById(R.id.input_password);
        saveUserButton = view.findViewById(R.id.save_user_btn);

        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                editImage();
                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setName(name.getText().toString());
                user.setLastName(lastName.getText().toString());
//                user.setMail(mail.getText().toString());
                user.setPassword(password.getText().toString());
            }
        });

        return view;
    }
}