package com.adiandnoy.RatEat.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.adiandnoy.RatEat.R;
import com.adiandnoy.RatEat.model.Dish;
import com.adiandnoy.RatEat.model.Model;
import com.adiandnoy.RatEat.model.User;
import com.google.android.material.textfield.TextInputEditText;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

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
        mail =  view.findViewById(R.id.input_email);
        password =  view.findViewById(R.id.input_password);
        saveUserButton = view.findViewById(R.id.save_user_btn);
        userImage = view.findViewById(R.id.input_profile_img);
        editImage = view.findViewById(R.id.editImageRegisterFragment);

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });


        saveUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                user.setId(UUID.randomUUID().toString());
                user.setName(name.getText().toString());
                user.setLastName(lastName.getText().toString());
                user.setMail(mail.getText().toString());
                user.setPassword(password.getText().toString());

                //Save the image
                BitmapDrawable drawable = (BitmapDrawable)userImage.getDrawable();
                Bitmap bitmap = drawable.getBitmap();

                Model.instance.uploadImage(bitmap, user.getId(), new Model.uploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if (url == null){

                        }else {
                            user.setImageUrl(url);
                            Model.instance.addUser(user,new Model.AddUserListener() {
                                @Override
                                public void onComplete() {

                                }
                            });
                        }
                    }
                });
            }
        });

        return view;
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void editImage() {
        Intent takePictureIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
                resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userImage.setImageBitmap(imageBitmap);
        }
    }
}